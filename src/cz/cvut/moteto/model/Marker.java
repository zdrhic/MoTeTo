/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Zdrha
 */
public class Marker {
    private List<Marker> children;
    private String name;
    private String gesture;
    
    public static final String[] defaultGestures = {
            "74123",
            "74159",
            "12369",
            "32159",
            "7412369",
            "12357",
            "14789",
            "14753",
            "78963"
        };
    
    public Marker(String name, String gesture) {
    	this.name = name;
    	this.setGesture(gesture);
    	this.children = new ArrayList<Marker>();
    }
    
    public Marker() {
    	this("", "");
    }
    
    public void addChild(Marker m) {
    	this.children.add(m);
    }
    
    public List<Marker> getChildren() {
    	return children;
    }
    
    public String getName() {
    	return name;
    }

	public String getGesture() {
		return gesture;
	}

	public void setGesture(String gesture) {
		this.gesture = gesture;
	}
}
