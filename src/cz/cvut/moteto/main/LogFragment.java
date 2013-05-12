/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import cz.cvut.moteto.model.Task;

/**
 *
 * @author Jan Zdrha
 */
public class LogFragment extends Fragment implements SelectedTaskChangedListener {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SessionActivity activity = (SessionActivity) getActivity();
        activity.addSelectedTaskChangedListener(this);
     // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.log, container, false);
        
        List<Button> buttons = new ArrayList<Button>();
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
        
//        return super.onCreateView(inflater, container, savedInstanceState);
        return view;
    }

    public void selectedTaskChanged(Task selestedTask) {
        this.setCurrentTask(selestedTask);
    }

    private void setCurrentTask(Task selestedTask) {
        getActivity().setTitle(selestedTask.toString());
    }
    
    
}
/*

 vylistování markerů:

 task.getMarkers();

 if (marker.getChildren().size() > 0) {
 // je to kategorie s podmarkerama
 } {
 // je to jen marker - po kliknutí přidat note
 }



 přidání poznámky:

 String str = marker.getName();
 session.addNote(str);

 */
