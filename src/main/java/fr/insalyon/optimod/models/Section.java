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

    private Location mOrigin;
    private Location mDestination;

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
