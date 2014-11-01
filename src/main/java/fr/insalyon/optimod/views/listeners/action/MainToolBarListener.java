package fr.insalyon.optimod.views.listeners.action;

/**
 * Binds to the main toolbar
 */
public interface MainToolBarListener {
    /**
     * When the Import map button is clicked
     */
    public void onImportMapAction();

    /**
     * When the Import deliveries button is clicked
     */
    public void onImportDeliveriesAction();

    /**
     * When the user wants to go back
     */
    public void onUndoAction();

    /**
     * When the user wants to go forward again
     */
    public void onRedoAction();
}
