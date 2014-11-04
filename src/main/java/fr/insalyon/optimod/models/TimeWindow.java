package fr.insalyon.optimod.models;

import org.w3c.dom.Node;

import java.util.*;

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
    private List<Delivery> mDeliveries = new ArrayList<Delivery>();

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
     * Add a new delivery
     * @param delivery A delivery
     */
    public void addDelivery(Delivery delivery) {
        delivery.setTimeWindow(this);
        mDeliveries.add(delivery);
    }

    /**
     * Delete a delivery
     * @param delivery A Delivery
     */
    public void deleteDelivery(Delivery delivery) {
        delivery.setTimeWindow(null);
        mDeliveries.remove(delivery);
    }

    /**
     * Returns a read-only list
     * @return A list of deliveries
     */
    public final List<Delivery> getDeliveries() {
        return mDeliveries;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeWindow that = (TimeWindow) o;

        if (mEnd != null ? !mEnd.equals(that.mEnd) : that.mEnd != null) return false;
        if (mStart != null ? !mStart.equals(that.mStart) : that.mStart != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mStart != null ? mStart.hashCode() : 0;
        result = 31 * result + (mEnd != null ? mEnd.hashCode() : 0);
        return result;
    }

    /**
     * Deserializes a time window from a dom node
     * @param node A dom node
     * @return A time window
     */
    public static TimeWindow deserialize(Node node) throws DeserializationException {
        return null; // TODO
    }
}
