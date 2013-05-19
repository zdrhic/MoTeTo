package cz.cvut.moteto.main;

import java.util.Calendar;
import java.util.List;

import cz.cvut.moteto.model.Marker;
import cz.cvut.moteto.model.Note;
import cz.cvut.moteto.model.WorkSpace;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MarkerOnClickListener implements OnClickListener {
	private static final String TAG = "MarkerOnClickListener";
	private int position;
	SessionActivity activity;
	LogFragment fragment;
	
	public MarkerOnClickListener(SessionActivity activity, LogFragment fragment, int position) {
		this.position = position;
		this.activity = activity;
		this.fragment = fragment;
	}
	
	@Override
	public void onClick(View view) {
		Marker marker = activity.getSelectedMarkers().get(position);
		if (marker.hasChildren()) {
			activity.setSelectedMarkers(marker.getChildren());
			fragment.fillButtonList(marker.getChildren());
		} else {
			Log.v(TAG, "button index=" + Integer.toString(position));
			String taskName = activity.getSelectedTask().getPath();
			String markerName = marker.getName();
			Note note = new Note(Calendar.getInstance(), taskName+" - "+markerName);
			activity.getSession().addNote(note);
			Toast.makeText((Context)activity, markerName, Toast.LENGTH_SHORT).show();
			activity.setSelectedMarkers(activity.getSelectedTask().getMarkers());
			fragment.fillButtonList(activity.getSelectedTask().getMarkers());
		}
	}

}
