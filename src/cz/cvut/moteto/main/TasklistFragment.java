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
import cz.cvut.moteto.model.Task;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import java.util.List;

/**
 *
 * @author Jan Zdrha
 */
public class TasklistFragment extends ListFragment {
    
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        
        ListAdapter adapter = createAdapter(WorkSpace.getInstance().getCurrentTest());
        setListAdapter(adapter);
    }
    
    protected ListAdapter createAdapter(Test test) {
        List<Task> tasks = test.getTasks();
        ListAdapter adapter = new ArrayAdapter<Task>(getActivity(), android.R.layout.simple_list_item_1, tasks);
        return adapter;
    }
    
    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Task task = (Task) getListAdapter().getItem(position);
        SessionActivity activity = ((SessionActivity) getActivity());
        activity.setSelectedTask(task);
        activity.setFragment(SessionActivity.TAB_LOG);
        
    }
}
