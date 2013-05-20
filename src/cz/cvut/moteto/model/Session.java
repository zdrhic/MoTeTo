/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

/**
 *
 * @author Jan Zdrha
 */
public class Session implements Serializable {

    private Test test;
    private String path;
    private String name;
    private String participant;
    private Calendar beginning;
    private Calendar end;
    private boolean isRunning;
    private List<Note> notes;

    public Session(Test test, String path) {
        this.test = test;
        this.path = path;
        File f = new File(path);
        this.name = f.getName().replace(".xml", "");
        this.isRunning = false;
        notes = new LinkedList<Note>();
    }

    public Session(Test test, String path, String participant) {
        this(test, path);
        this.participant = participant;
    }

    /**
     * @return the test
     */
    public Test getTest() {
        return test;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return name;
    }

    public Calendar getBeginning() {
        return beginning;
    }

    public Calendar getEnd() {
        return end;
    }

    public void start() {
        this.beginning = Calendar.getInstance();
        this.isRunning = true;
    }

    public void end() {
        this.end = Calendar.getInstance();
        this.isRunning = false;
    }

    public boolean isRunning() {
        return this.isRunning;
    }

    public void addNote(String text) {
    	Note note = new Note(Calendar.getInstance(), text);
    	addNote(note);
    }
    
    public void addNote(Note note) {
    	notes.add(note);
    }

	public List<Note> getNotes() {
		return notes;
	}
    
    public void open() throws Exception {
    	Document doc = WorkSpace.loadXML(path);
    	Element el;
    	
    	el = (Element) doc.getElementsByTagName("participant").item(0);
    	participant = el.getTextContent();
    	
    	el = (Element) doc.getElementsByTagName("beginning").item(0);
    	beginning = Calendar.getInstance();
    	beginning.setTimeInMillis(Long.parseLong(el.getTextContent()));

    	el = (Element) doc.getElementsByTagName("end").item(0);
    	end = Calendar.getInstance();
    	end.setTimeInMillis(Long.parseLong(el.getTextContent()));
    	
    	notes.clear();
    	NodeList nodeList = doc.getElementsByTagName("note");
    	for (int i = 0; i < nodeList.getLength(); i+=1) {
    		el = (Element) nodeList.item(i);
    		Calendar time = Calendar.getInstance();
    		time.setTimeInMillis(Long.parseLong(el.getAttribute("time")));
    		String text = el.getAttribute("text");
    		notes.add(new Note(time, text));
    	}
    }

    public void save() throws Exception {   	
    	XmlSerializer xs = Xml.newSerializer();
    	StringWriter sw = new StringWriter();
    	xs.setOutput(sw);
    	xs.startDocument(null, null);
    	
    	xs.startTag(null, "session");
    	
    	xs.startTag(null, "participant");
    	xs.text(participant.toString());
    	xs.endTag(null, "participant");
    	
    	xs.startTag(null, "beginning");
    	xs.text(""+beginning.getTimeInMillis());
    	xs.endTag(null, "beginning");
    	
    	xs.startTag(null, "end");
    	xs.text(""+end.getTimeInMillis());
    	xs.endTag(null, "end");
    	
    	
    	xs.startTag(null, "notes");
    	
    	for (Note note: notes) {
    		xs.startTag(null, "note");
    		xs.attribute(null, "time", note.getTime());
    		xs.attribute(null, "text", note.getText());
    		xs.endTag(null, "note");
    	}
    	
    	xs.endTag(null, "notes");
    	
    	xs.endTag(null, "session");
    	
    	xs.endDocument();
    	
    	new File(path).getParentFile().mkdirs();

  		FileWriter fstream = new FileWriter(this.path);
  		fstream.write(sw.toString());
   		fstream.close();
    }

}
