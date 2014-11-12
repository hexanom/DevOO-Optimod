package fr.insalyon.optimod.views.listeners.action;

/**
 * Used to show/hide road names and location names
 */
public interface MapListener {

    /**
     * Used to show/hide road names on the map
     */
    public void toggleSectionNames(boolean enabled);

    /**
     * Used to show/hide location names on the map
     */
    public void toggleLocationNames(boolean enabled);

}
