package fr.insalyon.optimod.views.listeners.action;

/**
 * Listens for tab selection changes
 */
public interface TabSelectionListener {
    /**
     * When the user opens the roadmap tab
     */
    public void onRoadMapTabSelected();

    /**
     * When the user opens the deliveries tab
     */
    public void onDeliveriesTabSelected();
}
