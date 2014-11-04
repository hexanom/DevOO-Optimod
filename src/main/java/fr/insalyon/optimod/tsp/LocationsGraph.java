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

    public LocationsGraph(Location warehouse, List<Delivery> deliveries) {

        this.deliveries = deliveries;
        this.warehouse = warehouse;

        Map<TimeWindow, List<Delivery>> twDeliveries = new HashMap<>();
        TreeSet<TimeWindow> timeWindows;

        nbVertices = deliveries.size() + 1;
        succ = new ArrayList<List<Integer>>();
        minArcCost = Integer.MAX_VALUE;
        maxArcCost = Integer.MIN_VALUE;
        cost = new int[nbVertices][nbVertices];

        // Discriminate by timewindow for fast search afterwards
        for (Delivery del : deliveries) {
            TimeWindow timeWindow = del.getTimeWindow();
            if (!twDeliveries.containsKey(timeWindow)) {
                twDeliveries.put(timeWindow, new ArrayList<Delivery>());
            }
            twDeliveries.get(timeWindow).add(del);
        }
        timeWindows = new TreeSet<>(twDeliveries.keySet());

        // Warehouse to first timeWindow deliveries
        List<Integer> firstSucc = new ArrayList<>();
        succ.add(0, firstSucc);
        TimeWindow firstTw = timeWindows.first();
        List<Delivery> firstDeliveries = twDeliveries.get(firstTw);
        for (Delivery delivery : firstDeliveries) {
            int pathCost = costBetweenLocations(warehouse, delivery);
            int indexOfDelivery = getIndexOfDelivery(delivery);
            updateCostBetweenLocations(WAREHOUSE_INDEX, indexOfDelivery, pathCost);
            firstSucc.add(indexOfDelivery);
        }

        // Deliveries
        int i = 0;
        for (Delivery delivery : deliveries) {
            TimeWindow timeWindow = delivery.getTimeWindow();
            List<Delivery> relatedDeliveries = twDeliveries.get(timeWindow);

            ArrayList<Integer> successors = new ArrayList<>();
            succ.add(i, successors);

            for (Delivery otherDelivery : relatedDeliveries) {
                if (otherDelivery == delivery) {
                    continue;
                }
                int j = getIndexOfDelivery(delivery);

                TimeWindow nextTimeWindow = timeWindows.higher(timeWindow);
                if (nextTimeWindow == null) { // Case : last time window
                    int pathCost = costBetweenLocations(delivery, warehouse);
                    updateCostBetweenLocations(i, WAREHOUSE_INDEX, pathCost);
                } else {
                    List<Delivery> nextDeliveries = twDeliveries.get(nextTimeWindow);
                    for (Delivery nextDelivery : nextDeliveries) {
                        int pathCost = costBetweenLocations(delivery, nextDelivery);
                        updateCostBetweenLocations(i, deliveries.indexOf(nextDelivery), pathCost);
                    }
                }

                int pathCost = costBetweenLocations(delivery, otherDelivery);
                updateCostBetweenLocations(i, j, pathCost);
                successors.add(j);
            }
            i++;
        }

    }

    private int getIndexOfDelivery(Delivery loc) {
        return 1 + deliveries.indexOf(loc);
    }

    private void updateCostBetweenLocations(int i, int j, int pathCost) {
        cost[i][j] = pathCost;
        minArcCost = Math.min(minArcCost, pathCost);
        maxArcCost = Math.max(maxArcCost, pathCost);
    }

    private int costBetweenLocations(Location delivery, Location otherDelivery) {
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm();
        dijkstra.execute(delivery);
        LinkedList<Location> path = dijkstra.getPath(otherDelivery);
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
