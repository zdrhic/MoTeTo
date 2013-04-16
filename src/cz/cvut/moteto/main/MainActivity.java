package cz.cvut.moteto.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final Button tests = (Button) findViewById(R.id.tests);
        tests.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, TestsActivity.class);                
                MainActivity.this.startActivity(myIntent);
            }
        });

        final Button help = (Button) findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, HelpActivity.class);                
                MainActivity.this.startActivity(myIntent);
            }
        });

        final Button properties = (Button) findViewById(R.id.properties);
        properties.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MainActivity.this, PropertiesActivity.class);                
                MainActivity.this.startActivity(myIntent);
            }
        });


    }
}
