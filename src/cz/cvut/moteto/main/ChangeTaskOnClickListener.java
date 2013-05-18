package cz.cvut.moteto.main;

import java.util.List;

import cz.cvut.moteto.model.Task;
import android.util.Log;
import android.util.StringBuilderPrinter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

public class ChangeTaskOnClickListener implements OnClickListener {

	// Maximum number of chars for TextView (Set in Log.xml to 25), using for
	// displaying task name minus 3 chars for Task Number
	private static final int MAX_CHARS = 22;
	
	//Debugging TAG 
	private static final String TAG = "ChangeTaskOnClickListener";
	
	//SessionActivity for passing Test instance
	private SessionActivity activity;
	
	//Parent fragment container
	private ViewGroup container;

	public ChangeTaskOnClickListener(SessionActivity activity,
			ViewGroup container) {
		this.activity = activity;
		this.container = container;
	}

	@Override
	public void onClick(View view) {

		final ImageButton nextTaskBtn = (ImageButton) container
				.findViewById(R.id.following_task_btn);
		final ImageButton prevTaskBtn = (ImageButton) container
				.findViewById(R.id.previous_task_btn);
		final TextView selectedTaskTextView = (TextView) container
				.findViewById(R.id.current_task_text);

		// Get Selected Task
		Task selectedTask = activity.getSelectedTask();
		// Get List of all Task's
		List<Task> taskList = activity.getTest().getTasks();
		// Get Index of Selected Task in List
		int selectedTaskIndex = taskList.indexOf(selectedTask);

		Log.d(TAG, "Current Task Index: " + selectedTaskIndex);

		String newTaskName = null;
		int newTaskIndex = -1;
		// Depending on clicked button, increase or decrease index for task
		// and return corresponding one
		try {
			switch (view.getId()) {
			// NEXT TASK
			case R.id.following_task_btn:

				newTaskIndex = selectedTaskIndex + 1;
				Log.d(TAG, "Next Task selected");
				Log.d(TAG, "New Task Index: " + newTaskIndex);

				activity.setSelectedTask(taskList.get(newTaskIndex));
				newTaskName = taskList.get(newTaskIndex).getPath();

				break;
			// PREVIOUS TASK
			case R.id.previous_task_btn:

				newTaskIndex = selectedTaskIndex - 1;
				Log.d(TAG, "Prev Task selected");
				Log.d(TAG, "New Task Index: " + newTaskIndex);

				activity.setSelectedTask(taskList.get(newTaskIndex));
				newTaskName = taskList.get(newTaskIndex).getPath();

				break;
			default:
				// No way leading to this statement
				break;
			}
		} catch (IndexOutOfBoundsException ex) {
			// Do nothing, stay on current task
		}

		// Disable buttons while 0 or length-1 index hit
		if (newTaskIndex == taskList.size() - 1) {
			Log.d(TAG, "Disabling following button function");
			nextTaskBtn.setEnabled(false);
		} else {
			Log.d(TAG, "Enabling following button function");
			nextTaskBtn.setEnabled(true);
		}

		if (newTaskIndex == 0) {
			Log.d(TAG, "Disabling previous button function");
			prevTaskBtn.setEnabled(false);
		} else {
			Log.d(TAG, "Enabling previous button function");
			prevTaskBtn.setEnabled(true);
		}

		// TODO: Maybe create new method for this.
		
		StringBuilder sb = new StringBuilder();
		sb.append(Integer.toString(newTaskIndex + 1));
		sb.append(". ");

		if (newTaskName.length() >= MAX_CHARS) {
			sb.append(newTaskName);
			sb.delete(MAX_CHARS - 1, sb.length());
			sb.append("...");
		} else {
			sb.append(newTaskName);
		}

		//Finally, set task name to textview
		selectedTaskTextView.setText(sb.toString());
		
	}

}
