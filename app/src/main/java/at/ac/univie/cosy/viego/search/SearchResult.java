package at.ac.univie.cosy.viego.search;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONException;

import java.util.List;

import at.ac.univie.cosy.viego.R;
import at.ac.univie.cosy.viego.TourPreviewActivity;

/**
 * Dazu gehoerende XML-Files:<br>
 * -acitivity_search_result<br>
 * list_item_place
 *
 * @author raphaelkolhaupt, mayerhubert, beringuelmarkanthony
 */

public class SearchResult extends AppCompatActivity {

	private static final String TAG = "Search Result Log";
	ListView listview;
	TextView no_result;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);

		//I set set the actionbar so that it has a back-button
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		//I call the intent
		Intent intent = getIntent();
		try {
			//I get the reply from the API from the intent and start the JSON Handler for the search results:
			String api_response = intent.getStringExtra(SearchActivity.API_CALL_MESSAGE);
			Log.i(TAG, "API wurde aufgerufen!");
			Log.i(TAG, api_response);
			//I Parse the searchresults and put them into a PlaceInfo List.
			List<PlaceInfo> searchresult = SearchHandler.getPlaceInformation(api_response);
			no_result = (TextView) findViewById(R.id.text_noresults);

			// If we didn't get any search results, we set no results message to visible.
			if (searchresult.isEmpty())
				no_result.setVisibility(View.VISIBLE);

			// I use my custom adapter (SearchAdapter) to turn each PlaceInfo object into a listview item.
			listview = (ListView) findViewById(R.id.search_results);
			final SearchAdapter adapter = new SearchAdapter(this, android.R.layout.simple_list_item_1, searchresult);
			listview.setAdapter(adapter);

			//Once the user has selected all the Placeinfos he can click the button to send them.
			Button send_button = (Button) findViewById(R.id.send_btn);
			send_button.setOnClickListener(new View.OnClickListener() {
				@Override

				//TODO  I MOVED THE CLICK LISTENER HERE AND CHANGED ADAPTER TO FINAL
				public void onClick(View view) {
					Intent i = new Intent(SearchResult.this, TourPreviewActivity.class);
					i.putExtra("tourPlaceInfos", adapter.tourPlaceInfos);

					startActivity(i);
					adapter.tourPlaceInfos.clear();
				}

			});
		} catch (JSONException e) {
			Log.e(TAG, "JSON Konvertierung failed");
			Log.e(TAG, e.getMessage());
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// If we click the back button, we close the activity and return to the previous.
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}


}
