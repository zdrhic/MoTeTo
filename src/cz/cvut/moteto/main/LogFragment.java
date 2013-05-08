/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        return super.onCreateView(inflater, container, savedInstanceState);
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
