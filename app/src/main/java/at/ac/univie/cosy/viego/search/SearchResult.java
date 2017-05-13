package at.ac.univie.cosy.viego.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.ac.univie.cosy.viego.R;
import at.ac.univie.cosy.viego.TourPreviewActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author beringuelmarkanthony, mayerhubert, raphaelkolhaupt
 */

public class SearchResult extends AppCompatActivity{

    // to add:  kurzer code damit die placeresults aus dem API call an diese class weitergegeben werden.
    ListView listview;
    //ProgressBar nowloading;
    private static final String TAG = "Main Activity Log";
	TextView no_result;

  @Override
    protected void onCreate (Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_search_result);

	  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

      Intent intent = getIntent();
	  try {
		  String api_response = intent.getStringExtra(SearchActivity.API_CALL_MESSAGE);
		  Log.i(TAG, "API wurde aufgerufen!");
		  Log.i(TAG, api_response);
		  List<PlaceInfo> searchresult = SearchHandler.getPlaceInformation(api_response);
		  no_result = (TextView)findViewById(R.id.text_noresults);
		  if (searchresult.isEmpty())
		  		no_result.setVisibility(View.VISIBLE);


		  listview = (ListView) findViewById(R.id.search_results);

		  final SearchAdapter adapter = new SearchAdapter(this, android.R.layout.simple_list_item_1, searchresult);
		  listview.setAdapter(adapter);

		  Button send_button = (Button) findViewById(R.id.send_btn);
		  send_button.setOnClickListener(new View.OnClickListener() {
			  @Override

			  //TODO  I MOVED THE CLICK LISTENER HERE AND CHANGED ADAPTER TO FINAL
			  public void onClick(View view) {
				  Intent i = new Intent(SearchResult.this, TourPreviewActivity.class);
				  i.putExtra("tourPlaceInfos", adapter.tourPlaceInfos);
				  startActivity(i);
			  }

		  });
	  }

      catch (JSONException e){
          Log.e(TAG, "JSON Konvertierung failed");
          Log.e(TAG, e.getMessage());
      }


  }

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



}
