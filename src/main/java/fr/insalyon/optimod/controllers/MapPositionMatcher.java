package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.models.Location;

/**
 * Able to match an x & y pos on a map to an actual location
 */
public interface MapPositionMatcher {
    /**
     * Matches a pixel coordinate with an actual location
     *
     * @param x The x pixel coordinate
     * @param y The y pixel coordinate
     * @return The location
     */
    public Location matchLocation(int x, int y);
}
