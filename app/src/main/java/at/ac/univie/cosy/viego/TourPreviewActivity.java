package at.ac.univie.cosy.viego;

import android.graphics.Color;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.HashSet;

import at.ac.univie.cosy.viego.search.PlaceInfo;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This class generates a path for the tour using the chosen sights from SearchResult.java
 * <p>Used XML-Files:</p>
 * <p>res.layout:</p>
 * <ul>
 * <li>tourpreview_layout</li>
 * <p>Layout of the Tour previewer</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>R.id.tourpreview_bottom_content</li>
 * </ul>
 *
 * @author raphaelkolhaupt, mayerhubert, beringuelmarkanthony
 */
public class TourPreviewActivity extends AppCompatActivity
		implements OnMapReadyCallback {

	private static final float minZoomFactor = 15.0f;
	private static final float maxZoomFactor = 18.0f;
	private GoogleMap gMap;
	private LatLng curcoord = new LatLng(
			SingletonPosition.getInstance().getCurrentlat(),
			SingletonPosition.getInstance().getCurrentlong());
	private PolylineOptions path;
	private ArrayList<PlaceInfo> list = null;

	public static final String TAG = "Mainmenu Activity Log";
	public static final String API_CALL_MESSAGE = "API_WikiMESSAGE";
	public static final int REQUEST_CODE = 123;

	int random = (int) (Math.random() * 10);

	String[] exactWikiArticle = {"St. Stephen's Cathedral, Vienna", "Karlsplatz", "Volkstheater,_Vienna",
			"Gasometer,_Vienna", "TU_Wien", "Wiener_Riesenrad", "Albertina", "Natural_History_Museum,_Vienna",
			"Austrian_National_Library", "Wotruba_Church"};

	String url = "https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles="
			+ exactWikiArticle[random];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tourpreview_layout);

		APIWikiSummary APIWikiSummary = new APIWikiSummary();
		APIWikiSummary.execute(url);

		//Google Maps init
		list = new ArrayList<PlaceInfo>((HashSet<PlaceInfo>) getIntent().getSerializableExtra("tourPlaceInfos"));
		path = createPath(new ArrayList<PlaceInfo>((HashSet<PlaceInfo>) getIntent().getSerializableExtra("tourPlaceInfos")));

		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.gmap);
		mapFragment.getMapAsync(this);

	}

	/*
	Overrides the action bar with a back button
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		return true;
	}

	/*
	This method handles all the actions made in the action bar.
	In this case the back arrow button
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		switch (id) {
			case android.R.id.home:
				gMap.clear();
				this.finish();
				return true;
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	/*
	Method, that initiates the Google Map with the path included
	Also limits the zoom factor by the constants minZoomFactor & maxZoomFactor
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		gMap = googleMap;
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curcoord, maxZoomFactor));
		gMap.setMinZoomPreference(minZoomFactor);
		gMap.setMaxZoomPreference(maxZoomFactor);
		gMap.setBuildingsEnabled(false);

		gMap.addPolyline(path);

		gMap.addMarker(new MarkerOptions()
				.position(curcoord)
				.title("You are here")
		);

		for (PlaceInfo info : list) {
			LatLng pos = new LatLng(Double.valueOf(info.loc_lat), Double.valueOf(info.loc_lng));
			gMap.addMarker(new MarkerOptions()
					.position(pos)
					.title(info.place_name)
			);
		}
	}

	/*
	This method is needed for creating the paths.
	As starting point, it will use the user's current position and adds it to the collection of
	coordinates and the method will draw a path to the nearest sight. Once a match has been found
	it will take that sight as new "current" position and draw the shortest path as well until
	all sights used.
	 */
	private PolylineOptions createPath(ArrayList<PlaceInfo> list) {
		PolylineOptions createdPath = new PolylineOptions();
		createdPath.width(10);
		createdPath.color(Color.RED);

		SingletonPosition singletonPosition = SingletonPosition.getInstance();
		PlaceInfo placeInfo = new PlaceInfo();
		placeInfo.loc_lat = String.valueOf(singletonPosition.getCurrentlat());
		placeInfo.loc_lng = String.valueOf(singletonPosition.getCurrentlong());
		list.add(placeInfo);

		while (!list.isEmpty()) {
			createdPath.add(getNearest(list.get(list.size() - 1), list));
		}
		return createdPath;
	}

	/*
	This method calculates the shortest distance between all sights and returns the coordinates
	 */
	private LatLng getNearest(PlaceInfo curr, ArrayList<PlaceInfo> list) {
		PlaceInfo shortestPlace = list.get(0);
		LatLng startpoint = new LatLng(Double.valueOf(curr.loc_lat), Double.valueOf(curr.loc_lng));
		LatLng minCoordinates = new LatLng(Double.valueOf(shortestPlace.loc_lat), Double.valueOf(shortestPlace.loc_lng));
		double minDistance = getDistance(startpoint, minCoordinates);

		for (PlaceInfo x : list) {
			LatLng endpoint = new LatLng(Double.valueOf(x.loc_lat), Double.valueOf(x.loc_lng));
			double calc = getDistance(startpoint, endpoint);
			if ((calc != 0) && (minDistance > calc)) {
				shortestPlace = x;
				minCoordinates = endpoint;
				minDistance = calc;
			}
		}
		list.remove(list.indexOf(shortestPlace));
		return minCoordinates;
	}

	/*
	This method calculates the distance between two coordinates
	 */
	private double getDistance(LatLng startpoint, LatLng endpoint) {
		Location pointA = new Location("point A");
		pointA.setLatitude(startpoint.latitude);
		pointA.setLongitude(startpoint.longitude);

		Location pointB = new Location("point B");
		pointB.setLatitude(endpoint.latitude);
		pointB.setLongitude(endpoint.longitude);
		return pointA.distanceTo(pointB);
	}

	public class APIWikiSummary extends AsyncTask<String, Void, String> {

		//private String url;
		OkHttpClient client = new OkHttpClient();


		/**
		 * Wird aufgerufen, bevor der Task ausgefuehrt wird. Und setzt den Maximalwert der ProgressBar auf 100.
		 */
		@Override
		protected void onPreExecute() {

		}


		@Override
		protected String doInBackground(String... params) {
			Request.Builder builder = new Request.Builder();
			//Ich nehme die URL und mache eine request
			builder.url(params[0]);
			Request request = builder.build();

			Log.e(TAG, "Requested URL worked");

			try {
				//Ich führe den API aufruf aus, und wenn erfolgreich ohne Exception, returne das Ergebnis an die onPostExecute funktion.
				Response response = client.newCall(request).execute();
				Log.e(TAG, "executed ok http");
				return response.body().string();

			} catch (Exception e) //Exception if call fails.
			{
				Log.e(TAG, "API Call failed");
				Log.e(TAG, e.getMessage());
			}
			return null;
		}

		/**
		 * Wird mehrmals von doInBackground aufgerufen, um den Fortschritt anzuzeigen.
		 *
		 * @param values der Wert, der den Fortschritt anzeigt
		 */
		protected void onProgressUpdate(Integer... values) {

			Log.v("Progress", "Once");
		}

		/**
		 * Wird nachdem Beenden der Methode doInBackground aufgerufen und berechnet anhand des uebergebenen result
		 * den Prozentsatz an ausgeliehenen Raedern. Außerdem wird die Progressbar wieder versteckt.
		 *
		 * @param result beinhaltet den String mit der Anzahl der Summe an EmptySlots und FreeBikes,
		 *               der von doInBackground returned worden ist.
		 */
		@Override
		protected void onPostExecute(String result) {

			String help = result;

			//int beginIndex = result.lastIndexOf("\"extract\":\"");
			int beginIndex = result.lastIndexOf("\"extract\":\"") + 11;
			int endIndex = result.lastIndexOf("\"");
			String output = result.substring(beginIndex, endIndex);

			View test1View = findViewById(R.id.tourpreview_bottom_content);
			TextView output_bottom = (TextView) test1View.findViewById(R.id.mainmenu_bottom_contenttextview);

			output_bottom.setMovementMethod(new ScrollingMovementMethod());

			output_bottom.setText(output);

			//output.setText(String.format("%.2f %%", percentage ) );
			//progressBar.setVisibility(View.INVISIBLE);
		}
	}


}
