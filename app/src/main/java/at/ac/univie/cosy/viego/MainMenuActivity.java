package at.ac.univie.cosy.viego;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;
import org.w3c.dom.Text;

import at.ac.univie.cosy.viego.search.SearchActivity;
import at.ac.univie.cosy.viego.search.SearchResult;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * <p>Dazu gehoerende XML-Files:</p>
 * <p>res.layout:</p>
 * <ul>
 * <li>mainmenu_layout</li>
 * <p>Layout of the main menu without the navigation drawer</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>R.id.toolbar</li>
 * </ul></br>
 * <li>mainmenu_layout_with_nav</li>
 * <p>Layout of the main menu with the navigation drawer</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>R.id.nav_view</li>
 * </ul></br>
 * <li>navigation_drawer_header</li>
 * <p>Header of the navigation drawer, which contains a logo and the name of the app</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>R.id.nav_header_img</li>
 * <li>R.id.nav_header_link</li>
 * </ul></br>
 * <li>mainmenu_bottom_content</li>
 * <p>Everything that displays the bottom part of the main menu will be handled here</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>Kill</li>
 * <li>Me</li>
 * </ul></br>
 * </ul>
 * <p>res.menu:</p></br>
 * <ul>
 * <li>mainmenu_navigation_drawer</li>
 * <li>search_app_bar</li>
 * </ul>
 *
 * @author raphaelkolhaupt, mayerhubert, beringuelmarkanthony
 */
public class MainMenuActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener,
		OnMapReadyCallback,
		View.OnClickListener {

	private static final float minZoomFactor = 15.0f;
	private static final float maxZoomFactor = 18.0f;
	private GoogleMap gMap;
	private LatLng curcoord = null;
	private LinearLayout bottom_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu_layout_with_nav);

		SingletonPosition singleton = SingletonPosition.getInstance();
		curcoord = new LatLng(singleton.getCurrentlat(), singleton.getCurrentlong());

		//App Bar init
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		//Main Menu Layout init
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mainmenu_drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,
				drawer,
				toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close
		) {
		};
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		//Google Maps init
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.gmap);
		mapFragment.getMapAsync(this);
		/*
		locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
		locationListener = new LocationListener() {
			@Override
			public void onLocationChanged(Location location) {
				double lat = location.getLatitude();
				double lon = location.getLongitude();

				LatLng latLng = new LatLng(lat, lon);
				Geocoder geocoder = new Geocoder(getApplicationContext());
				try {
					List<Address> addressList = geocoder.getFromLocation(lat, lon, 1);
					String str = addressList.get(0).getLocality() + ", ";
					str += addressList.get(0).getCountryName();
					gMap.addMarker(new MarkerOptions()
							.position(latLng)
							.title(str)
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_menu_send))
					);
					gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, maxZoomFactor));
				} catch (IOException e) {
					e.printStackTrace();
				}


			}

			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {

			}
		};


		if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// Should we show an explanation?
			/*if (ActivityCompat.shouldShowRequestPermissionRationale(this,
					Manifest.permission.ACCESS_FINE_LOCATION)) {

				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);

				View layout = inflater.inflate(R.layout.dialog_form, (ViewGroup) findViewById(R.id.llid));
				//layout_root should be the name of the "top-level" layout node in the dialog_layout.xml file.
				final EditText txtFB = (EditText) layout.findViewById(R.id.txtFB);

				// Show an explanation to the user *asynchronously* -- don't block
				// this thread waiting for the user's response! After the user
				// sees the explanation, try again to request the permission.
				AlertDialog.Builder builder = new AlertDialog.Builder(this);
						builder.setTitle("Location Permission Needed");
						builder.setMessage("This app needs the Location permission, please accept to use location functionality");
						builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialogInterface, int i) {
								//Prompt the user once explanation has been shown
								ActivityCompat.requestPermissions(MainMenuActivity.this,
										new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
										MY_PERMISSIONS_REQUEST_LOCATION);
							}
						});
						builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
						AlertDialog dialog = builder.create();
						dialog.show();


			} else {
				// No explanation needed, we can request the permission.
				ActivityCompat.requestPermissions(this,
						new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
						MY_PERMISSIONS_REQUEST_LOCATION);
			}

		}

		if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
		} else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
		}
		*/

		//Navigation Drawer
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.gps_btn);
		myFab.setOnClickListener(this);


