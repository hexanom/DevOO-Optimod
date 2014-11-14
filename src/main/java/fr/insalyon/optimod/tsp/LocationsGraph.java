package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

/**
 * Created by edouard on 31/10/14.
 */
public class LocationsGraph implements Graph {

    private int nbVertices;
    private int maxArcCost;
    private int minArcCost;
    private int[][] cost;
    private List<List<Integer>> succ;

    private TomorrowDeliveries mTomorrowDeliveries;


    private static final int WAREHOUSE_INDEX = 0;

    /**
     * Create a location graph from a tomorrowDeliveries object.
     * Will link timewindow's own deliveries between themselves (complete graph).
     * Will link the warehouse to the first timewindow's deliveries
     * Will link the last timewindow's deliveries to the warehouse
     * @param tomorrowDeliveries
     * @throws NoPathFoundException
     */
    public LocationsGraph(TomorrowDeliveries tomorrowDeliveries) throws NoPathFoundException {

        List<Delivery> deliveries = tomorrowDeliveries.getDeliveries();
        Location warehouse = tomorrowDeliveries.getWarehouse();
        TreeSet<TimeWindow> timeWindows = tomorrowDeliveries.getTimeWindows();

        mTomorrowDeliveries = tomorrowDeliveries;
        nbVertices = deliveries.size() + 1;
        succ = new ArrayList<List<Integer>>();
        minArcCost = Integer.MAX_VALUE;
        maxArcCost = Integer.MIN_VALUE;
        cost = new int[nbVertices][nbVertices];


        // Connect warehouse to deliveries in the first time window
        TimeWindow firstTw = timeWindows.first();
        List<Delivery> firstDeliveries = firstTw.getDeliveries();
        List<Integer> firstSuccessors = connectDeliveryToDeliveries(null, firstDeliveries);
        succ.add(0, firstSuccessors);

        // Connect deliveries
        int i = 1;
        for (Delivery delivery : deliveries) {
            Location deliveryLocation = delivery.getLocation();

            ArrayList<Integer> successors = new ArrayList<>();
            succ.add(i, successors);

            TimeWindow timeWindow = delivery.getTimeWindow();

            // Connect to deliveries OR warehouse in the next time window
            TimeWindow nextTimeWindow = timeWindows.higher(timeWindow);
            // Connect to the warehouse
            if (nextTimeWindow == null) {
                int pathCost = costBetweenLocations(deliveryLocation, warehouse);
                updateCostBetweenLocations(i, WAREHOUSE_INDEX, pathCost);
                successors.add(WAREHOUSE_INDEX);
            }
            // Connect to the deliveries in the next time window
            else {
                List<Delivery> nextTwDeliveries = nextTimeWindow.getDeliveries();
                List<Integer> nextTwSuccessors = connectDeliveryToDeliveries(delivery, nextTwDeliveries);
                successors.addAll(nextTwSuccessors);
            }

            // Connect to deliveries in the same time window
            List<Delivery> sameTwDeliveries = timeWindow.getDeliveries();
            List<Integer> sameTwSuccesors = connectDeliveryToDeliveries(delivery, sameTwDeliveries);
            successors.addAll(sameTwSuccesors);

            i++;
        }

    }

    /**
     * List the indexes of all the successors (destinations) of origin.
     * Updates minArcCost, maxArcCost and costs.
     * @param origin If null, it's the warehouse
     * @param destinations
     * @return The index of the successors of origin
     * @throws NoPathFoundException
     */
    private List<Integer> connectDeliveryToDeliveries(Delivery origin, List<Delivery> destinations)
            throws NoPathFoundException {
        List<Integer> successors = new ArrayList<>(destinations.size());
        Location originLocation = origin != null ? origin.getLocation() : mTomorrowDeliveries.getWarehouse();
        for (Delivery delivery : destinations) {

            Location destLocation = delivery.getLocation();

            if (destLocation == originLocation) {
                continue;
            }

            int indexOfOriginDelivery = origin != null ? getIndexOfDelivery(origin) : WAREHOUSE_INDEX;
            int indexOfDestDelivery = getIndexOfDelivery(delivery);
            int pathCost = costBetweenLocations(originLocation, destLocation);

            updateCostBetweenLocations(indexOfOriginDelivery, indexOfDestDelivery, pathCost);

            successors.add(indexOfDestDelivery);
        }
        return successors;
    }

    /**
     * Returns the index of the delivery
     * @param loc
     * @return
     */
    private int getIndexOfDelivery(Delivery loc) {
        return 1 + mTomorrowDeliveries.getDeliveries().indexOf(loc);
    }

    /**
     * Update the minArcCost, maxArcCost (if necessary) and cost of a path
     * @param i
     * @param j
     * @param pathCost
     */
    private void updateCostBetweenLocations(int i, int j, int pathCost) {
        cost[i][j] = pathCost;
        minArcCost = Math.min(minArcCost, pathCost);
        maxArcCost = Math.max(maxArcCost, pathCost);
    }

    /**
     * Calculate the cost a path between two locations using the best path provided by dijkstra
     * @param delivery
     * @param otherDelivery
     * @return
     * @throws NoPathFoundException
     */
    private int costBetweenLocations(Location delivery, Location otherDelivery) throws NoPathFoundException {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        dijkstra.execute(delivery);
        LinkedList<Location> path = dijkstra.getPath(otherDelivery);

        if(path == null) {
            throw new NoPathFoundException("No path found between " + delivery.getAddress()
                    + " and " + otherDelivery.getAddress() + ".");
        }

        return (int) pathTimeSum(path);
    }

    /**
     * Calculate the total time for a path
     * @param path
     * @return
     */
    private double pathTimeSum(List<Location> path) {
        double sum = 0;
        for (int i = 0; i < path.size() - 1; i++) {
            for (Section s : path.get(i).getOuts()) {
                if (s.getDestination().equals(path.get(i + 1))) {
                    sum += s.getTime();
                    break;
                }
            }
        }
        return sum;
    }

    @Override
    public int getMaxArcCost() {
        return maxArcCost;
    }

    @Override
    public int getMinArcCost() {
        return minArcCost;
    }

    @Override
    public int getNbVertices() {
        return nbVertices;
    }

    @Override
    public int[][] getCost() {
        return cost;
    }

    public int[] getSucc(int i) throws ArrayIndexOutOfBoundsException {
        if ((i < 0) || (i >= nbVertices))
            throw new ArrayIndexOutOfBoundsException();
        int[] tab = new int[succ.get(i).size()];
        for (int j = 0; j < tab.length; j++) {
            tab[j] = succ.get(i).get(j);
        }
        return tab;
    }


    public int getNbSucc(int i) throws ArrayIndexOutOfBoundsException {
        if ((i < 0) || (i >= nbVertices))
            throw new ArrayIndexOutOfBoundsException();
        return succ.get(i).size();
    }
}
