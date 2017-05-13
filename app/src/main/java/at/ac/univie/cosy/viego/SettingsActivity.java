package at.ac.univie.cosy.viego;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;


import static android.app.PendingIntent.getActivity;


/**
 * @author beringuelmarkanthony, mayerhubert, raphaelkolhaupt
 */

/*
ADD TO SEARCH ACTIVITY
String settingsTAG = "AppNameSettings";
SharedPreferences prefs = getSharedPreferences(settingsTAG, 0);
boolean miles = prefs.getBoolean("miles", false);
if(miles == true){
    populate array with mile stuff
    Textview meter says "Miles" instead.

    if(miles== true) distance = miles * 1,6 oder so
}
 */

public class SettingsActivity extends AppCompatActivity {
	private String settingsTAG = "ViegoSettings";
	private SharedPreferences prefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_settings);
		prefs = getSharedPreferences(settingsTAG, 0);

		final RadioButton km = (RadioButton) findViewById(R.id.settings_radio_km);
		final RadioButton miles = (RadioButton) findViewById(R.id.settings_radio_miles);

		km.setChecked(prefs.getBoolean("km", true));
		miles.setChecked(prefs.getBoolean("miles", false));
		Button btnSave = (Button) findViewById(R.id.settings_button_save);

		btnSave.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				prefs = getSharedPreferences(settingsTAG, 0);
				SharedPreferences.Editor editor = prefs.edit();

				editor.putBoolean("km", km.isChecked());
				editor.putBoolean("miles", miles.isChecked());
				editor.commit();

				finish();

			}
		});

	}

	public boolean onOptionsItemSelected (MenuItem item){
		switch (item.getItemId()) {
			case android.R.id.home:
				// app icon in action bar clicked; goto parent activity.
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}

/*
public class SettingsActivity extends AppCompatActivity {

	protected void onCreate(Bundle savedInstanceState) {


		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_settings);


	}

	public void onRadioButtonClicked(View view) {
		Context context = getApplicationContext();
		SharedPreferences sharedPref = context.getSharedPreferences(
				String.valueOf(R.string.pref_format), Context.MODE_PRIVATE
		);

		SharedPreferences.Editor editor = sharedPref.edit();

		// Is the button now checked?
		boolean checked = ((RadioButton) view).isChecked();

		// Check which radio button was clicked
		switch(view.getId()) {
			case R.id.settings_radio_km:
				if (checked)
					editor.putString(String.valueOf(R.string.pref_format), "km");
					editor.commit();
				break;
			case R.id.settings_radio_miles:
				if (checked)
					editor.putString(String.valueOf(R.string.pref_format), "miles");
				editor.commit();
				break;
		}
	}
}
*/
