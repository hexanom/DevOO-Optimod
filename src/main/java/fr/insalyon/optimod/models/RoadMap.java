package fr.insalyon.optimod.models;

import fr.insalyon.optimod.tsp.LocationsGraph;
import fr.insalyon.optimod.tsp.NoPathFoundException;
import fr.insalyon.optimod.tsp.SolutionState;
import fr.insalyon.optimod.tsp.TSP;

import java.util.*;

/**
 * Represents a Courier's Road map
 */
public class RoadMap {
    private TomorrowDeliveries tomorrowDeliveries;
    private Courier mCourier;
    private Location mWarehouse;
    private LinkedList<Path> mPaths = new LinkedList<>();
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
    public final LinkedList<Path> getPaths() {
        return mPaths;
    }

    /**
     * Generate the possible paths
     */
    public void generatePaths() {
        // TODO - Generate all possible paths (dijkstra) between start and end
    }

    /**
     * Verify if the roadmap is respecting the timewindows
     * @return True if timewindows are respected
     */
    public boolean isRespectingTimeWindows() {
        Date startTime = mTimeWindows.first().getStart();
        Calendar timeOfDay = Calendar.getInstance();
        timeOfDay.setTime(startTime);

        for(Path path : mPaths) {
            Delivery delivery = path.getDestination().getDelivery();

            if(delivery == null) { // it means we are back to the warehouse
                break;
            }

            int pathTime = (int) path.getTotalTime();
            timeOfDay.add(Calendar.SECOND, pathTime);

            Calendar timeWindowEnd = Calendar.getInstance();
            timeWindowEnd.setTime(delivery.getTimeWindow().getEnd());
            if(timeOfDay.after(timeWindowEnd)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Gets the starting warehouse
     * @return A warehouse
     */
    public Location getWarehouse() {
        return mWarehouse;
    }

    /**
     * Create a roadmap from tomorrowDeliveries. Will calculate all the required pathingfinding.
     * @param tomorrowDeliveries
     * @return
     * @throws NoPathFoundException
     */
    public static RoadMap fromTomorrowDeliveries(TomorrowDeliveries tomorrowDeliveries) throws NoPathFoundException {
        List<Delivery> deliveries = tomorrowDeliveries.getDeliveries();
        Location warehouse = tomorrowDeliveries.getWarehouse();
        LocationsGraph locationsGraph = new LocationsGraph(warehouse, deliveries);
        TSP tsp = new TSP(locationsGraph);
        tsp.solve(20000, locationsGraph.getNbVertices()*locationsGraph.getMaxArcCost()+1);
        SolutionState solutionState = tsp.getSolutionState();

        if(solutionState == SolutionState.NO_SOLUTION_FOUND || solutionState == SolutionState.INCONSISTENT) {
            throw new NoPathFoundException("Solution State : " + solutionState.name());
        }

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
        roadMap.addPath(Path.fromTwoLocations(dest, warehouse));

        if(!roadMap.isRespectingTimeWindows()) {
            throw new NoPathFoundException("Roadmap not respecting time windows");
        }

        return roadMap;
    }
}
