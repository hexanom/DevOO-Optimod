package fr.insalyon.optimod.models;

import java.util.Comparator;
import java.util.TreeSet;

/**
 * Represents a time window for a delivery
 */
public class TimeWindow {
    /**
     * The TimeWindow comparator for TreeSets
     */
    public static final Comparator<TimeWindow> COMPARATOR = new Comparator<TimeWindow>() {
        @Override
        public int compare(TimeWindow tw1, TimeWindow tw2) {
            return 0; // TODO
        }
    };

    private RoadMap mRoadMap;
    private TreeSet<Path> mPaths = new TreeSet<Path>(Path.COMPARATOR);

    /**
     * Gets the associated road map
     * @return A Road Map
     */
    public RoadMap getRoadMap() {
        return mRoadMap;
    }

    void setRoadMap(RoadMap roadMap) {
        mRoadMap = roadMap;
    }

    /**
     * Add a new path
     * @param path A Path
     */
    public void addPath(Path path) {
        path.setTimeWindow(this);
        mPaths.add(path);
    }

    /**
     * Delete a path
     * @param path A Path
     */
    public void deletePath(Path path) {
        path.setTimeWindow(null);
        mPaths.remove(path);
    }

    /**
     * Returns a read-only list
     * @return A list of paths
     */
    public final TreeSet<Path> getPaths() {
        return mPaths;
    }
}
