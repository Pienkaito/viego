package at.ac.univie.cosy.viego.search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author raphaelkolhaupt, mayerhubert, beringuelmarkanthony
 */
// places API key AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY

// felder siehe
// https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Sydney&key=AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY

public class SearchHandler {

    public static List<PlaceInfo> getPlaceInformation (String jString) throws JSONException
    {
        //Turn the string into a Json to parse.
        JSONObject json = new JSONObject(jString);
        //We always have to take this step. If i do it once here we don't have to redo it.
        JSONArray results = json.getJSONArray("results");

        List<PlaceInfo> returninfo = new LinkedList<>();

        for (int i=0; i< results.length(); i++){
			PlaceInfo nextitem = new PlaceInfo();

            //These fields alwys exist.
            nextitem.place_name = results.getJSONObject(i).getString("name");
            nextitem.place_id = results.getJSONObject(i).getString("place_id");

            //These fields could be missing
            if(results.getJSONObject(i).has("vicinity"))
                nextitem.formatted_address = results.getJSONObject(i).getString("vicinity");
            if(results.getJSONObject(i).has("rating"))
                nextitem.place_rating = results.getJSONObject(i).getString("rating");
            if(results.getJSONObject(i).has("photos")) {
                if (results.getJSONObject(i).getJSONArray("photos").getJSONObject(0).has("photo_reference"))
                    nextitem.place_img_id = results.getJSONObject(i).getJSONArray("photos").getJSONObject(0).getString("photo_reference");
            }
                if(results.getJSONObject(i).getJSONObject("geometry").has("location")) {
                nextitem.loc_lat = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                nextitem.loc_lng = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
            }
                returninfo.add(nextitem);
        }
        return returninfo;
    }
}
