package fr.insalyon.optimod.models;

import java.util.Comparator;
import java.util.Date;
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
            return (int)(tw2.getStart().getTime() - tw1.getStart().getTime());
        }
    };

    private final Date mStart;
    private final Date mEnd;
    private RoadMap mRoadMap;
    private TreeSet<Path> mPaths = new TreeSet<Path>(Path.COMPARATOR);

    /**
     * Default Constructor
     */
    public TimeWindow() {
        mStart = new Date(0);
        mEnd = new Date(1);
    }

    /**
     * Time Constructor
     * @param start The start time for the time window
     * @param end The end time for the time window
     */
    public TimeWindow(Date start, Date end) {
        mStart = start;
        mEnd = end;
    }

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

    /**
     * Gets the start of the time window
     * @return A start date
     */
    public Date getStart() {
        return mStart;
    }

    /**
     * Gets the end of the time window
     * @return Anend date
     */
    public Date getEnd() {
        return mEnd;
    }
}
