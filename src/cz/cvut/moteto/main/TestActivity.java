/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import cz.cvut.moteto.main.R;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import cz.cvut.moteto.utils.TabListener;

public class TestActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent();
        Test test = (Test) intent.getSerializableExtra("test");
        WorkSpace.getInstance().setCurrentTest(test);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        Tab tab = actionBar.newTab()
                .setText(R.string.new_session)
                .setTabListener(new TabListener<NewSessionFragment>(
                this, NewSessionFragment.class.getName(), NewSessionFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.sessions)
                .setTabListener(new TabListener<SessionsFragment>(
                this, SessionsFragment.class.getName(), SessionsFragment.class));
        actionBar.addTab(tab);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
