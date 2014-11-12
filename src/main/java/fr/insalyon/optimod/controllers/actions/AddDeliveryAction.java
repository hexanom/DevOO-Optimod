package fr.insalyon.optimod.controllers.actions;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.RoadMap;

/**
 * An action able to add a location in tomorrow deliveries
 */
public class AddDeliveryAction implements Action {
    private final RoadMap mRoadMap;
    private final Location mAfter;
    private final Location mLocation;
    private final Location mBefore;

    /**
     * Creates the action
     *
     * @param roadMap The roadMap to add in
     * @param after Add after this location
     * @param location Add this location as a delivery
     * @param before Add before this location
     */
    public AddDeliveryAction(RoadMap roadMap, Location after, Location location, Location before) {
        mRoadMap = roadMap;
        mAfter = after;
        mLocation = location;
        mBefore = before;
    }

    @Override
    public void doAction() {
        // TODO
    }

    @Override
    public void undoAction() {
        // TODO
    }
}
