/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import java.util.LinkedList;
import java.util.List;

public class TestsActivity extends ListActivity {

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        //setContentView(R.layout.tests);
        ListAdapter adapter = createAdapter();
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Test test = (Test) getListAdapter().getItem(position);
        Intent myIntent = new Intent(TestsActivity.this, TestActivity.class);
        myIntent.putExtra("test", test);
        TestsActivity.this.startActivity(myIntent);
    }

    protected ListAdapter createAdapter() {
        List<Test> tests = WorkSpace.getInstance().getTests();
        ListAdapter adapter = new ArrayAdapter<Test>(this, android.R.layout.simple_list_item_1, tests);
        return adapter;
    }
}
