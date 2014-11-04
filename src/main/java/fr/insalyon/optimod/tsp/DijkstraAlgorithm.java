package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Section;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Dijkstra algorithm adapted from
 * http://www.vogella.com/tutorials/JavaAlgorithmsDijkstra/article.html
 */
public class DijkstraAlgorithm {

    private Set<Location> settledNodes;
    private Set<Location> unSettledNodes;
    private Map<Location, Location> predecessors;
    private Map<Location, Double> distance;

    public void execute(Location source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0d);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Location node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Location node) {
        List<Location> adjacentNodes = getNeighbors(node);
        for (Location target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private double getDistance(Location node, Location target) {
        for (Section edge : node.getOuts()) {
            if (edge.getOrigin().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getTime();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private Location getMinimum(Set<Location> vertexes) {
        Location minimum = null;
        for (Location vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private List<Location> getNeighbors(Location node) {
        List<Location> neighbors = new ArrayList<Location>();

        for(Section edge : node.getOuts()) {
            if (edge.getOrigin().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }

        return neighbors;
    }

    private boolean isSettled(Location vertex) {
        return settledNodes.contains(vertex);
    }

    private double getShortestDistance(Location destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<Location> getPath(Location target) {
        LinkedList<Location> path = new LinkedList<Location>();
        Location step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

} 