package fr.insalyon.optimod.models;

import java.util.Comparator;

/**
 * Represents a graph arrow
 */
public class Section {
    /**
     * The Section comparator for TreeSets
     */
    public static Comparator<Section> COMPARATOR = new Comparator<Section>() {
        @Override
        public int compare(Section o1, Section o2) {
            return 0; // TODO
        }
    };

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
     * @param speed The speed in this street
     * @param length The length of the street
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
}
