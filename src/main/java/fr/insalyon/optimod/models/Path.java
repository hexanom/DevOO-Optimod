package fr.insalyon.optimod.models;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a Path
 */
public class Path {
    /**
     * The Path comparator for TreeSets
     */
    public static final Comparator<Path> COMPARATOR = new Comparator<Path>() {
        @Override
        public int compare(Path p1, Path p2) {
            return 0; // TODO -> Use total weight
        }
    };

    private TimeWindow mTimeWindow;
    private List<Section> mOrderedSections = new ArrayList<Section>();

    /**
     * Gets the associated time window
     * @return A time window
     */
    public TimeWindow getTimeWindow() {
        return mTimeWindow;
    }

    void setTimeWindow(TimeWindow tw) {
        mTimeWindow = tw;
    }

    /**
     * Gets the list of sections in order
     * @return A list of sections
     */
    public final List<Section> getOrderedSections() {
        return mOrderedSections;
    }

    /**
     * Adds a section at the end of the path
     * @param s The section to add
     * @return Fails and returns false if the section is not linked to the last node
     */
    public boolean appendSection(Section s) {
        if(mOrderedSections.size() > 0) {
            if (s.getOrigin().equals(mOrderedSections
                    .get(mOrderedSections.size() - 1)
                    .getDestination())) {
                mOrderedSections.add(s);
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the first section
     * @return An origin
     */
    public Location getOrigin() {
        if(mOrderedSections.size() > 0) {
            return mOrderedSections.get(0).getOrigin();
        }
        return null;
    }

    /**
     * Gets the last section
     * @return A destination
     */
    public Location getDestination() {
        if(mOrderedSections.size() > 0) {
            return mOrderedSections.get(mOrderedSections.size() - 1).getDestination();
        }
        return null;
    }

    /**
     * Removes the first section
     */
    public void removeOrigin() {
        if(mOrderedSections.size() > 0) {
            mOrderedSections.remove(0);
        }
    }

    /**
     * Removes the last section
     */
    public void removeDestination() {
        if(mOrderedSections.size() > 0) {
            mOrderedSections.remove(mOrderedSections.size() - 1);
        }
    }

    /**
     * Gets the total weight of a path
     * @return A path weight
     */
    long getTotalWeight() {
        return 0; // TODO
    }
}
