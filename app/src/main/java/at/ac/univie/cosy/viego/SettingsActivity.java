package at.ac.univie.cosy.viego;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/**
 * @author beringuelmarkanthony, mayerhubert, raphaelkolhaupt
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

		final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.settings_radiogroup);
		radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
		{
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				RadioButton checked = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
				prefs = getSharedPreferences(settingsTAG, 0);
				SharedPreferences.Editor editor = prefs.edit();

				editor.putBoolean("km", km.isChecked());
				editor.putBoolean("miles", miles.isChecked());
				editor.commit();
				finish();
			}
		});

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				// If we click the back button, we close the activity and return to the previous.
				this.finish();
				return true;
			default:
				return super.onOptionsItemSelected(item);
		}
	}
}
