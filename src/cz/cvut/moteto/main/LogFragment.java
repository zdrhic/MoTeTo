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
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import cz.cvut.moteto.model.Task;

/**
 * 
 * @author Jan Zdrha
 */
public class LogFragment extends Fragment implements
		SelectedTaskChangedListener, OnGesturePerformedListener {

	private GestureLibrary gestureLib;
	List<Button> buttons;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		SessionActivity activity = (SessionActivity) getActivity();
		activity.addSelectedTaskChangedListener(this);
		// Load gestures from raw file 'gestures'
		gestureLib = GestureLibraries.fromRawResource(getActivity(), R.raw.gestures);
		// Inflate the layout for this fragment
		Context context = container.getContext();
		View view = inflater.inflate(R.layout.log, container, false);

		GestureOverlayView gestureoverlay = (GestureOverlayView) view.findViewById(R.id.gestures);
		gestureoverlay.addOnGesturePerformedListener(this);

		buttons = new ArrayList<Button>();
		Button marker = null;
		for (int i = 0; i < 9; i++) {
			marker = new Button(getActivity());
			marker.setText("Marker " + Integer.toString(i));
			marker.setId(i);
			marker.setOnClickListener(new MarkerOnClickListener(i));
			buttons.add(marker);
		}

		GridView gridView = (GridView) view.findViewById(R.id.grid_view);

		gridView.setAdapter(new ButtonAdapter(buttons));

		// return super.onCreateView(inflater, container, savedInstanceState);
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

		for (Prediction prediction : predictions) {
			if (prediction.score > 1.0) {
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
