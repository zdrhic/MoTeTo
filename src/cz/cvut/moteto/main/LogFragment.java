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

	// TODO: solve this shit :D
	// used while generating buttons
	private final static int MARKER_BUTTON_SIZE = 150;
	private GestureLibrary gestureLib;
	List<Button> buttons;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		final SessionActivity activity = (SessionActivity) getActivity();
		activity.addSelectedTaskChangedListener(this);

		// Load gestures from raw file 'gestures'
		gestureLib = GestureLibraries.fromRawResource(getActivity(),
				R.raw.gestures);
		if (!gestureLib.load()) {
			getActivity().finish();
		}

		// Inflate the layout for this fragment
		final View view = inflater.inflate(R.layout.log, container, false);

		final Button help = (Button) view.findViewById(R.id.add_note_btn);
		help.setOnClickListener(new View.OnClickListener() {
			public void onClick(View arg0) {
				EditText t = ((EditText) view.findViewById(R.id.note_text));
				String taskName = activity.getSelectedTask().getPath();
				String markerName = t.getText().toString();
				Note note = new Note(Calendar.getInstance(), taskName + " - "
						+ markerName);
				activity.getSession().addNote(note);
				Toast.makeText((Context) activity, markerName,
						Toast.LENGTH_SHORT).show();
				t.getText().clear();
			}
		});

		// create GestureOverlayView for ... gestures, maybe?
		GestureOverlayView gestureoverlay = (GestureOverlayView) view
				.findViewById(R.id.gestures);
		gestureoverlay.addOnGesturePerformedListener(this);

		List<Marker> markers = ((SessionActivity) getActivity())
				.getSelectedTask().getMarkers();

		// create 9 sexy buttons
		buttons = new ArrayList<Button>();
		Button markerBtn = null;
		for (int i = 0; i < markers.size(); i++) {
			markerBtn = new Button(getActivity());
			markerBtn.setText(markers.get(i).getName());
			markerBtn.setId(i);
			markerBtn.setHeight(MARKER_BUTTON_SIZE);
			markerBtn.setWidth(MARKER_BUTTON_SIZE);

			markerBtn
					.setOnClickListener(new MarkerOnClickListener(activity, i));
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
		ArrayList<Prediction> predictions = gestureLib.recognize(gesture);

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

				int position = predictions.indexOf(prediction);
				Log.v("LOGFRAGMENT", "button clicked: " + position);
				Log.v("LOGFRAGMENT", "gesture name: " + prediction.name);
//				buttons.get(position).performClick();
				return;
				// some action
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
