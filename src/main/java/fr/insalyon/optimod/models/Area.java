package fr.insalyon.optimod.models;

import java.util.List;
import java.util.ArrayList;

/**
 * Represents an Area in the map
 */
public class Area {
    private String mName;
    private List<TomorrowDeliveries> mTomorrowDeliveries = new ArrayList<TomorrowDeliveries>();

    /**
     * Default constructor
     */
    public Area() {
        mName = "Area0";
    }

    /**
     * Constructor with name
     * @param name An Area name
     */
    public Area(String name) {
        mName = name;
    }

    /**
     * Returns the name
     * @return An Area name
     */
    public String getName() {
        return mName;
    }

    /**
     * Add a new tomorrow delivery associated to this Area
     * @param td Many deliveries
     */
    public void addTommorowDeliveries(TomorrowDeliveries td) {
        td.setArea(this);
        mTomorrowDeliveries.add(td);
    }

    /**
     * Delete a tomorrow delivery associated to this Area
     * @param td Many deliveries
     */
    public void deleteTommorowDeliveries(TomorrowDeliveries td) {
        td.setArea(null);
        mTomorrowDeliveries.remove(td);
    }

    /**
     * Returns a read-only list
     * @return A list of TomorrowDeliveries
     */
    public final List<TomorrowDeliveries> getTomorrowDeliveries() {
        return mTomorrowDeliveries;
    }
}
