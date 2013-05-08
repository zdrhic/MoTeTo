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
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        test = WorkSpace.getInstance().getCurrentTest();
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.new_session, container, false);

        spinner = (Spinner) view.findViewById(R.id.names);
        ArrayAdapter adapter = createAdapter();
        spinner.setAdapter(adapter);

        Button newSession = (Button) view.findViewById(R.id.start_session_btn);
        newSession.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(getActivity(), SessionActivity.class);
                Session session = test.getNewSession(spinner.getSelectedItem().toString());
                session.addNote("Ahoj");
                myIntent.putExtra("session", session);
                myIntent.putExtra("test", test);
                getActivity().startActivity(myIntent);
            }
        });
        return view;
    }

    protected ArrayAdapter createAdapter() {
        List<String> participants = WorkSpace.getInstance().getCurrentTest().getParticipants();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, participants);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        return adapter;
    }
}
