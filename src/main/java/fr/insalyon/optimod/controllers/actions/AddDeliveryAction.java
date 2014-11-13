package fr.insalyon.optimod.controllers.actions;

import fr.insalyon.optimod.models.*;

import java.util.LinkedList;

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
    public AddDeliveryAction(RoadMap roadMap, Location before, Location location, Location after) {
        mRoadMap = roadMap;
        mAfter = after;
        mLocation = location;
        mBefore = before;
    }

    @Override
    public void doAction() {

        Delivery delivery = new Delivery(mLocation, null);
        TomorrowDeliveries tomorrowDeliveries = mRoadMap.getTomorrowDeliveries();
        delivery.setTomorrowDeliveries(tomorrowDeliveries);
        TimeWindow timeWindow = mAfter.getDelivery().getTimeWindow();
        delivery.setTimeWindow(timeWindow);
        mLocation.setDelivery(delivery);

        timeWindow.addDelivery(delivery);
        tomorrowDeliveries.addDelivery(delivery);

        Path pathToLoc = Path.fromTwoLocations(mBefore, mLocation);
        Path pathFromLoc = Path.fromTwoLocations(mLocation, mAfter);

        Path pathToReplace = mRoadMap.getPathBetweenLocations(mBefore, mAfter);

        LinkedList<Path> paths = mRoadMap.getPaths();
        int i = paths.indexOf(pathToReplace);
        paths.add(i + 1, pathFromLoc);
        paths.add(i, pathToLoc);
        paths.remove(pathToReplace);
    }

    @Override
    public void undoAction() {

        Delivery deliveryToDelete = mLocation.getDelivery();

        mLocation.setDelivery(null);

        TomorrowDeliveries tomorrowDeliveries = mRoadMap.getTomorrowDeliveries();
        TimeWindow timeWindow = deliveryToDelete.getTimeWindow();

        timeWindow.deleteDelivery(deliveryToDelete);
        tomorrowDeliveries.deleteDelivery(deliveryToDelete);

        Path pathToDelete1 = mRoadMap.getPathBetweenLocations(mBefore, mLocation);
        Path pathToDelete2 = mRoadMap.getPathBetweenLocations(mLocation, mAfter);

        Path pathToInsert = Path.fromTwoLocations(mBefore, mAfter);

        LinkedList<Path> paths = mRoadMap.getPaths();
        int i = paths.indexOf(pathToDelete1);
        paths.add(i, pathToInsert);

        paths.remove(pathToDelete1);
        paths.remove(pathToDelete2);
    }
}
