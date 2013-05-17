/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
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
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import cz.cvut.moteto.model.Marker;
import cz.cvut.moteto.model.Note;
import cz.cvut.moteto.model.Task;

/**
 * 
 * @author Jan Zdrha
 */
public class LogFragment extends Fragment implements
		SelectedTaskChangedListener, OnGesturePerformedListener {

	// TODO: solve button size - find a flexible way for wider set of devices
	// used while generating buttons
	private final static int MARKER_BUTTON_SIZE = 150;
	// Gesture Library for loading and recognizing gestures
	private GestureLibrary gestureLib;
	// Array of buttons, used as markers
	private List<Button> buttons;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final SessionActivity sessionActivity = (SessionActivity) getActivity();
		sessionActivity.addSelectedTaskChangedListener(this);

		// Load gestures from raw file 'gestures'
		gestureLib = GestureLibraries.fromRawResource(getActivity(),
				R.raw.gestures);
		if (!gestureLib.load()) {
			getActivity().finish();
		}

		// Inflate the layout for this fragment
		final View view = inflater.inflate(R.layout.log, container, false);

		// Set listeners for switching task buttons and set SelectedTask text
		final ImageButton followingTaskBtn = (ImageButton) view.findViewById(R.id.following_task_btn);
		followingTaskBtn.setOnClickListener(new ChangeTaskOnClickListener(sessionActivity));		
		final ImageButton previousTaskBtn = (ImageButton) view.findViewById(R.id.previous_task_btn);
		previousTaskBtn.setOnClickListener(new ChangeTaskOnClickListener(sessionActivity));
		TextView selectedTaskTextView = (TextView) view.findViewById(R.id.current_task_text);
		selectedTaskTextView.setText(sessionActivity.getSelectedTask().getPath());
		
		//Replace last characters from Task name by "...".
		String text = (String) selectedTaskTextView.getText();
		text = text.substring(0, text.length() - 4);
		selectedTaskTextView.setText(text + "...");
		
		
		
		
		// Button for adding hand-write notes (flexible marker)
		final Button addNoteBtn = (Button) view.findViewById(R.id.add_note_btn);
		addNoteBtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				EditText editText = ((EditText) view.findViewById(R.id.note_text));
				String taskName = sessionActivity.getSelectedTask().getPath();
				String markerName = editText.getText().toString();
				Note note = new Note(Calendar.getInstance(), taskName + " - "
						+ markerName);
				sessionActivity.getSession().addNote(note);
				Toast.makeText((Context) sessionActivity, markerName,
						Toast.LENGTH_SHORT).show();
				editText.getText().clear();
			}
		});

		// create GestureOverlayView for gestures which is top layout
		GestureOverlayView gestureoverlay = (GestureOverlayView) view
				.findViewById(R.id.gestures);
		gestureoverlay.addOnGesturePerformedListener(this);

		List<Marker> markers = ((SessionActivity) getActivity())
				.getSelectedTask().getMarkers();

		// Fill list "buttons" with markers
		buttons = new ArrayList<Button>();
		Button markerBtn = null;
		for (int i = 0; i < markers.size(); i++) {
			markerBtn = new Button(getActivity());
			markerBtn.setText(markers.get(i).getName());
			markerBtn.setId(i);
			markerBtn.setHeight(MARKER_BUTTON_SIZE);
			markerBtn.setWidth(MARKER_BUTTON_SIZE);

			markerBtn
					.setOnClickListener(new MarkerOnClickListener(sessionActivity, i));
			buttons.add(markerBtn);
		}

		// gridview for buttons(markers)
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
		
		//Returns ArrayList of predictions sorted by highest score
		ArrayList<Prediction> predictions = gestureLib.recognize(gesture);

		// Walk through predictions and find corresponding name to gesture
		for (Prediction prediction : predictions) {
			Log.v("LOGFRAGMENT", "gesture score " + prediction.score);
			// usually with mouse in emulator you get something between 3 - 7
			if (prediction.score > 5.0) {
				String gestureName = prediction.name;
				try {
					if (gestureName.equals("marker1")) {
						buttons.get(0).performClick();
					} else if (gestureName.equals("marker2")) {
						buttons.get(1).performClick();
					} else if (gestureName.equals("marker3")) {
						buttons.get(2).performClick();
					} else if (gestureName.equals("marker4")) {
						buttons.get(3).performClick();
					} else if (gestureName.equals("marker5")) {
						buttons.get(4).performClick();
					} else if (gestureName.equals("marker6")) {
						buttons.get(5).performClick();
					} else if (gestureName.equals("marker7")) {
						buttons.get(6).performClick();
					} else if (gestureName.equals("marker8")) {
						buttons.get(7).performClick();
					} else if (gestureName.equals("marker9")) {
						buttons.get(8).performClick();
					}
				} catch (IndexOutOfBoundsException ex) {
					//Happens when gesture MarkerX is recognized and there are 
					//less markers in Task Class. 
					Toast.makeText((Context) getActivity(), "Marker isn't defined",
							Toast.LENGTH_SHORT).show();
				}

				// Debug variable
				int position = predictions.indexOf(prediction);
				
				Log.v("LOGFRAGMENT", "button clicked: " + position);
				Log.v("LOGFRAGMENT", "gesture name: " + prediction.name);
				//When gesture is found - we're done
				return;
				
			}
		}

	}

}

