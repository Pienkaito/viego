package at.ac.univie.cosy.viego.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.nio.BufferUnderflowException;
import java.util.ArrayList;

import at.ac.univie.cosy.viego.R;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mourni on 07.05.2017.
 */
/*
public class SearchAdapter extends ArrayAdapter<PlaceInfo> {
    public SearchAdapter(Context context, ArrayList<PlaceInfo> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        PlaceInfo placeinfo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_place, parent, false);
        }
        // Find views from the xml
        TextView place_description = (TextView) convertView.findViewById(R.id.text_place_description);
        TextView place_name = (TextView) convertView.findViewById(R.id.text_place_name);


        //>>> Insert more values, image!
        place_name.setText(placeinfo.place_name);
        place_description.setText(placeinfo.formatted_address);
        // Return the completed view to render on screen
        return convertView;
    }
}
*/

public class SearchResult extends Activity{
    // to add:  kurzer code damit die placeresults aus dem API call an diese class weitergegeben werden.
    public final static String CITY_MESSAGE = "Stadt";
    ListView listview;
    ArrayAdapter<PlaceInfo> adapter;
    ProgressBar nowloading;


  @Override
    protected void onCreate (Bundle savedInstanceState){
      super.onCreate(savedInstanceState);

      nowloading = (ProgressBar)findViewById(R.id.search_progressbar);


      Intent intent = getIntent();
      String api_response = intent.getStringExtra(SearchActivity.API_CALL_MESSAGE);
      PlaceInfo[] searchresult =null;
      try{
          nowloading.setVisibility(View.VISIBLE);
          searchresult = SearchHandler.getPlaceInformation(api_response);
      }

      catch (JSONException e){
          nowloading.setVisibility(View.GONE);

      }
      if (searchresult == null)
          return;

      setContentView(R.layout.activity_search_result);
      listview = (ListView) findViewById(R.id.search_results);

      adapter = new ArrayAdapter<PlaceInfo>(this, android.R.layout.simple_list_item_1, searchresult);
      listview.setAdapter(adapter);
      nowloading.setVisibility(View.GONE);

      listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



              Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected", Toast.LENGTH_LONG).show();
          }
      });

  }



}
