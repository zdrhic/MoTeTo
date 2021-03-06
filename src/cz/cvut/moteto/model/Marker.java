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
    	this.gesture = gesture;
    	children = new ArrayList<Marker>();
    }
    
    public Marker() {
    	this("", "");
    }
    
    public void addChild(Marker m) {
    	this.getChildren().add(m);
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
	
	public boolean hasChildren() {
		return getChildren().size() > 0;
	}
	
	@Override
	public String toString() {
		String str = name;
		if (hasChildren()) {
			str = "[" + str + "]";
		}
		return str;
	}
}
