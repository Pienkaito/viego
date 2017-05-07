package at.ac.univie.cosy.viego.search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import at.ac.univie.cosy.viego.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkBox2:
                if (checked)
                {

                }
                // Put some meat on the sandwich
            else
                // Remove the meat
                break;
            case R.id.checkBox3:
                if (checked)
                {

                }
                else
                {
                    //asdf
                }
                // I'm lactose intolerant
                break;
            // TODO: Veggie sandwich
        }
                // Cheese me

        }

    }
