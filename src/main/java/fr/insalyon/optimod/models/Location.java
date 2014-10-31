package fr.insalyon.optimod.models;

import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a graph node
 */
public class Location {
    private final long mX;
    private final long mY;
    private final String mAddress;
    private List<Section> mIns = new ArrayList<Section>();
    private List<Section> mOuts = new ArrayList<Section>();
    private Map mMap;

    /**
     * Default Constructor
     */
    public Location() {
        mAddress = "Address0";
        mX = 0;
        mY = 0;
    }

    /**
     * Address constructor
     * @param address An unique address (aka. the id)
     * @param x The x coord <strong>in meters</strong>
     * @param y The y coord <strong>in meters</strong>
     */
    public Location(String address, long x, long y) {
        mAddress = address;
        mX = x;
        mY = y;
    }

    /**
     * Gives an address for the location (aka. the id)
     * @return An address
     */
    public String getAddress() {
        return mAddress;
    }

    /**
     * Gets the x position
     * @return A length <strong>in meters</strong>
     */
    public long getX() {
        return mX;
    }

    /**
     * Gets the y position
     * @return A length <strong>in meters</strong>
     */
    public long getY() {
        return mY;
    }

    /**
     * Connects the node to another one with a generic street
     * @param location An other node
     * @return The newly created section or null if the nodes doesn't belong to the same graph
     */
    public Section connectTo(Location location) {
        return connectTo(location, null, 0, 0);
    }

    /**
     * Connects the node to another one
     * @param location An other node
     * @param streetName The name of the street between the two nodes
     * @param speed The speed in the street
     * @param length The length of the street
     * @return The newly created section or null if the nodes doesn't belong to the same graph
     */
    public Section connectTo(Location location, String streetName, double speed, double length) {
        if(location.getMap() != null && location.getMap().equals(mMap)) {
            Section section;
            if(streetName != null) {
                section = new Section(streetName, speed, length);
            } else {
                section = new Section();
            }
            section.setOrigin(this);
            mOuts.add(section);
            location.connectedFrom(section);
            return section;
        }
        return null;
    }

    void connectedFrom(Section section) {
        section.setDestination(this);
        mIns.add(section);
    }

    /**
     * Delete any of your IN/OUT arrows
     * @param section An arrow
     */
    public void deleteConnectedSection(Section section) {
        if(section.getOrigin() != null && section.getOrigin().equals(this)) {
            section.setOrigin(null);
            mOuts.remove(section);
            if(section.getDestination() != null) {
                section.getDestination().deleteConnectedSection(section);
            }
        } else if(section.getDestination() != null && section.getDestination().equals(this)) {
            section.setDestination(null);
            mIns.remove(section);
            if(section.getOrigin() != null) {
                section.getOrigin().deleteConnectedSection(section);
            }
        }
    }

    /**
     * Get the incoming arrows
     * @return A list of arrows
     */
    public List<Section> getIns() {
        return mIns;
    }

    /**
     * Get the the outgoing arrows
     * @return A list of arrows
     */
    public List<Section> getOuts() {
        return mOuts;
    }

    /**
     * Gets all the connected arrows
     * @return A list of arrows
     */
    public List<Section> getConnectedSections() {
        List<Section> all = new ArrayList<Section>();
        all.addAll(mIns);
        all.addAll(mOuts);
        return all;
    }

    /**
     * Gets the associated graph
     * @return A Map
     */
    public Map getMap() {
        return mMap;
    }

    public void setMap(Map map) {
        mMap = map;
    }

    /**
     * Deserializes a location from a dom node
     * @param node A dom node
     * @return A location
     */
    public static Location deserialize(Node node) throws DeserializationException {
        return null; // TODO
    }
}
