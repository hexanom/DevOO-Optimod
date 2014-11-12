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

    private Delivery mDelivery;

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

        mDelivery = new Delivery(mLocation, null);
        TomorrowDeliveries tomorrowDeliveries = mRoadMap.getTomorrowDeliveries();
        mDelivery.setTomorrowDeliveries(tomorrowDeliveries);
        TimeWindow timeWindow = mAfter.getDelivery().getTimeWindow();
        mDelivery.setTimeWindow(timeWindow);

        timeWindow.addDelivery(mDelivery);
        tomorrowDeliveries.addDelivery(mDelivery);

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

        TomorrowDeliveries tomorrowDeliveries = mRoadMap.getTomorrowDeliveries();
        TimeWindow timeWindow = mDelivery.getTimeWindow();

        timeWindow.deleteDelivery(mDelivery);
        tomorrowDeliveries.deleteDelivery(mDelivery);

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
