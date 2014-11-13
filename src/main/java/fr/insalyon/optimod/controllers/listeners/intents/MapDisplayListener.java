package fr.insalyon.optimod.controllers.listeners.intents;

/**
 * Used to show/hide road names and location names
 */
public interface MapDisplayListener {

    /**
     * Called when the user clicked on the "Show road names" menu item
     */
    public void onToggleSectionNames(boolean enabled);

    /**
     * Called when the user clicked on the "Show location names" menu item
     */
    public void onToggleLocationNames(boolean enabled);

    /**
     * Called when the user clicked on the "Animate roadmap" menu item
     */
    public void onAnimateRoadmap();
}
