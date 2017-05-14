package at.ac.univie.cosy.viego;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


/**
 * AboutActivity beinhaltet Beschreibung der App
 *
 * @author beringuelmarkanthony, mayerhubert, raphaelkolhaupt
 */

public class AboutActivity extends AppCompatActivity {

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
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
