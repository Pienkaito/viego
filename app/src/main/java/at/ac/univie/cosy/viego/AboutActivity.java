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

public class AboutActivity extends AppCompatActivity {
	//ActionBar actionBar = getActionBar();

	//kommt dann in TourPreview!!!!!!!!!!!!!
	public static final String TAG = "Mainmenu Activity Log";
	//ads
	//https://en.wikipedia.org/w/api.php?format=json&action=query&prop=extracts&exintro=&explaintext=&titles=St. Stephen's Cathedral, Vienna
	//kommt dann in TourPreview!!!!!!!!!!!!!




	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		//actionBar.setDisplayHomeAsUpEnabled(true);
	}


	//kommt dann in TourPreviewActivity





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

}
