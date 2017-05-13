package at.ac.univie.cosy.viego.search;

import java.io.Serializable;

/**
 * Created by Mourni on 5/6/2017.
 */
@SuppressWarnings("serial")
public class PlaceInfo implements Serializable {
    public String place_id = null;
    public String place_img_id = null;
    public String formatted_address = null;
    public String loc_lng = null;
    public String loc_lat = null;
    public String place_name = null;
    public String place_rating = null;
}