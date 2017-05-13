package at.ac.univie.cosy.viego.search;

import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import at.ac.univie.cosy.viego.R;
/**
 * Dazu gehoerende XML-Files:<br>
 *    list_item_place<br>
 * @author raphaelkolhaupt, mayerhubert, beringuelmarkanthony
 */


//This is a custom adapter that applies our data from PlaceInfo to our custom list item and returns the view
public class SearchAdapter extends ArrayAdapter<PlaceInfo> {


	public final static String apikey = "AIzaSyCo-ALqUgeiisJYac9D9Sog8E3VK9xHv74";
	HashSet<PlaceInfo> tourPlaceInfos = new HashSet<PlaceInfo>();

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

			//if there is a picture reference, we download the picture!
			if (p.place_img_id != null) {
				new DownloadImageFromAPI((ImageView) v.findViewById(R.id.img_place))
						.execute("https://maps.googleapis.com/maps/api/place/photo?" +
								"maxheight=200&" +
								"maxwidth=200&" +
								"photoreference=" + p.place_img_id +
								"&key=" + apikey
						);
			}
			place_name.setText(p.place_name);
			place_description.setText(p.formatted_address);

			Button button = (Button) v.findViewById(R.id.list_button_add);
			button.setTag(position);
			button.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					int position = (Integer) view.getTag();
					PlaceInfo objectinfo = getItem(position);
					tourPlaceInfos.add(objectinfo);
					Toast.makeText(getContext(), "You clicked a button yay", Toast.LENGTH_LONG).show();
				}
			});




		}
		return v;
	}

	private class DownloadImageFromAPI extends AsyncTask<String, Void, Bitmap> {
		ImageView Image;

		private DownloadImageFromAPI(ImageView Image) {
			this.Image = Image;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap return_pic = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				return_pic = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return return_pic;
		}

		protected void onPostExecute(Bitmap result) {
			Image.setImageBitmap(result);
		}
	}


}

/*



        // Return the completed view to render on screen
        return convertView;
    }
}
*/