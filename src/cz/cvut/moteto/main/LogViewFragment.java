/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import java.util.List;

import cz.cvut.moteto.model.Note;
import cz.cvut.moteto.model.Session;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

/**
 *
 * @author Jan Zdrha
 */
public class LogViewFragment extends ListFragment {
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        ListAdapter adapter = createAdapter(((LogActivity)getActivity()).getSession());
        setListAdapter(adapter);
    }
    
    @Override
    public void onResume() {
    	ListAdapter adapter = createAdapter(((LogActivity)getActivity()).getSession());
        setListAdapter(adapter);
    	super.onResume();
    }

    protected ListAdapter createAdapter(Session session) {
        List<Note> notes = session.getNotes();
        ListAdapter adapter = new ArrayAdapter<Note>(getActivity(), android.R.layout.simple_list_item_1, notes);
        return adapter;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
    }
}