package at.ac.univie.cosy.viego;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainMenu extends AppCompatActivity implements View.OnClickListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Button btnOne = (Button) findViewById(R.id.btn_maps);
        btnOne.setOnClickListener(this);
    }

    /*
    onClick forwards the user to the next activity
    by checking the id that has been activated on the View.
    It also passes the choice to the next activity
     */
    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MapsTest.class);
        int req_code = 0;

        switch (v.getId()) {
            case R.id.btn_maps:
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
