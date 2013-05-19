package cz.cvut.moteto.model;

import java.io.Serializable;
import java.util.Calendar;

public class Note implements Serializable {
    public static final String DELIMITER = " - ";
	private Calendar time;
    private String text;
    private int x;
    private int y;

    public Note(Calendar time, String text) {
    	this.time = time;
    	this.text = text;
    }

    @Override
    public String toString() {
    	return String.format("%02d:%02d:%02d - %s", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.SECOND), text);
    }
    
    public String toXML() {
		return "<note time=\""+time.getTimeInMillis()+"\" text=\""+text+"\" />\n";
    }
}