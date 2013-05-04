/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import cz.cvut.moteto.model.Session;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import java.util.List;

/**
 *
 * @author Jan Zdrha
 */
public class SessionsFragment extends ListFragment {

    private Test test;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        this.test = ((TestActivity) getActivity()).getTest();

        ListAdapter adapter = createAdapter(test);
        setListAdapter(adapter);
    }

    protected ListAdapter createAdapter(Test test) {
        List<Session> sessions = WorkSpace.getInstance().getSessions(test);
        ListAdapter adapter = new ArrayAdapter<Session>(getActivity(), android.R.layout.simple_list_item_1, sessions);
        return adapter;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Session session = (Session) getListAdapter().getItem(position);
        Intent myIntent = new Intent(getActivity(), LogActivity.class);
        myIntent.putExtra("test", test);
        myIntent.putExtra("session", session);
        getActivity().startActivity(myIntent);
    }
}
