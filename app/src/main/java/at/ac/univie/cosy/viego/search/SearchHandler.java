package at.ac.univie.cosy.viego.search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Mourni on 5/6/2017.
 */


// felder siehe
// https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Sydney&key=AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY

public class SearchHandler {

    public static PlaceInfo[] getPlaceInformation (String jString, String search_text) throws JSONException
    {
        JSONObject json = new JSONObject(jString);

        JSONArray results = json.getJSONArray("results");
        PlaceInfo[] returninfo = new PlaceInfo[results.length()];

        for (int i =0; i< results.length();i++){
            returninfo[i].place_name = results.getJSONObject(i).getString("name");
            returninfo[i].place_id = results.getJSONObject(i).getString("place_id");
            returninfo[i].formatted_address = results.getJSONObject(i).getString("formatted_address");
         //   returninfo[i].img_id = results.getJSONObject("photos").getJSONArray(0).getString("photo_reference");
        }

        return returninfo;


    }


// https://maps.googleapis.com/maps/api/place/nearbysearch/output?parameters


//https://maps.googleapis.com/maps/api/place/nearbysearch/output?parameters
//info duch place id  https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJN1t_tDeuEmsRUsoyG83frY4&key=YOUR_API_KEY

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=YOUR_API_KEY


}
