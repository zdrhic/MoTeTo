/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import cz.cvut.moteto.MoTeTo;
import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jan Zdrha
 */
public class WorkSpace {

    private static WorkSpace instance = new WorkSpace();

    public static WorkSpace getInstance() {
        return WorkSpace.instance;
    }

    private WorkSpace() {
    }

    private String getWorkspaceFolder() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MoTeTo.getAppContext());
        return sharedPrefs.getString("workspace", "/");
    }

    public List<Test> getTests() {
        List<Test> tests = new LinkedList<Test>();

        tests.add(new Test("test 1"));
        tests.add(new Test("test 2"));
        tests.add(new Test("test 3"));


//        List<Test> tests = new LinkedList<Test>();
//        File f = new File(this.getWorkspaceFolder());
//
//        File[] files = f.listFiles();
//        for (File file : files) {
//            Test test = new Test(file.getPath());
//            tests.add(test);
//        }
        return tests;
    }

    public List<Session> getSessions(Test test) {
        List<Session> sessions = new LinkedList<Session>();

        sessions.add(new Session(test, test.toString() + "session 1", ""));
        sessions.add(new Session(test, test.toString() + "session 2", ""));
        sessions.add(new Session(test, test.toString() + "session 3", ""));

        return sessions;
    }
}
