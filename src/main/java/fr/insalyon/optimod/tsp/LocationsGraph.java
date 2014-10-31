package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Section;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by edouard on 31/10/14.
 */
public class LocationsGraph implements Graph {

    private int nbVertices;
    private int maxArcCost;
    private int minArcCost;
    private int[][] cost;
    private List<List<Integer>> succ;

    public LocationsGraph(List<Location> locations) {

        nbVertices = locations.size();
        succ = new ArrayList<List<Integer>>();
        minArcCost = Integer.MAX_VALUE;
        maxArcCost = Integer.MIN_VALUE;
        cost = new int[nbVertices][nbVertices];

        int i = 0;
        for (Location location : locations) {

            List<Integer> successors = new ArrayList<Integer>();

            for (Section section : location.getOuts()) {
                Location destination = section.getDestination();
                int j = locations.indexOf(destination);
                if (j != -1) {
                    successors.add(j);
                    int secCost = (int) section.getTime();
                    cost[i][j] = secCost;
                    minArcCost = Math.min(minArcCost, secCost);
                    maxArcCost = Math.max(maxArcCost, secCost);
                }
            }

            succ.add(i, successors);
            i++;
        }
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
