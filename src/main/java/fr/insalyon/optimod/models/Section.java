package fr.insalyon.optimod.models;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.w3c.dom.Element;

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
     * @throws ParseException
     */
    public static Section deserialize(Element node) throws DeserializationException {

        NumberFormat format = NumberFormat.getInstance(Locale.FRANCE);
        //attributes
        String streetName = node.getAttribute("nomRue");
        double speed, length;
        try {
            speed = format.parse(node.getAttribute("vitesse")).doubleValue();
            length = format.parse(node.getAttribute("longueur")).doubleValue();
        } catch (ParseException e) {

            e.printStackTrace();
            return null;
        }

        Section section  = new Section(streetName, speed, length);
        return section;
    }


}
