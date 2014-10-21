package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.Map;

/**
 * Trait that characterize objects able to generate maps
 */
public interface MapFactory {
    /**
     * Creates the map
     * @return The map or null if it failed for some reason
     */
    public Map create();
}
