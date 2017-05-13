package at.ac.univie.cosy.viego;

import android.content.Intent;
import android.os.Bundle;
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
 * @author beringuelmarkanthony, mayerhubert, raphaelkolhaupt
 */
public class MainMenuActivity extends AppCompatActivity
		implements NavigationView.OnNavigationItemSelectedListener,
		OnMapReadyCallback,
		View.OnClickListener {

	private static final float minZoomFactor = 15.0f;
	private static final float maxZoomFactor = 18.0f;
	private GoogleMap gMap;
	private LatLng curcoord = null;
	private LinearLayout bottom_content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainmenu_layout_with_nav);

		SingletonPosition singleton = SingletonPosition.getInstance();
		curcoord = new LatLng(singleton.getCurrentlat(), singleton.getCurrentlong());

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


		bottom_content = (LinearLayout) findViewById(R.id.mainmenu_bottom_content);
		bottom_content.setVisibility(View.GONE);


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
		getMenuInflater().inflate(R.menu.search_app_bar, menu);
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
			case R.id.app_bar_search:
				Intent intent = new Intent(this, SearchActivity.class);
				startActivity(intent);
				//Enter Search bar things here
				break;
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
				Intent intent_search = new Intent(this, SearchActivity.class);
				startActivity(intent_search);
				break;
			case R.id.nav_002:
				//Start Tour
				break;
			case R.id.nav_003:
				Intent intent_settings = new Intent(this, SettingsActivity.class);
				startActivity(intent_settings);
				break;
			case R.id.nav_004:
				Intent intent_about = new Intent(this, AboutActivity.class);
				startActivity(intent_about);
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

		gMap.addPolyline(new PolylineOptions().add(
				curcoord,
				new LatLng(48.207602, 16.373008),
				new LatLng(48.206741, 16.373515)
				)
						.width(10)
						.color(Color.RED)
		);
		*/
		gMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curcoord, maxZoomFactor));
		gMap.setMinZoomPreference(minZoomFactor);
		gMap.setMaxZoomPreference(maxZoomFactor);
		gMap.setBuildingsEnabled(false);


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
