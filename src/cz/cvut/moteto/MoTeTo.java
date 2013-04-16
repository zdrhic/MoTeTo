/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto;

import android.app.Application;
import android.content.Context;

/**
 * 
 * @author Jan Zdrha
 */
public class MoTeTo extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        MoTeTo.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MoTeTo.context;
    }
}
