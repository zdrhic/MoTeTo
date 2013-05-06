/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.ctc.android.widget.ImageMap;

import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 *
 * @author Jan Zdrha
 */
public class MapFragment extends Fragment {
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		return createMap();
	}
	
	public View createMap() {
		Test test = WorkSpace.getInstance().getCurrentTest();
		Document doc = test.getDocument();
		Element map = (Element) doc.getElementsByTagName("map").item(0);

		String filename = map.getElementsByTagName("filename").item(0).getTextContent();
		filename = WorkSpace.getInstance().getWorkspaceFolder()+"/"+filename;
		Bitmap bm = BitmapFactory.decodeFile(filename);

        ImageMap imageView = new ImageMap((Context)getActivity());
        imageView.setImageBitmap(bm);
        imageView.setVisibility(View.VISIBLE);
        return imageView;
	}
}
