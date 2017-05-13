package at.ac.univie.cosy.viego;

import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.HashSet;
import java.util.Iterator;

import at.ac.univie.cosy.viego.search.PlaceInfo;
import at.ac.univie.cosy.viego.search.SearchActivity;

/**
 * <p>Dazu gehoerende XML-Files:</p>
 * <p>res.layout:</p>
 * <ul>
 * <li>mainmenu_layout</li>
 * <p>Layout of the main menu without the navigation drawer</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>R.id.toolbar</li>
 * </ul></br>
 * <li>mainmenu_layout_with_nav</li>
 * <p>Layout of the main menu with the navigation drawer</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>R.id.nav_view</li>
 * </ul></br>
 * <li>navigation_drawer_header</li>
 * <p>Header of the navigation drawer, which contains a logo and the name of the app</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>R.id.nav_header_img</li>
 * <li>R.id.nav_header_link</li>
 * </ul></br>
 * <li>mainmenu_bottom_content</li>
 * <p>Everything that displays the bottom part of the main menu will be handled here</p>
 * <p>It has the following elements:</p>
 * <ul>
 * <li>Kill</li>
 * <li>Me</li>
 * </ul></br>
 * </ul>
 * <p>res.menu:</p></br>
 * <ul>
 * <li>mainmenu_navigation_drawer</li>
 * <li>search_app_bar</li>
 * </ul>
 *
 * @author raphaelkolhaupt, mayerhubert, beringuelmarkanthony
 */
