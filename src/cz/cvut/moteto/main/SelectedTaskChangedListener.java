/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.moteto.main;

import cz.cvut.moteto.model.Task;

/**
 *
 * @author Jan Zdrha
 */
public interface SelectedTaskChangedListener {

    public void selectedTaskChanged(Task selestedTask);
}
