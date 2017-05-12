package at.ac.univie.cosy.viego.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import at.ac.univie.cosy.viego.R;

/**
 * Created by Mourni on 07.05.2017.
 * https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */

public class SearchAdapter extends ArrayAdapter<PlaceInfo> {

	public SearchAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId);
	}

	public SearchAdapter(Context context, int resource, List<PlaceInfo> items) {
		super(context, resource, items);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getContext());
			v = inflater.inflate(R.layout.list_item_place, null);
		}

		PlaceInfo p = getItem(position);

		if (p != null) {
			TextView place_description = (TextView) v.findViewById(R.id.text_place_description);
			TextView place_name = (TextView) v.findViewById(R.id.text_place_name);

				place_name.setText(p.place_name);
				place_description.setText(p.formatted_address);

			Button button = (Button) v.findViewById(R.id.list_button_add);
			button.setTag(position);
			button.setOnClickListener(new View.OnClickListener(){
				@Override
				public void onClick(View view) {
					int position =(Integer) view.getTag();
					PlaceInfo objectinfo = getItem(position);
					Toast.makeText(getContext(),"You clicked a button yay", Toast.LENGTH_LONG).show();

				}

			});

		}
		return v;
	}

}

/*



        // Return the completed view to render on screen
        return convertView;
    }
}
*/