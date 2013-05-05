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
        //return sharedPrefs.getString("workspace", "/");
        return "/cache/test-data/";
    }

    public List<Test> getTests() {
        List<Test> tests = new LinkedList<Test>();

//         tests.add(new Test("test"));
//         tests.add(new Test("test 2"));
//         tests.add(new Test("test 3"));

       File f = new File(this.getWorkspaceFolder());

       File[] files = f.listFiles();
       for (File file : files) {
    	   String path = file.getName();
    	   if (path.endsWith(".xml")) {
    		   Test test = new Test(path.replace(".xml", ""));
    		   tests.add(test);
    	   }
       }
        return tests;
    }

	public Test getCurrentTest() {
		return currentTest;
	}

	public void setCurrentTest(Test currentTest) {
		this.currentTest = currentTest;
	}

	public static Document loadXML(String path) {
	    Document doc = null;
        try {
            URL url;
            url = new URL("file://"+path);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db;
            db = dbf.newDocumentBuilder();

            doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return doc;
	}
}
