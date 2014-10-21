package fr.insalyon.optimod.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the graph
 */
public class Map {
    private List<Location> mLocations = new ArrayList<Location>();

    /**
     * Add a new location
     * @param location A location
     */
    public void addLocation(Location location) {
        location.setMap(this);
        mLocations.add(location);
    }

    /**
     * Delete a location
     * @param location A location
     */
    public void deleteLocation(Location location) {
        location.setMap(null);
        mLocations.remove(location);
    }

    /**
     * Returns a read-only list
     * @return A list of Locations
     */
    public final List<Location> getLocations() {
        return mLocations;
    }
}
