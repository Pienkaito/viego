package at.ac.univie.cosy.viego.search;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
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
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import at.ac.univie.cosy.viego.MainMenuActivity;
import at.ac.univie.cosy.viego.R;
import at.ac.univie.cosy.viego.TourPreviewActivity;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Mourni on 07.05.2017.
 * https://github.com/codepath/android_guides/wiki/Using-an-ArrayAdapter-with-ListView
 */

public class SearchAdapter extends ArrayAdapter<PlaceInfo> {

	//TO-DO zu diesem Array sollten die Namen hinzugefuegt werden, die durch Klick auf den Add Button zur Tour
	//hinzugefuegt werden sollten
	public final static String apikey = "AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY";
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

			// wenn es ein Bild gibt, lade es herunter!
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