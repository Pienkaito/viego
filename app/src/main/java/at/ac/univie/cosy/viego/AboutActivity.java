package at.ac.univie.cosy.viego;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;


/**
 * AboutActivity beinhaltet Beschreibung der App
 * @author beringuelmarkanthony, mayerhubert, raphaelkolhaupt
 */

public class AboutActivity extends AppCompatActivity {
	//ActionBar actionBar = getActionBar();


	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		//actionBar.setDisplayHomeAsUpEnabled(true);
	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
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
