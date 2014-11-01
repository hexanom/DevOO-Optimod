package fr.insalyon.optimod.views.listeners.action;

/**
 * Binds to user clicks on the map
 */
public interface MapClickListener {
    /**
     * Called when the user clicks on a map
     *
     * @param x The x click coord
     * @param y The y click coord
     */
    public void onMapClick(int x, int y);
}
