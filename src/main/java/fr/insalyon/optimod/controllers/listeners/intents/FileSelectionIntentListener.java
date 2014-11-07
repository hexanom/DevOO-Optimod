package fr.insalyon.optimod.controllers.listeners.intents;

/**
 * For getting file paths
 */
public interface FileSelectionIntentListener {
    /**
     * Should open a dialog and let the user choose a file
     *
     * @return A file path
     */
    public String onFileSelectionIntent();
}
