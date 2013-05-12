package cz.cvut.moteto.main;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MarkerOnClickListener implements OnClickListener {
	private static final String TAG = "MarkerOnClickListener";
	private int position;
	
	public MarkerOnClickListener(int position) {
		this.position = position;
	}
	
	@Override
	public void onClick(View view) {
		//tady budeou nejaky case a switch hadam
		Log.v(TAG, "button index=" + Integer.toString(position));
	}

}
