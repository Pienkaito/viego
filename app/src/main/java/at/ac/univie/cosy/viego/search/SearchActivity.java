package at.ac.univie.cosy.viego.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import at.ac.univie.cosy.viego.R;
import at.ac.univie.cosy.viego.SingletonPosition;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * Dazu gehoerende XML-Files:<br>
 * -acitivity_search<br>
 * -search_textfield_appbar
 *
 * @author raphaelkolhaupt, mayerhubert, beringuelmarkanthony
 */

public class SearchActivity extends AppCompatActivity {

	public final static String apikey = "AIzaSyCo-ALqUgeiisJYac9D9Sog8E3VK9xHv74";
	public final static String API_CALL_MESSAGE = "API_MESSAGE";
	public static final String TAG = "Search Activity LOG";
	ProgressBar nowloading;
	String selected_category = null;
	Spinner spinner_radius;
	//the name for our shared pref data
	String settingsTAG = "ViegoSettings";
	TextView type;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);

		// I load the settings for miles/km
		SharedPreferences prefs = getSharedPreferences(settingsTAG, 0);
		boolean miles = prefs.getBoolean("miles", false);

		// I turn the progressbar invisible.
		nowloading = (ProgressBar) findViewById(R.id.search_progressbar);
		nowloading.setVisibility(View.GONE);

		//I define the textview that either says km or mile.
		type = (TextView) findViewById(R.id.text_type);

		// Define Spinner, apply spinner adapter to spinner to fill with values from the array depending on the settings miles or km
		spinner_radius = (Spinner) findViewById(R.id.spinner_umkreis);
		if (miles) {
			type.setText("miles");
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.radius_array_miles, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_radius.setAdapter(adapter);
		} else {
			type.setText("meters");
			ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.radius_array_km, android.R.layout.simple_spinner_item);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spinner_radius.setAdapter(adapter);
		}
	}

	@Override

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; goto parent activity.
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getSupportActionBar().setDisplayShowTitleEnabled(false);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getMenuInflater().inflate(R.menu.search_textfield_appbar, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		final SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String query) {
				//if the user submits his text:
				SharedPreferences prefs = getSharedPreferences(settingsTAG, 0);
				boolean miles = prefs.getBoolean("miles", false);
				String radius = spinner_radius.getSelectedItem().toString();
				//if the setting says we use miles, we have to calculate the approximate meters
				if (miles) {
					double rad = Double.parseDouble(radius) * 1609;
					radius = String.valueOf((int) rad);
				}
				String url = null;
				// we make the loading bar visible and turn off user interaction while also removing the input overlay
				nowloading.setVisibility(View.VISIBLE);
				getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
						WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
				searchView.clearFocus();
				//we calculate the current position (Simulated, dummy)
				SingletonPosition singleton = SingletonPosition.getInstance();
				double lat = singleton.getCurrentlat();
				double lo = singleton.getCurrentlong();

				//Building the url for the API Call
				if (query != null) {
					//QUERY + CATEGORY provided
					if (selected_category != null) {
						//Ich mache die Progressbar sichtbar da der Button geklickt wurde
						// Ich ändere die URL für den API Aufruf basierend auf der User Auswahl

						url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?&location=" +
								lat + "," + lo +
								"&radius=" + radius +
								"&type=" + selected_category +
								"&keyword=" + query +
								"&language=en" +
								"&key=" + apikey  //AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY
						;
						// lat 48.220071   long 16.356277 =  our institute
					}
					// NO CATEGORY provided!
					else {
						url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?&location=" +
								lat + "," + lo +
								"&radius=" + radius +
								//"&type=" + selected_category +
								"&keyword=" + query +
								"&language=en" +
								"&key=" + apikey  //AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY
						;
					}
				}
				//Starting the API Caller with the url to get results.
				if (url != null) {
					Log.i(TAG, "I sent" + url);
					APICallerPlaces places_api = new APICallerPlaces();
					places_api.execute(url);
				}
				return true;
			}

			@Override
			public boolean onQueryTextChange(String query) {
				return true;
			}
		});
		return true;
	}

	public void onRadioButtonClicked(View view) {
		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();
		Log.i(TAG, "changed category: was " + selected_category);
		// Check which radio button was clicked and change value
		switch (view.getId()) {
			case R.id.radio_ALL:
				if (checked)
					selected_category = null;
				break;
			case R.id.radio_art_gallery:
				if (checked)
					selected_category = "art_gallery";
				break;
			case R.id.radio_cafe:
				if (checked)
					selected_category = "cafe";
				break;
			case R.id.radio_church:
				if (checked)
					selected_category = "church";
				break;
			case R.id.radio_sight:
				if (checked)
					selected_category = "sight";
				break;

			case R.id.radio_museum:
				if (checked)
					selected_category = "museum";
				break;

		}
		Log.i(TAG, "changed cat: is now" + selected_category);
	}

	public class APICallerPlaces extends AsyncTask<String, Void, String> {
		OkHttpClient client = new OkHttpClient();

		@Override
		protected String doInBackground(String... params) {
			Request.Builder builder = new Request.Builder();
			//Ich nehme die URL und mache eine request
			builder.url(params[0]);
			Request request = builder.build();

			try {
				//Ich führe den API aufruf aus, und wenn erfolgreich ohne Exception, returne das Ergebnis an die onPostExecute funktion.
				Response response = client.newCall(request).execute();

				return response.body().string();

			} catch (Exception e) //Exception if call fails.
			{
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
				Log.e(TAG, "API Call for String failed");
				Log.e(TAG, e.getMessage());
				//print error message
			}
			return null;
		}

		@Override

		protected void onPostExecute(String api_response) {
			super.onPostExecute(api_response);
			try {

				//Ich speichere die Informationen im internal storage und gebe sie an die ResultActivity weiter.
				Intent intent = new Intent(getApplicationContext(), SearchResult.class);
				intent.putExtra(API_CALL_MESSAGE, api_response);
				//Die Berechnungen sind fertig, deshalb mache ich die Progressbar wieder unsichtbar und rufe die Result Activity auf
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
				nowloading.setVisibility(View.GONE);

				startActivity(intent);

			} catch (Exception e) // Exception wird gefangen nachdem die konvertierung fehlschlägt
			{
				getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
				nowloading.setVisibility(View.GONE);
				Log.e(TAG, "put INTENT FAILED");
				Log.e(TAG, e.getMessage());
				//Error message!
			}
		}
	}
}