/*
		//if jQuery != null, dann fuehre dass jQuery aus und fuelle bottom content mit den Daten und mach die View
		//Visible, ansonst unten weiter
		//https://en.wikipedia.org/w/api.php?action=query&format=json&list=geosearch&titles=Stephansdom&gscoord=48.224368%7C16.353041&gsradius=10000&gslimit=10
		String jString = null;
		String url = null;
		String titleOfstandort = "Stephansdom";
		String latitude = "48.224368";
		String longitude = "16.353041";

		url = "https://en.wikipedia.org/w/api.php?" + "action=query&format==json&list=geosearch&titles=" +
				titleOfstandort + "&gscoord=" + latitude + "|" + longitude + "&gsradius=10000&gslimit=10"
		;

			//Hide Bottom
			JSONObject json = null;
			try {
				json = new JSONObject(jString);
				JSONArray results = json.getJSONArray("results");

			} catch (JSONException e) {
				e.printStackTrace();
			}*/


		if(getCallingActivity() == null)
		{
			//ansonst
			//Hide Bottom
			bottom_content = (LinearLayout) findViewById(R.id.mainmenu_bottom_content);
			bottom_content.setVisibility(View.GONE);
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.gps_btn:

				break;

		}
	}


	//WIEDER LOESCHEN, nur fuer die Weiterleitung durch Button zur naechsten Activity!!!!!!!!
	public void buttonClickFunction(View v) {
		if (v.getId() == R.id.bottom_btn_1) {
			Intent intent = new Intent(this, SearchActivity.class);
			startActivity(intent);
		}
	}

	/*
	Sets the app bar drawer with the elements from R.menu.search_app_bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search_app_bar, menu);
		return true;
	}

	/*
	This method handles all the actions made in the app bar.
	The action bar will automatically handle clicks on the Home/Up button,
	so long as you specify a parent activity in AndroidManifest.xml.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
			case R.id.app_bar_search:
				Intent intent = new Intent(this, SearchActivity.class);
				startActivity(intent);
				//Enter Search bar things here
				break;
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	/*
	This method handles all the actions made in the navigation drawer
   */
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
			case R.id.nav_001:
				Intent intent_search = new Intent(this, SearchActivity.class);
				startActivity(intent_search);
				break;
			case R.id.nav_002:
				//Start Tour
				break;
			case R.id.nav_003:
				Intent intent_settings = new Intent(this, SettingsActivity.class);
				startActivity(intent_settings);
				break;
			case R.id.nav_004:
				Intent intent_about = new Intent(this, AboutActivity.class);
				startActivity(intent_about);
				break;
			default:
				break;
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mainmenu_drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	/*
	Method, that initiates the Google Map with default coordinates (Center of Vienna)
	Also limits the zoom factor by the constants minZoomFactor & maxZoomFactor
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		gMap = googleMap;

		/*
		gMap.addMarker(new MarkerOptions()
			.position(curcoord)
			.title("Viiiiiiii")
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone))
		);

		gMap.addPolyline(new PolylineOptions().add(
				curcoord,
				new LatLng(48.207602, 16.373008),
				new LatLng(48.206741, 16.373515)
				)
						.width(10)
						.color(Color.RED)
		);
		*/
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curcoord, maxZoomFactor));
		gMap.setMinZoomPreference(minZoomFactor);
		gMap.setMaxZoomPreference(maxZoomFactor);
		gMap.setBuildingsEnabled(false);


	}

	/*
	Method, that's being called, everytime the user presses the back button
	In this case it checks the current state of the navigation drawer and reacts accordingly
	 */
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mainmenu_drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}

}
