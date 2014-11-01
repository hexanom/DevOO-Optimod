package fr.insalyon.optimod.controllers.listeners.data;

import fr.insalyon.optimod.models.RoadMap;

/**
 * Binds to a roadmap change
 */
public interface RoadMapListener {
    /**
     * When the roadmap changes
     *
     * @param roadMap A roadmap
     */
    public void onRoadMapChanged(RoadMap roadMap);
}