public class TourPreviewActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener,
		OnMapReadyCallback,
		View.OnClickListener {

	private static final float minZoomFactor = 15.0f;
	private static final float maxZoomFactor = 18.0f;
	private GoogleMap gMap;
	private LatLng curcoord = new LatLng(48.208456, 16.373130);
	private LinearLayout layout_content;
	private ConstraintLayout layout_loading;
	private PolylineOptions createdPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tourpreview_layout);

		layout_content = (LinearLayout) findViewById(R.id.tourpreview_content);
		layout_content.setVisibility(View.GONE);

		layout_loading = (ConstraintLayout) findViewById(R.id.tourpreview_loading);
		layout_loading.setVisibility(View.VISIBLE);

		//App Bar init
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		//Main Menu Layout init
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mainmenu_drawer_layout);
		ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
				this,
				drawer,
				toolbar,
				R.string.navigation_drawer_open,
				R.string.navigation_drawer_close
		) {
		};
		drawer.setDrawerListener(toggle);
		toggle.syncState();

		//Google Maps init
		SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.gmap);
		mapFragment.getMapAsync(this);

		//Navigation Drawer
		NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
		navigationView.setNavigationItemSelectedListener(this);

		FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.gps_btn);
		myFab.setOnClickListener(this);

		/*
		//if jQuery != null, dann fuehre dass jQuery aus und fuelle bottom content mit den Daten und mach die View
		//Visible, ansonst unten weiter
		//https://en.wikipedia.org/w/api.php?action=query&format=json&list=geosearch&titles=Stephansdom&gscoord=48.224368%7C16.353041&gsradius=10000&gslimit=10
		String jString = null;
		String url = null;
		String titleOfstandort = "Stephansdom";
		String latitude = "48.224368";
		String longitude = "16.353041";

		url = "https://en.wikipedia.org/w/api.php?" + "action=query&format==json&list=geosearch&titles=" +
				titleOfstandort + "&gscoord=" + latitude + "|" + longitude + "&gsradius=10000&gslimit=10"
		;

			//Hide Bottom
			JSONObject json = null;
			try {
				json = new JSONObject(jString);
				JSONArray results = json.getJSONArray("results");

			} catch (JSONException e) {
				e.printStackTrace();
			}
			//ansonst
			bottom_content = (LinearLayout) findViewById(R.id.mainmenu_bottom_content);
			bottom_content.setVisibility(View.GONE);
			*/
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.gps_btn:

				break;

		}
	}

	/*
	Sets the app bar drawer with the elements from R.menu.search_app_bar
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	/*
	This method handles all the actions made in the app bar.
	The action bar will automatically handle clicks on the Home/Up button,
	so long as you specify a parent activity in AndroidManifest.xml.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
			default:
				break;
		}

		return super.onOptionsItemSelected(item);
	}

	/*
	This method handles all the actions made in the navigation drawer
   */
	@SuppressWarnings("StatementWithEmptyBody")
	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		int id = item.getItemId();

		switch (id) {
			case R.id.nav_001:
				break;
			case R.id.nav_002:
				//Start Tour
				break;
			case R.id.nav_003:
				break;
			case R.id.nav_004:
				break;
			default:
				break;
		}

		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mainmenu_drawer_layout);
		drawer.closeDrawer(GravityCompat.START);
		return true;
	}

	/*
	Method, that initiates the Google Map with default coordinates (Center of Vienna)
	Also limits the zoom factor by the constants minZoomFactor & maxZoomFactor
	 */
	@Override
	public void onMapReady(GoogleMap googleMap) {
		gMap = googleMap;

		/*
		gMap.addMarker(new MarkerOptions()
			.position(curcoord)
			.title("Viiiiiiii")
			.icon(BitmapDescriptorFactory.fromResource(R.drawable.common_full_open_on_phone))
		);
		*/
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curcoord, maxZoomFactor));
		gMap.setMinZoomPreference(minZoomFactor);
		gMap.setMaxZoomPreference(maxZoomFactor);
		gMap.setBuildingsEnabled(false);

		gMap.addPolyline(new PolylineOptions().add(
				curcoord,
				new LatLng(48.207602, 16.373008),
				new LatLng(48.206741, 16.373515)
				)
						.width(10)
						.color(Color.RED)
		);
	}

	private PolylineOptions createPath(HashSet<PlaceInfo> tourPlaceInfos) {
		Iterator<PlaceInfo> placeInfoIterator = tourPlaceInfos.iterator();
		while (placeInfoIterator.hasNext()) {
			PlaceInfo curr = placeInfoIterator.next();
			createdPath.add(getNearest(curr, tourPlaceInfos));
			placeInfoIterator.remove();
		}
		return createdPath;
	}

	private LatLng getNearest(PlaceInfo curr, HashSet<PlaceInfo> list) {
		LatLng startpoint = new LatLng(Double.valueOf(curr.loc_lat), Double.valueOf(curr.loc_lng));

		PlaceInfo firstSight = list.iterator().next();
		LatLng minCoordinates = new LatLng(Double.valueOf(firstSight.loc_lat), Double.valueOf(firstSight.loc_lng));
		double minDistance = getDistance(startpoint, minCoordinates);
		list.iterator().remove();

		for (PlaceInfo x : list) {
			LatLng endpoint = new LatLng(Double.valueOf(x.loc_lat), Double.valueOf(x.loc_lng));
			if (minDistance > getDistance(startpoint, endpoint)) {
				minCoordinates = endpoint;
				minDistance = getDistance(startpoint, endpoint);
			}
		}
		return minCoordinates;
	}

	private double getDistance(LatLng startpoint, LatLng endpoint) {
		Location pointA = new Location("point A");
		pointA.setLatitude(startpoint.latitude);
		pointA.setLongitude(startpoint.longitude);

		Location pointB = new Location("point B");
		pointB.setLatitude(endpoint.latitude);
		pointB.setLongitude(endpoint.longitude);
		return pointA.distanceTo(pointB);

	}

	private double deg2rad(double deg) {
		return (deg * Math.PI / 180.0);
	}

	private double rad2deg(double rad) {
		return (rad * 180 / Math.PI);
	}

	/*
	Method, that's being called, everytime the user presses the back button
	In this case it checks the current state of the navigation drawer and reacts accordingly
	 */
	@Override
	public void onBackPressed() {
		DrawerLayout drawer = (DrawerLayout) findViewById(R.id.mainmenu_drawer_layout);
		if (drawer.isDrawerOpen(GravityCompat.START)) {
			drawer.closeDrawer(GravityCompat.START);
		} else {
			super.onBackPressed();
		}
	}
}
