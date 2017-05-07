package at.ac.univie.cosy.viego.search;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.nio.BufferUnderflowException;

import at.ac.univie.cosy.viego.R;

/**
 * Created by Mourni on 07.05.2017.
 */

public class SearchResult extends Activity{
    ListView listview;
    ArrayAdapter<String> adapter;
    String[] test = {"cupcake", "Donut", "Froyo"};


  @Override
    protected void onCreate (Bundle savedInstanceState){
      super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_search_result);
      listview = (ListView) findViewById(R.id.search_results);

      adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, test);
      listview.setAdapter(adapter);

      listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
          @Override
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              Toast.makeText(getBaseContext(),parent.getItemAtPosition(position)+"is selected", Toast.LENGTH_LONG).show();
          }
      });

  }

}
