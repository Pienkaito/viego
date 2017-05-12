package at.ac.univie.cosy.viego.search;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Mourni on 5/6/2017.
 */
// places API key AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY

// felder siehe
// https://maps.googleapis.com/maps/api/place/textsearch/json?query=restaurants+in+Sydney&key=AIzaSyAahAPIqHgVnBjMziAK_I8Vce0wmkEycFY

public class SearchHandler {

    //pagetoken   erlaubt es uns die nächsten 20 Ergebnisse abzufragen!

    /*
    String url =   MUSS MAN ZUSAMMENSTÜCKELN!


    TO BE MOVED TO SEARCH ACTIVITY

    OkHttpClient client = new OkHttpClient();

String run(String url) throws IOException {
  Request request = new Request.Builder()
      .url(url)
      .build();

  Response response = client.newCall(request).execute();
  return response.body().string();
}
    */
    public static List<PlaceInfo> getPlaceInformation (String jString) throws JSONException
    {

        JSONObject json = new JSONObject(jString);

        JSONArray results = json.getJSONArray("results");




        List<PlaceInfo> returninfo = new LinkedList<>();

        for (int i=0; i< results.length(); i++){
			PlaceInfo nextitem = new PlaceInfo();
            nextitem.place_name = results.getJSONObject(i).getString("name");
            nextitem.place_id = results.getJSONObject(i).getString("place_id");
            if(results.getJSONObject(i).has("vicinity"))
                nextitem.formatted_address = results.getJSONObject(i).getString("vicinity");
            if(results.getJSONObject(i).has("rating"))
                nextitem.place_rating = results.getJSONObject(i).getString("rating");
            if(results.getJSONObject(i).getJSONArray("photos").getJSONObject(0).has("photo_reference"))
                nextitem.place_img_id = results.getJSONObject(i).getJSONArray("photos").getJSONObject(0).getString("photo_reference");
            if(results.getJSONObject(i).getJSONObject("geometry").has("location")) {
                nextitem.loc_lat = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                nextitem.loc_lng = results.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
            }
                returninfo.add(nextitem);
        }
        /*
        PlaceInfo[] returninfo = new PlaceInfo[results.length()];
        for (int i =0; i< results.length();i++){
            returninfo[i].place_name = results.getJSONObject(i).getString("name");
            returninfo[i].place_id = results.getJSONObject(i).getString("place_id");
            returninfo[i].formatted_address = results.getJSONObject(i).getString("formatted_address");
            returninfo[i].place_rating = results.getJSONObject(i).getString("rating");

            returninfo[i].place_img_id = results.getJSONObject(i).getJSONArray("photos").getJSONObject(0).getString("photo_reference");
        }
        */
        return returninfo;
    }


// https://maps.googleapis.com/maps/api/place/nearbysearch/output?parameters


//https://maps.googleapis.com/maps/api/place/nearbysearch/output?parameters
//info duch place id  https://maps.googleapis.com/maps/api/place/details/json?placeid=ChIJN1t_tDeuEmsRUsoyG83frY4&key=YOUR_API_KEY

//https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=-33.8670522,151.1957362&radius=500&type=restaurant&keyword=cruise&key=YOUR_API_KEY


}
