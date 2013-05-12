package cz.cvut.moteto.main;

import java.io.File;
import java.util.ArrayList;

import com.kaloer.filepicker.FilePickerActivity;

import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private static final int REQUEST_PICK_FILE = 1;
	
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
            	Intent intent = new Intent(MainActivity.this, TestsActivity.class);

    			// Set the initial directory to be the sdcard
            	intent.putExtra(FilePickerActivity.EXTRA_FILE_PATH, WorkSpace.getInstance().getWorkspaceFolder());
    			
    			// Show hidden files
    			//intent.putExtra(FilePickerActivity.EXTRA_SHOW_HIDDEN_FILES, true);
    			
    			// Only make .xml files visible
    			ArrayList<String> extensions = new ArrayList<String>();
    			extensions.add(".xml");
    			intent.putExtra(FilePickerActivity.EXTRA_ACCEPTED_FILE_EXTENSIONS, extensions);
    			
    			// Start the activity
    			startActivityForResult(intent, REQUEST_PICK_FILE);
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
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if(resultCode == RESULT_OK) {
			switch(requestCode) {
			case REQUEST_PICK_FILE:
				if(data.hasExtra(FilePickerActivity.EXTRA_FILE_PATH)) {
					// Get the file path
					File f = new File(data.getStringExtra(FilePickerActivity.EXTRA_FILE_PATH));
					WorkSpace.getInstance().setWorkspaceFolder(f.getParentFile().getAbsolutePath());
			        Test test = new Test(f.getPath());
			        Intent myIntent = new Intent(MainActivity.this, TestActivity.class);
			        myIntent.putExtra("test", test);
			        MainActivity.this.startActivity(myIntent);
				}
			}
		}
	}
}
