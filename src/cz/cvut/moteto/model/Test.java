/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Jan Zdrha
 */
public class Test implements Serializable {

    private String path;

    public Test(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return this.getPath();
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }

    public ListIterator<Task> getTasks() {
        List<Task> tasks = new LinkedList<Task>();
        
        tasks.add(new Task("Task 1"));
        tasks.add(new Task("Task 2"));
        tasks.add(new Task("Task 3"));
        
        return tasks.listIterator();
    }
}
