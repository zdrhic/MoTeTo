/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import cz.cvut.moteto.model.Session;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import java.util.LinkedList;
import java.util.List;

public class TestActivity extends ListActivity {

    private Test test;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent();
        this.test = (Test) intent.getSerializableExtra("test");
        //setContentView(R.layout.tests);
        ListAdapter adapter = createAdapter(test);
        setListAdapter(adapter);
    }

    protected ListAdapter createAdapter(Test test) {
        List<Session> sessions = WorkSpace.getInstance().getSessions(test);
        ListAdapter adapter = new ArrayAdapter<Session>(this, android.R.layout.simple_list_item_1, sessions);
        return adapter;
    }
}
