/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ctc.android.widget.ImageMap;

import cz.cvut.moteto.model.Location;
import cz.cvut.moteto.model.Task;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;

/**
 *
 * @author Jan Zdrha
 */
public class MapViewFragment extends Fragment {

	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		ImageMap mImageMap = createMap();
		
		// add a click handler to react when areas are tapped
        mImageMap.addOnImageMapClickedHandler(new ImageMap.OnImageMapClickedHandler() {
            @Override
            public void onImageMapClicked(int id) {
                // when the area is tapped, show the name in a
                // text bubble
                ((ImageMap)getView()).showBubble(id);
            }
 
            @Override
            public void onBubbleClicked(int id) {
                // react to info bubble for area being tapped
            }
        });
		
		return mImageMap;
	}
	
	public ImageMap createMap() {
		Test test = WorkSpace.getInstance().getCurrentTest();
		List<Location> locations = test.getLocations();
		
        ImageMap imageView = new ImageMap((Context)getActivity());
        
        
        for (int i = 0; i < locations.size(); i+=1) {
        	Location l = locations.get(i);
        	imageView.addDotArea(i+1, l.getName(), (float)l.getX(), (float)l.getY(), 50);
        }
        
        if (test.getMapPath() != null) {
        	Bitmap bm = BitmapFactory.decodeFile(test.getMapPath());
        	imageView.setImageBitmap(bm);
        }
        imageView.setVisibility(View.VISIBLE);
        return imageView;
	}
	
	public void showLocation(Task task) {
		if (task.getLocation() >= 0) {
			ImageMap map = ((ImageMap)getView());
			map.centerAndShowArea(task.getLocation()+1);
		}
	}
}
