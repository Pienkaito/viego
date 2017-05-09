package at.ac.univie.cosy.viego.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;

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

    ProgressBar nowloading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Hole mir die progressbar vom view und mach sie unsichtbar.
        nowloading = (ProgressBar)findViewById(R.id.search_progressbar);
        nowloading.setVisibility(View.GONE);


        //android.app.ActionBar actionBar = getActionBar();
        //getActionBar().setCustomView(R.layout.mainmenu_layout);


        // Toolbar wird geholt und der Titel der App wird nicht angezeigt d
        //Toolbar searchToolbar = (Toolbar) findViewById(R.id.search_toolbar);
        //setSupportActionBar(searchToolbar);

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
                //TODO write your code what you want to perform on search
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

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch (view.getId()) {
            case R.id.checkBox2:
                if (checked) {

                }
                // Put some meat on the sandwich
                else
                    // Remove the meat
                    break;
            case R.id.checkBox3:
                if (checked) {

                } else {
                    //asdf
                }
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
        }
        // Cheese me

    }

    //Following added by Mourni:

    public void onClick(View view) {  // ERROR HIER WEIL NOCH KEIN SEARCH BUTTON

        //Ich mache die Progressbar sichtbar da der Button geklickt wurde
        nowloading.setVisibility(View.VISIBLE);
        // Ich ändere die URL für den API Aufruf basierend auf der User Auswahl
        String TESTURL = null;
        //HIER MÜSSEN WIR BERECHNEN WELCHE URL WIR ABSCHICKEN!
        //
        //
        //
        TESTURL = "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY"
                    ;


        //Ich frage bei der API an über den Konstruktor und die execute funktion mit der vollständigen URL als parameter
        APICallerPlaces places_api = new APICallerPlaces();
        places_api.execute(TESTURL);


    }

    public class APICallerPlaces extends AsyncTask<String, Void, String> {
        // Ich erstelle einen OkHttp client
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
                // Ich hole mir die Ergebnisse von meiner JSOnHandler Klasse und berechne die Werte für die result activity
                PlaceInfo[] searchresult = SearchHandler.getPlaceInformation(api_response);


                //Ich speichere die Informationen im internal storage und gebe sie an die ResultActivity weiter.
                Intent intent = new Intent(getApplicationContext(), SearchResult.class);
                /*
                intent.putExtra(CITY_MESSAGE, spinner.getSelectedItem().toString());
                */
                //Die Berechnungen sind fertig, deshalb mache ich die Progressbar wieder unsichtbar und rufe die Result Activity auf
                nowloading.setVisibility(View.GONE);
                startActivity(intent);

            } catch (JSONException e) // Exception wird gefangen nachdem die konvertierung fehlschlägt
            {
               //Error message!
            }
        }

    }
}