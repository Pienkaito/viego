package at.ac.univie.cosy.viego.search;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.InputStream;
import java.io.Serializable;

/**
 * Created by Mourni on 5/6/2017.
 */
@SuppressWarnings("serial")
public class PlaceInfo implements Serializable {
    public String place_id = null;
    public String place_img_id = null;
    public String formatted_address = null;
	Bitmap picture;
    public String loc_lng = null;
    public String loc_lat = null;
    public String place_name = null;
    public String place_rating = null;

	public void getPicture(String place_img_id){
		try {
			InputStream in = new java.net.URL("https://maps.googleapis.com/maps/api/place/photo?" +
					"maxheight=200&" +
					"maxwidth=200&" +
					"photoreference=" + place_img_id +
					"&key=" + "AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY"
			).openStream();
			picture = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
	}
}