package fr.insalyon.optimod.controllers.listeners.data;

import fr.insalyon.optimod.models.Map;

/**
 * Binds to a map update
 */
public interface MapChangeListener {
    /**
     * When the map changes
     *
     * @param map A map
     */
    public void onMapChanged(Map map);
}
