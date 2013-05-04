/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import cz.cvut.moteto.main.R;
import cz.cvut.moteto.main.LogViewFragment;
import cz.cvut.moteto.main.MapViewFragment;
import cz.cvut.moteto.model.Session;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.utils.TabListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jan Zdrha
 */
public class SessionActivity extends Activity {

    private Session session;
    private Test test;
    private Timer myTimer;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent();
        this.session = (Session) intent.getSerializableExtra("session");
        this.session.start();
        this.test = (Test) intent.getSerializableExtra("test");

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.tasklist)
                .setTabListener(new TabListener<MapViewFragment>(
                this, MapViewFragment.class.getName(), MapViewFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.log)
                .setTabListener(new TabListener<LogViewFragment>(
                this, LogViewFragment.class.getName(), LogViewFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.map)
                .setTabListener(new TabListener<MapViewFragment>(
                this, MapViewFragment.class.getName(), MapViewFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.stream)
                .setTabListener(new TabListener<MapViewFragment>(
                this, MapViewFragment.class.getName(), MapViewFragment.class));
        actionBar.addTab(tab);

        //from http://stackoverflow.com/questions/4597690/android-timer-how
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        }, 0, 1000);
    }

    private void TimerMethod() {
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(Timer_Tick);
    }
    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            //This method runs in the same thread as the UI.               
            //Do something to the UI thread here
            long diffInMillisec = Calendar.getInstance().getTimeInMillis() - session.getBeginning().getTimeInMillis();
            Calendar diff = Calendar.getInstance();
            diff.setTimeInMillis(diffInMillisec);
            String time = sdf.format(diff.getTime());
            setTitle("Time: " + time);
        }
    };

    public Session getSession() {
        return this.session;
    }

    private void endSessionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                session.end();
                finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                endSessionDialog();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
