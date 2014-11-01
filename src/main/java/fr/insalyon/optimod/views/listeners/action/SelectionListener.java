package fr.insalyon.optimod.views.listeners.action;

import fr.insalyon.optimod.models.Location;

/**
 * Binds to a user selection
 */
public interface SelectionListener {
    /**
     * When a selection is done by the user
     * @param location The location to select
     */
    public void onSelectLocation(Location location);
}
