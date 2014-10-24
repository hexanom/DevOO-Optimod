package fr.insalyon.optimod.models;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the deliveries for a day
 */
public class TomorrowDeliveries {
    private Area mArea;
    private List<Delivery> mDeliveries = new ArrayList<Delivery>();
    private List<RoadMap> mRoadMaps = new ArrayList<RoadMap>();

    /**
     * Gets the Area
     * @return An Area
     */
    public Area getArea() {
        return mArea;
    }

    // NOTE: nothing = package visibility
    void setArea(Area a) {
        mArea = a;
    }

    /**
     * Add a new delivery
     * @param delivery A delivery
     */
    public void addDelivery(Delivery delivery) {
        delivery.setTomorrowDeliveries(this);
        mDeliveries.add(delivery);
    }

    /**
     * Delete a delivery
     * @param delivery A delivery
     */
    public void deleteDelivery(Delivery delivery) {
        delivery.setTomorrowDeliveries(null);
        mDeliveries.remove(delivery);
    }

    /**
     * Returns a read-only list
     * @return A list of Deliveries
     */
    public final List<Delivery> getDeliveries() {
        return mDeliveries;
    }

    /**
     * Add a new road map
     * @param rm A road map
     */
    public void addRoadMap(RoadMap rm) {
        rm.setTomorrowDeliveries(this);
        mRoadMaps.add(rm);
    }

    /**
     * Delete a road map
     * @param rm A road map
     */
    public void deleteRoadMap(RoadMap rm) {
        rm.setTomorrowDeliveries(null);
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
     * Deserializes a tomorrow deliveries from a dom node
     * @param node A dom node
     * @return A tomorrow deliveries
     */
    public static TomorrowDeliveries deserialize(Node node) throws DeserializationException {
        // calls sub deserializations depending on the subnodes names
        return null; // TODO
    }
}
