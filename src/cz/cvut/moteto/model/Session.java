/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Jan Zdrha
 */
public class Session implements Serializable {

    private Test test;
    private String path;
    private String participant;
    private HashMap<Task, LinkedList<Marker>> markers;
    private Calendar beginning;
    private Calendar end;
    private boolean isRunning;

    public Session(Test test, String path, String participant) {
        this.test = test;
        this.path = path;
        this.markers = new HashMap<Task, LinkedList<Marker>>();
        this.isRunning = false;
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
        return this.path;
    }

    public void addMarker(Task task, Marker marker) {
        if (!this.markers.containsKey(task)) {
            this.markers.put(task, new LinkedList<Marker>());
        }
        this.markers.get(task).add(marker);
    }

    public List<Marker> getMarkers(Task task) {
        if (!this.markers.containsKey(task)) {
            return new LinkedList<Marker>();
        }
        return this.markers.get(task);
    }

    public boolean hasMarker(Task task) {
        return !this.getMarkers(task).isEmpty();
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
}