package fr.insalyon.optimod.views.listeners.action;

/**
 * Binds to the Toolbar that manages the deliveries
 */
public interface DeliveriesToolbarListener {
    /**
     * When the user clicks the add button
     */
    public void onAddDeliveryAction();

    /**
     * When the user clicks the remove button
     */
    public void onRemoveDeliveryAction();
}
