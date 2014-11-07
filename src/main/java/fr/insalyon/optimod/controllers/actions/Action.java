package fr.insalyon.optimod.controllers.actions;

/**
 * Defines a rewindable action
 */
public interface Action {
    /**
     * Does the action
     */
    public void doAction();

    /**
     * Undoes the action
     */
    public void undoAction();
}
