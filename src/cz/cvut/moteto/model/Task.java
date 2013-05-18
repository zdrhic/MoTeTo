/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Jan Zdrha
 */
public class Task implements Serializable {

    private String path;
    private int number;
    private List<Marker> markers;

    public Task(int number, String path, List<Marker> markers) {
        this.path = path;
        this.number = number;
        this.markers = markers;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    public String toString() {
        return number+". "+path;
    }
    
    public List<Marker> getMarkers() {
    	return markers;
    }
    
    @Override
    public boolean equals(Object o) {
    	Task task = (Task) o;
    	return task.getPath().equals(this.path);
    	
    }
}
