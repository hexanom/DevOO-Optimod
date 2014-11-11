package fr.insalyon.optimod.models;

import fr.insalyon.optimod.tsp.LocationsGraph;
import fr.insalyon.optimod.tsp.NoPathFoundException;
import fr.insalyon.optimod.tsp.SolutionState;
import fr.insalyon.optimod.tsp.TSP;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Represents a Courier's Road map
 */
public class RoadMap {
    private TomorrowDeliveries tomorrowDeliveries;
    private Courier mCourier;
    private Location mWarehouse;
    private LinkedList<Path> mPaths = new LinkedList<>();
    private TreeSet<TimeWindow> mTimeWindows = new TreeSet<TimeWindow>(TimeWindow.COMPARATOR);

    private static final int TRUCK_UNLOAD_TIME = 10*60; // in seconds
    private static final String NEWLINE =  System.getProperty("line.separator");

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
     * Is the present instance of roadmap respecting the time constraints ?
     * @return
     */
    public boolean isRespectingTimeWindows() {
        LinkedHashMap<Location, Date> detailedRoadmap = detailedRoadmap();

        for(Map.Entry<Location, Date> e : detailedRoadmap.entrySet()) {
            Location loc = e.getKey();
            Date time = e.getValue();

            Delivery delivery = loc.getDelivery();
            if(delivery != null) {
                if(time.after(delivery.getTimeWindow().getEnd())) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Export a roadmap in a String format for a text file
     * @return
     */
    public String exportRoadmap() {
        LinkedHashMap<Location, Date> detailedRoadmap = detailedRoadmap();
        StringBuilder sb = new StringBuilder();
        DateFormat df = new SimpleDateFormat("HH:mm");

        sb.append("[").append(df.format(detailedRoadmap.get(mWarehouse))).append("] Departure from the warehouse");
        sb.append(NEWLINE);
        detailedRoadmap.remove(mWarehouse);

        for (Path path : mPaths) {
            Location origin = path.getOrigin();
            Location dest = path.getDestination();

            for (Section section : path.getOrderedSections()) {
                sb.append('\t').append("Take the ").append(section.getStreetName()).append(" street (");
                sb.append(section.getOrigin().getAddress()).append(" -> ");
                sb.append(section.getDestination().getAddress()).append(").");
                sb.append(" Estimated navigation time: ").append(prettyTime((long)section.getTime())).append(".");
                sb.append(NEWLINE);
            }


            if(dest != mWarehouse) {
                Date arrivalTime = detailedRoadmap.get(dest);
                sb.append("[").append(df.format(arrivalTime)).append("] Arrival at ").append(dest.getAddress());
                sb.append(NEWLINE);

                Calendar departure = Calendar.getInstance();
                departure.setTime(arrivalTime);
                departure.add(Calendar.SECOND, TRUCK_UNLOAD_TIME);
                sb.append("[").append(df.format(departure.getTime())).append("] Departure from ").append(dest.getAddress());
                sb.append(NEWLINE);
            }
            else {
                Calendar warehouseArrival = Calendar.getInstance();
                warehouseArrival.setTime(detailedRoadmap.get(origin));
                warehouseArrival.add(Calendar.SECOND, TRUCK_UNLOAD_TIME);
                warehouseArrival.add(Calendar.SECOND, (int) path.getTotalTime());
                sb.append("[").append(df.format(warehouseArrival.getTime())).append("] Arrival at the warehouse");
                sb.append(NEWLINE);
            }
        }

        return sb.toString();
    }

    /**
     * Prettifies time display
     * @param time in seconds
     * @return
     */
    private String prettyTime(long time) {
        return String.format("%02d′%02d″",
                TimeUnit.SECONDS.toMinutes(time),
                TimeUnit.SECONDS.toSeconds(time) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(time))
        );
    }

    /**
     * Create a detail roadmap : where are we at each position in time
     * @return
     */
    public LinkedHashMap<Location, Date> detailedRoadmap() {

        LinkedHashMap<Location, Date> detailedRoadmap = new LinkedHashMap<>();

        Date startTime = mTimeWindows.first().getStart();
        Calendar timeOfDay = Calendar.getInstance();
        timeOfDay.setTime(startTime);
        timeOfDay.add(Calendar.SECOND, (int) -mPaths.peekFirst().getTotalTime()); // the truck must arrive at o'clock

        detailedRoadmap.put(mWarehouse, timeOfDay.getTime());

        for(Path path : mPaths) {
            Location origin = path.getOrigin();
            Location destination = path.getDestination();
            Delivery origDelivery = origin.getDelivery();

            if(origDelivery != null) {
                Date origTwStart = origDelivery.getTimeWindow().getStart();
                if(timeOfDay.getTime().before(origTwStart)) { // we are early
                    timeOfDay.setTime(origTwStart); // wait in the truck
                }
            }

            if(destination != mWarehouse) {
                int pathTime = (int) path.getTotalTime();
                timeOfDay.add(Calendar.SECOND, pathTime);

                detailedRoadmap.put(destination, timeOfDay.getTime());
            }

            timeOfDay.add(Calendar.SECOND, TRUCK_UNLOAD_TIME);

        }

        return detailedRoadmap;
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
        LocationsGraph locationsGraph = new LocationsGraph(tomorrowDeliveries);
        TSP tsp = new TSP(locationsGraph);
        tsp.solve(20000, locationsGraph.getNbVertices()*locationsGraph.getMaxArcCost()+1);
        SolutionState solutionState = tsp.getSolutionState();

        if(solutionState == SolutionState.NO_SOLUTION_FOUND || solutionState == SolutionState.INCONSISTENT) {
            throw new NoPathFoundException("Solution State : " + solutionState.name());
        }

        int[] succesors = tsp.getNext();

        List<Delivery> deliveries = tomorrowDeliveries.getDeliveries();
        Location warehouse = tomorrowDeliveries.getWarehouse();

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
