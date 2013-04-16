/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.model;

/**
 *
 * @author Jan Zdrha
 */
public class Task {

    private String path;

    public Task(String path) {
        this.path = path;
    }

    /**
     * @return the path
     */
    public String getPath() {
        return path;
    }
}
