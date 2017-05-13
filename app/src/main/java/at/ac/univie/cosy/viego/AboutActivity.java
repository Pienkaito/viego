package at.ac.univie.cosy.viego;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mourni on 11.05.2017.
 */

public class AboutActivity extends AppCompatActivity implements View.OnClickListener {
	//ActionBar actionBar = getActionBar();

	//kommt dann in TourPreview!!!!!!!!!!!!!
	public static final String TAG = "Mainmenu Activity Log";
	public static final String API_CALL_MESSAGE = "API_WikiMESSAGE";
	public static final int REQUEST_CODE = 123;
	String exactWikiArticle = "St. Stephen's Cathedral, Vienna";
	String url = "https://en.wikipedia.org/w/api.php?format=jsonfm&action=query&prop=extracts&exintro=&explaintext=&titles="
			+ exactWikiArticle
	;
	Button button;

	//https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=St. Stephen's Cathedral, Vienna
	//kommt dann in TourPreview!!!!!!!!!!!!!




	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		button = (Button) findViewById(R.id.about_bottomc);
		button.setOnClickListener(this);
		//actionBar.setDisplayHomeAsUpEnabled(true);
	}


	//kommt dann in TourPreviewActivity



	@Override
	public void onClick(View view) {
		Intent i = new Intent(getApplicationContext(), MainMenuActivity.class);
		//i.putExtra("tourPlaceInfos", adapter.tourPlaceInfos);
		APIWikiSummary APIWikiSummary = new APIWikiSummary();
		APIWikiSummary.execute(url);

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

			try
			{
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
		 * @param values der Wert, der den Fortschritt anzeigt
		 */
		protected void onProgressUpdate(Integer... values) {

			Log.v("Progress","Once");
		}

		/**
		 * Wird nachdem Beenden der Methode doInBackground aufgerufen und berechnet anhand des uebergebenen result
		 * den Prozentsatz an ausgeliehenen Raedern. Außerdem wird die Progressbar wieder versteckt.
		 * @param result beinhaltet den String mit der Anzahl der Summe an EmptySlots und FreeBikes,
		 * der von doInBackground returned worden ist.
		 */
		@Override
		protected void onPostExecute(String result) {

			String hels = result;
			//Ich speichere die Informationen im internal storage und gebe sie an die ResultActivity weiter.
			Intent intent = new Intent(getApplicationContext(), MainMenuActivity.class);
			intent.putExtra(API_CALL_MESSAGE, result);
			startActivityForResult(intent, REQUEST_CODE);
			//output.setText(String.format("%.2f %%", percentage ) );
			//progressBar.setVisibility(View.INVISIBLE);
		}
	}

}
