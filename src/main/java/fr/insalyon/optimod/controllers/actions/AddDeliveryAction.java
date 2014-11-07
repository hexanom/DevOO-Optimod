package fr.insalyon.optimod.controllers.actions;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.TomorrowDeliveries;

/**
 * An action able to add a location in tomorrow deliveries
 */
public class AddDeliveryAction implements Action {
    private final TomorrowDeliveries mDeliveries;
    private final Delivery mDelivery;

    /**
     * Creates the action
     *
     * @param deliveries The deliveries to add in
     * @param delivery The delivery to add
     */
    public AddDeliveryAction(TomorrowDeliveries deliveries, Delivery delivery) {
        mDeliveries = deliveries;
        mDelivery = delivery;
    }

    @Override
    public void doAction() {
        mDeliveries.addDelivery(mDelivery);
    }

    @Override
    public void undoAction() {
        mDeliveries.deleteDelivery(mDelivery);
    }
}
