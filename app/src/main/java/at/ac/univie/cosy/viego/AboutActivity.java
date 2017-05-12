package at.ac.univie.cosy.viego;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by Mourni on 11.05.2017.
 */

public class AboutActivity extends AppCompatActivity {
	//ActionBar actionBar = getActionBar();

		protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);


		//actionBar.setDisplayHomeAsUpEnabled(true);
		}

	@Override

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
