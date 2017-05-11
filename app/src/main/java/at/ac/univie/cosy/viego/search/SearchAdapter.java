package at.ac.univie.cosy.viego.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

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
			LayoutInflater vi;
			vi = LayoutInflater.from(getContext());
			v = vi.inflate(R.layout.list_item_place, null);
		}

		PlaceInfo p = getItem(position);

		if (p != null) {
			TextView place_description = (TextView) v.findViewById(R.id.text_place_description);
			TextView place_name = (TextView) v.findViewById(R.id.text_place_name);

				place_name.setText(p.place_name);
				place_description.setText(p.formatted_address);

			//if (tt2 != null) {
			//	tt2.setText(p.getCategory().getId());
			//}

		}


		return v;
	}

}

/*


        Button button = (Button) convertView.findViewById(R.id.add_button);
        button.setTag(position);
        button.setOnClickListener(new View.OnClickListener(){
		@Override
		public void onClick(View view) {
		int position =(Integer) view.getTag();
		PlaceInfo objectinfo = getItem(position);

		}

        });

        // Return the completed view to render on screen
        return convertView;
    }
}
*/