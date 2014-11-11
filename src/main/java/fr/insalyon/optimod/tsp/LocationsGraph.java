package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.*;

import java.util.*;
import java.util.Map;

/**
 * Created by edouard on 31/10/14.
 */
public class LocationsGraph implements Graph {

    private int nbVertices;
    private int maxArcCost;
    private int minArcCost;
    private int[][] cost;
    private List<List<Integer>> succ;

    private List<Delivery> deliveries;
    private Location warehouse;

    private static final int WAREHOUSE_INDEX = 0;

    public LocationsGraph(Location warehouse, List<Delivery> deliveries) throws NoPathFoundException {

        this.deliveries = deliveries;
        this.warehouse = warehouse;


        nbVertices = deliveries.size() + 1;
        succ = new ArrayList<List<Integer>>();
        minArcCost = Integer.MAX_VALUE;
        maxArcCost = Integer.MIN_VALUE;
        cost = new int[nbVertices][nbVertices];

        // Discriminate by timewindow for fast search afterwards
        Map<TimeWindow, List<Delivery>> twDeliveries = discriminateByTimeWindow(deliveries);
        TreeSet<TimeWindow> timeWindows = new TreeSet<>(TimeWindow.COMPARATOR);
        timeWindows.addAll(twDeliveries.keySet());

        // Connect warehouse to deliveries in the first time window
        TimeWindow firstTw = timeWindows.first();
        List<Delivery> firstDeliveries = twDeliveries.get(firstTw);
        List<Integer> firstSuccessors = connectLocationToDeliveries(WAREHOUSE_INDEX, warehouse, firstDeliveries);
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
                List<Delivery> nextDeliveries = twDeliveries.get(nextTimeWindow);
                List<Integer> connectedSuccessors = connectLocationToDeliveries(i, deliveryLocation, nextDeliveries);
                successors.addAll(connectedSuccessors);
            }

            // Connect to deliveries in the same time window
            List<Delivery> relatedDeliveries = twDeliveries.get(timeWindow);
            List<Integer> connectedSuccessors = connectLocationToDeliveries(i, deliveryLocation, relatedDeliveries);
            successors.addAll(connectedSuccessors);

            i++;
        }

    }

    private Map<TimeWindow, List<Delivery>> discriminateByTimeWindow(List<Delivery> deliveries) {
        Map<TimeWindow, List<Delivery>> twDeliveries = new HashMap<>();

        for (Delivery del : deliveries) {
            TimeWindow timeWindow = del.getTimeWindow();
            if (!twDeliveries.containsKey(timeWindow)) {
                twDeliveries.put(timeWindow, new ArrayList<Delivery>());
            }
            twDeliveries.get(timeWindow).add(del);
        }
        return twDeliveries;
    }

    private List<Integer> connectLocationToDeliveries(int deliveryIndex, Location origin, List<Delivery> destinations)
            throws NoPathFoundException {
        List<Integer> successors = new ArrayList<>(destinations.size());
        for (Delivery delivery : destinations) {

            Location deliveryLocation = delivery.getLocation();

            if (deliveryLocation == origin) {
                continue;
            }

            int indexOfConnectedDelivery = getIndexOfDelivery(delivery);
            int pathCost = costBetweenLocations(origin, deliveryLocation);
            updateCostBetweenLocations(deliveryIndex, indexOfConnectedDelivery, pathCost);
            successors.add(indexOfConnectedDelivery);
        }
        return successors;
    }

    private int getIndexOfDelivery(Delivery loc) {
        return 1 + deliveries.indexOf(loc);
    }

    private void updateCostBetweenLocations(int i, int j, int pathCost) {
        cost[i][j] = pathCost;
        minArcCost = Math.min(minArcCost, pathCost);
        maxArcCost = Math.max(maxArcCost, pathCost);
    }

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
