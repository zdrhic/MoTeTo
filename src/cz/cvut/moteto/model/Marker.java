/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jan Zdrha
 */
public class Marker implements Serializable {
    private List<Marker> children;
    private String name;
    private String gesture;
    
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
