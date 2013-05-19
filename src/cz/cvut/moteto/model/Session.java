/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

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

    public void save() {
    	StringBuilder sb = new StringBuilder();
    	
    	sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<session>\n");
    	
    	sb.append("<participant>");
    	sb.append(participant);
    	sb.append("</participant>\n");
    	
    	sb.append("<beginning>");
    	sb.append(beginning.getTimeInMillis());
    	sb.append("</beginning>\n");
    	
    	sb.append("<end>");
    	sb.append(end.getTimeInMillis());
    	sb.append("</end>\n");
    	
    	sb.append("<notes>\n");
    	
    	for (Note note: notes) {
    		sb.append(note.toXML());
    	}
    	
    	sb.append("</notes>\n");
    	
    	sb.append("</session>\n");
    	
    	new File(path).getParentFile().mkdirs();
    	try {
    		FileWriter fstream = new FileWriter(this.path);
    		fstream.write(sb.toString());
    		fstream.close();
    	} catch(IOException e) {
    		
    	}
    }

}
