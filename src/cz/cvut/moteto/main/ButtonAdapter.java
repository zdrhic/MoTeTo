package cz.cvut.moteto.main;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

public class ButtonAdapter extends BaseAdapter{
	
	private Context context;
	private List<Button> buttons;
	
	public ButtonAdapter(List<Button> buttons) {
		this.buttons = buttons;
	}
	
	@Override
	public int getCount() {
		return buttons.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return buttons.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Button button;
		if (convertView == null) {
			button = buttons.get(position);
		} else {
			button = (Button) convertView;
		}
		
		return button;
	}

}
