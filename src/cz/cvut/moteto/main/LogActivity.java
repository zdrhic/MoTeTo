/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import cz.cvut.moteto.main.R;
import cz.cvut.moteto.main.NewSessionFragment;
import cz.cvut.moteto.main.SessionsFragment;
import cz.cvut.moteto.model.Session;
import cz.cvut.moteto.model.Test;
import cz.cvut.moteto.model.WorkSpace;
import cz.cvut.moteto.utils.TabListener;

/**
 *
 * @author Jan Zdrha
 */
public class LogActivity extends Activity {

    private Session session;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        Intent intent = getIntent();
        this.session = (Session) intent.getSerializableExtra("session");
        try {
			this.session.open();
		} catch (Exception e) {
			WorkSpace.getInstance().showErrorDialog(this, getString(R.string.error_loading_session), e.getLocalizedMessage());
			finish();
			return;
		}
        //setContentView(R.layout.tests);
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        ActionBar.Tab tab = actionBar.newTab()
                .setText(R.string.log)
                .setTabListener(new TabListener<LogViewFragment>(
                this, LogViewFragment.class.getName(), LogViewFragment.class));
        actionBar.addTab(tab);

        tab = actionBar.newTab()
                .setText(R.string.map)
                .setTabListener(new TabListener<MapViewFragment>(
                this, MapViewFragment.class.getName(), MapViewFragment.class));
        actionBar.addTab(tab);
    }

    public Session getSession() {
        return this.session;
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
