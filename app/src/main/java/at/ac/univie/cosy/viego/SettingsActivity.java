package at.ac.univie.cosy.viego;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.Spinner;


import static android.app.PendingIntent.getActivity;


/**
 * Created by Mourni on 11.05.2017.
 */
/*
final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
String value=(mSharedPreference.getString("NameOfShared", "Default_Value"));


^this code allows us to get the shared preference from a different activity.
 */


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

