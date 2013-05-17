package cz.cvut.moteto.main;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class ChangeTaskOnClickListener implements OnClickListener {
	
	private SessionActivity activity;
	
	
	public ChangeTaskOnClickListener(SessionActivity activity ) {
		this.activity = activity;
	}
	
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.following_task_btn:
//			activity.getSession().getTest().get
			break;
		}
		
		//TODO: Fix this code. Throws Null Pointer exception on next line. IDK why...
//		TextView selectedTaskTextView = (TextView) view.findViewById(R.id.current_task_text);
//		String text = (String) selectedTaskTextView.getText();
//		text = text.substring(0, text.length() - 4);
//		selectedTaskTextView.setText(text + "...");
	}

}
