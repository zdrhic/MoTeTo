/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.gesture.Prediction;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import cz.cvut.moteto.model.Task;

/**
 * 
 * @author Jan Zdrha
 */
public class LogFragment extends Fragment implements
		SelectedTaskChangedListener, OnGesturePerformedListener {

	//TODO: solve this shit :D
	//used while generating buttons
	private final static int MARKER_BUTTON_SIZE = 150;
	private GestureLibrary gestureLib;
	List<Button> buttons;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SessionActivity activity = (SessionActivity) getActivity();
		activity.addSelectedTaskChangedListener(this);
		
		// Load gestures from raw file 'gestures'
		gestureLib = GestureLibraries.fromRawResource(getActivity(), R.raw.gestures);
		if (!gestureLib.load())
		{
			getActivity().finish();
		}
		
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.log, container, false);

		//create GestureOverlayView for ... gestures, maybe?
		GestureOverlayView gestureoverlay = (GestureOverlayView) view.findViewById(R.id.gestures);
		gestureoverlay.addOnGesturePerformedListener(this);

		//create 9 sexy buttons
		buttons = new ArrayList<Button>();
		Button marker = null;
		for (int i = 0; i < 9; i++) {
			marker = new Button(getActivity());
			marker.setText("Marker " + Integer.toString(i));
			marker.setId(i);
			marker.setHeight(MARKER_BUTTON_SIZE);
			marker.setWidth(MARKER_BUTTON_SIZE);
			marker.setOnClickListener(new MarkerOnClickListener(i));
			buttons.add(marker);
		}

		//gridview for buttons(markers)
		GridView gridView = (GridView) view.findViewById(R.id.grid_view);
		gridView.setAdapter(new ButtonAdapter(buttons));

		return view;
	}

	public void selectedTaskChanged(Task selestedTask) {
		this.setCurrentTask(selestedTask);
	}

	private void setCurrentTask(Task selestedTask) {
		getActivity().setTitle(selestedTask.toString());
	}

	
	@Override
	public void onGesturePerformed(GestureOverlayView overlay, Gesture gesture) {
		ArrayList<Prediction> predictions = gestureLib.recognize(gesture);
		Log.v("LOGFRAGMENT", "gesture found " + Integer.toString(predictions.size()));
		for (Prediction prediction : predictions) {
			Log.v("LOGFRAGMENT", "gesture score " + prediction.score);
			//usually with mouse in emulator you get something between 3 - 7
			if (prediction.score > 3.0) {
				
				int position = predictions.indexOf(prediction);
				Log.v("LOGFRAGMENT", "gesture RECOGNIZED " + Integer.toString(position));
				
				
				 Toast.makeText(getActivity(),"Button clicked " + Integer.toString(position),Toast.LENGTH_SHORT).show();
				 
				buttons.get(position).performClick();
				//some action
			}
		}

	}

}
/*
 * 
 * vylistování markerů:
 * 
 * task.getMarkers();
 * 
 * if (marker.getChildren().size() > 0) { // je to kategorie s podmarkerama } {
 * // je to jen marker - po kliknutí přidat note }
 * 
 * 
 * 
 * přidání poznámky:
 * 
 * String str = marker.getName(); session.addNote(str);
 */
