package fr.insalyon.optimod.models;

import java.util.TreeSet;

/**
 * Represents a Courier's Road map
 */
public class RoadMap {
    private TomorrowDeliveries mTomorrowDeliveries;
    private Courier mCourier;
    private TreeSet<Path> mPaths = new TreeSet<Path>(Path.COMPARATOR);
    private TreeSet<TimeWindow> mTimeWindows = new TreeSet<TimeWindow>(TimeWindow.COMPARATOR);

    /**
     * Gets the tomorrow deliveries
     * @return A TomorrowDeliveries
     */
    public TomorrowDeliveries getTomorrowDeliveries() {
        return mTomorrowDeliveries;
    }

    void setTomorrowDeliveries(TomorrowDeliveries deliveries) {
        mTomorrowDeliveries = deliveries;
    }

    /**
     * Gets the courier
     * @return A Courier
     */
    public Courier getCourier() {
        return mCourier;
    }

    void setCourier(Courier courier) {
        mCourier = courier;
    }

    /**
     * Add a new time window
     * @param tw A time window
     */
    public void addTimeWindow(TimeWindow tw) {
        tw.setRoadMap(this);
        mTimeWindows.add(tw);
    }

    /**
     * Delete a time window
     * @param tw A Time Window
     */
    public void deleteTimeWindow(TimeWindow tw) {
        tw.setRoadMap(null);
        mTimeWindows.remove(tw);
    }

    /**
     * Returns a read-only list
     * @return A list of TimeWondow
     */
    public final TreeSet<TimeWindow> getTimeWindows() {
        return mTimeWindows;
    }

    /**
     * Add a new path
     * @param path A Path
     */
    public void addPath(Path path) {
        path.setRoadMap(this);
        mPaths.add(path);
    }

    /**
     * Delete a path
     * @param path A Path
     */
    public void deletePath(Path path) {
        path.setRoadMap(null);
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
