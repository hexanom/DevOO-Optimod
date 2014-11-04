package fr.insalyon.optimod.views.listeners.action;

/**
 * Binds to the to roadmap toolbar
 */
public interface RoadMapToolbarListener {
    /**
     * When the user clicks the add button
     */
    public void onAddDeliveryAction();

    /**
     * When the user clicks the remove button
     */
    public void onRemoveDeliveryAction();

    /**
     * When the user clicks the export button
     */
    public void onExportRoadMapAction();
}
