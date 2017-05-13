package at.ac.univie.cosy.viego.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import at.ac.univie.cosy.viego.R;

/**
 * Dazu gehoerende XML-Files:<br>
 *     -acitivity_search<br>
 *     -search_textfield_appbar
 * @author raphaelkolhaupt, mayerhubert, beringuelmarkanthony
 */
public class SearchActivity extends AppCompatActivity {

    public final static String apikey = "AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY";
    ProgressBar nowloading;
    String selected_category = null;
    Spinner spinner_radius;
    TextView format;
    String settingsTAG = "ViegoSettings";

    public final static String API_CALL_MESSAGE = "API_MESSAGE";

    public static final String TAG = "Main Activity Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        SharedPreferences prefs = getSharedPreferences(settingsTAG, 0);
        boolean miles = prefs.getBoolean("miles", false);

        // Hole mir die progressbar vom view und mach sie unsichtbar.
        format = (TextView)findViewById(R.id.text_km);
        if(prefs.getBoolean("miles", false))
            format.setText("miles");
        else
            format.setText("km");

       nowloading = (ProgressBar)findViewById(R.id.search_progressbar);
       nowloading.setVisibility(View.GONE);

        // Define Spinner, apply spinner adapter to spinner to fill with values form the array
        spinner_radius = (Spinner) findViewById(R.id.spinner_umkreis);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.radius_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_radius.setAdapter(adapter);


        //android.app.ActionBar actionBar = getActionBar();
        //getActionBar().setCustomView(R.layout.mainmenu_layout);


        // Toolbar wird geholt und der Titel der App wird nicht angezeigt d
        //Toolbar searchToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        //setSupportActionBar(searchToolbar);

    }




	@Override

	public boolean onOptionsItemSelected (MenuItem item){
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
        getMenuInflater().inflate(R.menu.search_textfield_appbar, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                String url = null;

                //QUERY
                if (query != null){
                    //QUERY + CAT
                    if (selected_category != "default") {
                        //Ich mache die Progressbar sichtbar da der Button geklickt wurde
                       // nowloading.setVisibility(View.VISIBLE);
                        // Ich ändere die URL für den API Aufruf basierend auf der User Auswahl
                        url = null;

                        url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?&location=" +
                                "48.220071,16.356277" +
                                "&radius=" + spinner_radius.getSelectedItem().toString() +
                                "&type=" + selected_category +
                                "&keyword=" + query +
                                "&key=" + apikey  //AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY
                        ;
                        // lat 48.220071   long 16.356277
                    }

                    // NO CATEGORY
                    else
                    {
                            url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?&location=" +
                                    "48.220071,16.356277" +
                                    "&radius=" + spinner_radius.getSelectedItem().toString() +
                                    //"&type=" + selected_category +
                                    "&keyword=" + query +
                                    "&key=" + apikey  //AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY
                                    ;
                    }
                }

                    //Ich frage bei der API an über den Konstruktor und die execute funktion mit der vollständigen URL als parameter

                if (url != null) {
                    // TODO nowloading.setVisibility(View.VISIBLE);
					Log.i(TAG, "I sent"+url);
                    APICallerPlaces places_api = new APICallerPlaces();
                    places_api.execute(url);
                }
                return true;
            }
            @Override
            public boolean onQueryTextChange(String query) {
                //TODO write your code what you want to perform on search text change
                return true;
            }
        });
        return true;
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_ALL:
                if (checked)
                    selected_category = "default";
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
                Log.e(TAG, "API Call for String failed");
                Log.e(TAG, e.getMessage());
                //print error message
            }
            return null;
        }
        @Override

        protected void onPostExecute(String api_response)
        {
            super.onPostExecute(api_response);
            try
            {

                //Ich speichere die Informationen im internal storage und gebe sie an die ResultActivity weiter.
                Intent intent = new Intent(getApplicationContext(), SearchResult.class);
                intent.putExtra(API_CALL_MESSAGE, api_response);

                //Die Berechnungen sind fertig, deshalb mache ich die Progressbar wieder unsichtbar und rufe die Result Activity auf
                startActivity(intent);

            } catch (Exception e) // Exception wird gefangen nachdem die konvertierung fehlschlägt
            {
                Log.e(TAG, "put INTENT FAILED");
                Log.e(TAG, e.getMessage());
                //Error message!
            }
        }

    }

    //Following added by Mourni:
/*
    public void onClick(View view) {  // ERROR HIER WEIL NOCH KEIN SEARCH BUTTON

    }
*/
}