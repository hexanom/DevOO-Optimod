package fr.insalyon.optimod.models;

import java.util.Comparator;

/**
 * Represents a time window for a delivery
 */
public class TimeWindow {
    public static final Comparator<TimeWindow> COMPARATOR = new Comparator<TimeWindow>() {
        @Override
        public int compare(TimeWindow tw1, TimeWindow tw2) {
            return 0;
        }
    };
    private RoadMap mRoadMap;

    /**
     * Gets the associated road map
     * @return A Road Map
     */
    public RoadMap getRoadMap() {
        return mRoadMap;
    }

    public void setRoadMap(RoadMap roadMap) {
        mRoadMap = roadMap;
    }
}
