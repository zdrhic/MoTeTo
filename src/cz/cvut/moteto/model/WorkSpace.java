/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import android.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import cz.cvut.moteto.MoTeTo;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 *
 * @author Jan Zdrha
 */
public class WorkSpace {

    private static WorkSpace instance = new WorkSpace();
    private Test currentTest;

    public static WorkSpace getInstance() {
        return WorkSpace.instance;
    }

    private WorkSpace() {
    }

    public String getWorkspaceFolder() {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MoTeTo.getAppContext());
        return sharedPrefs.getString("workspace", "/");
    }

    public void setWorkspaceFolder(String folder) {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(MoTeTo.getAppContext());
        sharedPrefs.edit().putString("workspace", folder);
    }

	public Test getCurrentTest() {
		return currentTest;
	}

	public void setCurrentTest(Test currentTest) {
		this.currentTest = currentTest;
	}

	public static Document loadXML(String path) throws Exception {
	    Document doc = null;
        URL url;
        url = new URL("file://"+path);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        db = dbf.newDocumentBuilder();

        doc = db.parse(new InputSource(url.openStream()));
        doc.getDocumentElement().normalize();
        return doc;
	}

	public void showErrorDialog(Context ctx, String title, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

        builder.setTitle(title);
        builder.setMessage(msg);

        builder.setPositiveButton(ctx.getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
	}
}
