package fr.insalyon.optimod.controllers.actions;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.TomorrowDeliveries;

/**
 * An action able to delete a location in tomorrow deliveries
 */
public class DeleteDeliveryAction implements Action {
    private final AddDeliveryAction mAddDeliveryAction;

    /**
     * Creates the action
     *
     * @param deliveries The deliveries to delete in
     * @param delivery The delivery to delete
     */
    public DeleteDeliveryAction(TomorrowDeliveries deliveries, Delivery delivery) {
        mAddDeliveryAction = new AddDeliveryAction(deliveries, delivery);
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
