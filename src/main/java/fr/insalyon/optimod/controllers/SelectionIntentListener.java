package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.models.Location;

/**
 * A view able to select a Location dictated by the controller
 */
public interface SelectionIntentListener {
    /**
     * Selects a location in the view
     * @param location The location to select
     */
    public void onSelectIntentOnLocation(Location location);
}
