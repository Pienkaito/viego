package at.ac.univie.cosy.viego.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import at.ac.univie.cosy.viego.R;

/**
 * Created by Mourni on 07.05.2017.
 * https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */

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
        Button button = (Button) convertView.findViewById(R.id.add_button);
        button.setTag(position);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                int position =(Integer) view.getTag();
                PlaceInfo objectinfo = getItem(position);
                //  ADD THIS ITEM TO THE LIST OF DESTINATIONS
                // ADD A CHECK IF IT HAS ALREADY BEEN ADDED TO THE LIST
                //  IF ADDED HIDE BUTTON OR SMTH!

            }

        });

        TextView place_description = (TextView) convertView.findViewById(R.id.text_place_description);
        TextView place_name = (TextView) convertView.findViewById(R.id.text_place_name);


        //>>> Insert more values, image!
        place_name.setText(placeinfo.place_name);
        place_description.setText(placeinfo.formatted_address);

        // Return the completed view to render on screen
        return convertView;
    }
}