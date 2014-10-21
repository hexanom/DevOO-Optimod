package fr.insalyon.optimod.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a courier
 */
public class Courier {
    private List<RoadMap> mRoadMaps = new ArrayList<RoadMap>();
    private Truck mTruck = new Truck();

    /**
     * Add a new road map
     * @param rm A road map
     */
    public void addRoadMap(RoadMap rm) {
        rm.setCourier(this);
        mRoadMaps.add(rm);
    }

    /**
     * Delete a road map
     * @param rm Many deliveries
     */
    public void deleteRoadMap(RoadMap rm) {
        rm.setCourier(null);
        mRoadMaps.remove(rm);
    }

    /**
     * Returns a read-only list
     * @return A list of RoadMap
     */
    public final List<RoadMap> getRoadMaps() {
        return mRoadMaps;
    }

    /**
     * Gets the truck
     * @return A truck
     */
    public Truck getTruck() {
        return mTruck;
    }
}
