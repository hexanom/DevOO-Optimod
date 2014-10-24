package fr.insalyon.optimod.models;

import org.w3c.dom.Node;

/**
 * Represents a graph arrow
 */
public class Section {
    private final String mStreetName;
    private final double mSpeed;
    private final double mLength;
    private Location mOrigin;
    private Location mDestination;

    /**
     * Default Constructor
     */
    public Section() {
        mStreetName = "Street0";
        mSpeed = 1.0;
        mLength = 1.0;
    }

    /**
     * Street Constructor
     * @param streetName The name of the street
     * @param speed The speed in this street <strong>in m/s</strong>
     * @param length The length of the street <strong>in meters</strong>
     */
    public Section(String streetName, double speed, double length) {
        mStreetName = streetName;
        mSpeed = speed;
        mLength = length;
    }

    void setOrigin(Location origin) {
        mOrigin = origin;
    }

    /**
     * Gets the origin location
     * @return A Location
     */
    public Location getOrigin() {
        return mOrigin;
    }

    void setDestination(Location destination) {
        mDestination = destination;
    }

    /**
     * Gets the end location
     * @return A Location
     */
    public Location getDestination() {
        return mDestination;
    }

    /**
     * Gets the street's name
     * @return A name
     */
    public String getStreetName() {
        return mStreetName;
    }

    /**
     * Gets the street's speed
     * @return A speed <strong>in m/s</strong>
     */
    public double getSpeed() {
        return mSpeed;
    }

    /**
     * Gets the street's length
     * @return A length <strong>in meters</strong>
     */
    public double getLength() {
        return mLength;
    }

    /**
     * Gets the time needed to get through the street
     * @return A time <strong>in seconds</strong>
     */
    public double getTime() {
        return mLength/mSpeed;
    }

    /**
     * Deserializes a section from a dom node
     * @param node A dom node
     * @return A section
     */
    public static Section deserialize(Node node) throws DeserializationException {
        return null; // TODO
    }
}
