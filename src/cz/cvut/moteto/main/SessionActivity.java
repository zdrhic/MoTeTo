/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import cz.cvut.moteto.main.R;
import cz.cvut.moteto.main.LogViewFragment;
import cz.cvut.moteto.main.MapViewFragment;
import cz.cvut.moteto.model.Marker;
import cz.cvut.moteto.model.Session;
import cz.cvut.moteto.model.Task;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import cz.cvut.moteto.utils.TabListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Jan Zdrha
 */
public class SessionActivity extends Activity {

    public static final int TAB_TASKLIST = 0;
    public static final int TAB_LOG = 1;
    public static final int TAB_MAP = 2;
    public static final int TAB_STREAM = 3;
    private Session session;
    private Test test;
    private Timer myTimer;
    private static final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private Task selectedTask = null;
    private List<Marker> selectedMarkers = null;
    private List<SelectedTaskChangedListener> selectedTaskListeners;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent();
        this.session = (Session) intent.getSerializableExtra("session");
        this.session.start();
        this.setTest((Test) intent.getSerializableExtra("test"));

        selectedTaskListeners = new LinkedList<SelectedTaskChangedListener>();
        this.setSelectedTask(test.getTasks().get(0));

        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.tasklist)
                .setTabListener(new TabListener<TasklistFragment>(
                this, TasklistFragment.class.getName(), TasklistFragment.class));
        actionBar.addTab(tab, TAB_TASKLIST);

        tab = actionBar.newTab()
                .setText(R.string.log)
                .setTabListener(new TabListener<LogFragment>(
                this, LogFragment.class.getName(), LogFragment.class));
        actionBar.addTab(tab, TAB_LOG);

        tab = actionBar.newTab()
                .setText(R.string.map)
                .setTabListener(new TabListener<MapFragment>(
                this, MapViewFragment.class.getName(), MapFragment.class));
        actionBar.addTab(tab, TAB_MAP);

        tab = actionBar.newTab()
                .setText(R.string.stream)
                .setTabListener(new TabListener<MapFragment>(
                this, MapViewFragment.class.getName(), MapFragment.class));
        actionBar.addTab(tab, TAB_STREAM);

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

        builder.setTitle("End Session");
        builder.setMessage("Do you really want to end the current session?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                session.end();
                try {
					session.save();
				} catch (Exception e) {
					WorkSpace.getInstance().showErrorDialog((Context)getParent(), (String)getText(R.string.error_saving_session), e.getLocalizedMessage());
				}
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

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public void addSelectedTaskChangedListener(SelectedTaskChangedListener listener) {
        selectedTaskListeners.add(listener);
    }

    public Task getSelectedTask() {
        return this.selectedTask;
    }

    public void setSelectedTask(Task task) {
        this.selectedTask = task;
        this.selectedMarkers = task.getMarkers();
        for (SelectedTaskChangedListener listener : this.selectedTaskListeners) {
            listener.selectedTaskChanged(this.selectedTask);
        }
    }

    public void setFragment(int tabIndex) {
        ActionBar actionBar = getActionBar();
        actionBar.setSelectedNavigationItem(tabIndex);
    }

	public List<Marker> getSelectedMarkers() {
		return selectedMarkers;
	}

	public void setSelectedMarkers(List<Marker> markers) {
		this.selectedMarkers = markers;		
	}
}
