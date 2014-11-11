package fr.insalyon.optimod.models;

import fr.insalyon.optimod.tsp.LocationsGraph;
import fr.insalyon.optimod.tsp.TSP;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * Represents a Courier's Road map
 */
public class RoadMap {
    private TomorrowDeliveries tomorrowDeliveries;
    private Courier mCourier;
    private Location mWarehouse;
    private List<Path> mPaths = new ArrayList<>();
    private TreeSet<TimeWindow> mTimeWindows = new TreeSet<TimeWindow>(TimeWindow.COMPARATOR);

    /**
     * Default constructor
     */
    public RoadMap() {
    }

    /**
     * Warehouse constructor
     * @param warehouse The starting warehouse
     */
    public RoadMap(Location warehouse) {
        mWarehouse = warehouse;
    }

    /**
     * Gets the tomorrow deliveries
     * @return A TomorrowDeliveries
     */
    public TomorrowDeliveries getTomorrowDeliveries() {
        return tomorrowDeliveries;
    }

    public void setTomorrowDeliveries(TomorrowDeliveries deliveries) {
        tomorrowDeliveries = deliveries;
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
    public final List<Path> getPaths() {
        return mPaths;
    }

    /**
     * Generate the possible paths
     */
    public void generatePaths() {
        // TODO - Generate all possible paths (dijkstra) between start and end
    }

    /**
     * Gets the starting warehouse
     * @return A warehouse
     */
    public Location getWarehouse() {
        return mWarehouse;
    }

    public static RoadMap fromTomorrowDeliveries(TomorrowDeliveries tomorrowDeliveries) {
        List<Delivery> deliveries = tomorrowDeliveries.getDeliveries();
        Location warehouse = tomorrowDeliveries.getWarehouse();
        LocationsGraph locationsGraph = new LocationsGraph(warehouse, deliveries);
        TSP tsp = new TSP(locationsGraph);
        tsp.solve(20000, locationsGraph.getNbVertices()*locationsGraph.getMaxArcCost()+1);

        // TODO : traiter cas où ca bug !
        // TODO : traiter le cas où la tournée est infaisable en restant dans les délais !

        int[] succesors = tsp.getNext();

        RoadMap roadMap = new RoadMap(warehouse);
        roadMap.setTomorrowDeliveries(tomorrowDeliveries);
        for(Delivery d : deliveries) {
            roadMap.addTimeWindow(d.getTimeWindow());
        }

        Location origin;
        Location dest = warehouse;

        int next = succesors[0];
        while(next != 0) {
            origin = dest;
            dest = deliveries.get(next - 1).getLocation();

            roadMap.addPath(Path.fromTwoLocations(origin, dest));

            next = succesors[next];
        }

        return roadMap;
    }
}
