package fr.insalyon.optimod.controllers.actions;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.RoadMap;

/**
 * An action able to delete a location in tomorrow deliveries
 */
public class DeleteDeliveryAction implements Action {
    private final AddDeliveryAction mAddDeliveryAction;

    /**
     * Creates the action
     *
     * @param roadMap The roadMap to add in
     * @param before Delete before this location
     * @param after Delete after this location
     * @param location Delete this location as a delivery
     */
    public DeleteDeliveryAction(RoadMap roadMap, Location before, Location location, Location after) {
        mAddDeliveryAction = new AddDeliveryAction(roadMap, before, location, after);
    }

    @Override
    public void doAction() {
        mAddDeliveryAction.undoAction();
    }

    @Override
    public void undoAction() {
        mAddDeliveryAction.doAction();
    }
}
