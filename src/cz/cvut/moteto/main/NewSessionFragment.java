/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import cz.cvut.moteto.model.Session;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jan Zdrha
 */
public class NewSessionFragment extends Fragment {

    private Test test;
    private String userName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        this.test = ((TestActivity) getActivity()).getTest();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.new_session, container, false);

        Spinner spinner = (Spinner) view.findViewById(R.id.names);
        ArrayAdapter adapter = createAdapter();
        spinner.setAdapter(adapter);

        Button newSession = (Button) view.findViewById(R.id.new_session_btn);
        newSession.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(getActivity(), SessionActivity.class);
                myIntent.putExtra("test", test);
                Session session = WorkSpace.getInstance().getNewSession(test, userName);
                myIntent.putExtra("session", session);
                getActivity().startActivity(myIntent);
            }
        });
        return view;
    }

    protected ArrayAdapter createAdapter() {
        List<String> userNames = WorkSpace.getInstance().getUserNames();
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, userNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
