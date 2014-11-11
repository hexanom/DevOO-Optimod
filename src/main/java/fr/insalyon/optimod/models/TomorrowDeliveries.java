package fr.insalyon.optimod.models;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.*;

/**
 * Represents the deliveries for a day
 */
public class TomorrowDeliveries {
    private Area mArea;
    private Location mWarehouse;
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
     * Gets the warehouse
     * @return A Warehouse
     */
    public Location getWarehouse() {
        return mWarehouse;
    }

    public void setWarehouse(Location warehouse) {
        this.mWarehouse = warehouse;
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
     * Get the deliveries ordered by time windows
     * @return
     */
    public TreeSet<TimeWindow> getTimeWindows() {
        TreeSet<TimeWindow> timeWindows = new TreeSet<>(TimeWindow.COMPARATOR);

        for (Delivery del : mDeliveries) {
            timeWindows.add(del.getTimeWindow());
        }
        return timeWindows;
    }

    /**
     * Deserializes a tomorrow deliveries from a dom node
     * @param node A dom node
     * @param map A map
     * @return A tomorrow deliveries
     */
    public static TomorrowDeliveries deserialize(Element node, Map map)
            throws DeserializationException {

        TomorrowDeliveries tomorrowDeliveries = new TomorrowDeliveries();
        // calls sub deserializations depending on the subnodes names
        String tag = "";
        // Warehouse
        tag = "Entrepot";
        NodeList listChildNodes = node.getElementsByTagName(tag);
        if (listChildNodes.getLength() != 1) {
            System.out.println("Xml file must define One warehouse");
            return null;
        }

        Element warehouseElement = (Element) listChildNodes.item(0);

        //Warehouse warehouse = Warehouse.deserialize(warehouseElement);
        String warehouseAddress = warehouseElement.getAttribute("adresse");
        Location warehouseLocation = map.getLocationByAddress(warehouseAddress);
        if(warehouseLocation == null) {
            System.out.println("Warehouse location doesn't exist");
            return null;
        }
        // set warehouse
        tomorrowDeliveries.setWarehouse(warehouseLocation);

        // TimeWindows and deliveries
        tag = "PlagesHoraires";
        listChildNodes = node.getElementsByTagName(tag);
        if (listChildNodes.getLength() != 1) {
            System.out.println("Error in time Windows definition");
            return null;
        }

        Element timeWindowElement = (Element) listChildNodes.item(0);
        NodeList listTimeWindows = timeWindowElement.getElementsByTagName("Plage");
        if (listTimeWindows.getLength() < 1) {
            System.out.println("Error in time Windows definition");
            return null;
        }

        for (int j = 0; j < listTimeWindows.getLength(); j++) {
            Element windowElement = (Element) listTimeWindows.item(j);
            TimeWindow timeWindow = TimeWindow.deserialize(windowElement, map); // add map to get locations

            //TODO timeWindow ??
            if(timeWindow != null) {
                for(Delivery d : timeWindow.getDeliveries()) {
                    tomorrowDeliveries.addDelivery(d);
                }
            }
        }

        // If everything went okay, link locations to deliveries
        for(Location loc : map.getLocations()) {
            loc.setDelivery(null);
        }
        for(Delivery delivery : tomorrowDeliveries.getDeliveries()) {
            delivery.getLocation().setDelivery(delivery);
        }

        return tomorrowDeliveries;
    }
}
