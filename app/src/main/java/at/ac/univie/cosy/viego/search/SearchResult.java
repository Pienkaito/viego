package at.ac.univie.cosy.viego.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
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
 * Created by Mourni on 07.05.2017.
 */

public class SearchResult extends Activity{

    // to add:  kurzer code damit die placeresults aus dem API call an diese class weitergegeben werden.
    ListView listview;
    //ProgressBar nowloading;
    public static final String TAG = "Main Activity Log";
	TextView no_result;

  @Override
    protected void onCreate (Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
	  setContentView(R.layout.activity_search_result);

      //nowloading = (ProgressBar)findViewById(R.id.search_progressbar);
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
		 /* listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			  @Override
			  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

				  Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected", Toast.LENGTH_LONG).show();
			  }
		  });*/
		  Button send_button = (Button) findViewById(R.id.send_btn);
		  send_button.setOnClickListener(new View.OnClickListener() {
			  @Override

			  //TODO  I MOVED THE CLICK LISTENER HERE AND CHANGED ADAPTER TO FINAL
			  public void onClick(View view) {
				  Intent i = new Intent(getApplicationContext(), TourPreviewActivity.class);
				  //i.putExtra("tourPlaceInfos", adapter.tourPlaceInfos);
				  startActivity(i);
			  }

		  });
	  }

      catch (JSONException e){
        //  nowloading.setVisibility(View.GONE);
          Log.e(TAG, "JSON Konvertierung failed");
          Log.e(TAG, e.getMessage());
      }
     // nowloading.setVisibility(View.GONE);



  }




}
