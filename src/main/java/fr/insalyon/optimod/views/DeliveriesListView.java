package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.TomorrowDeliveriesListener;
import fr.insalyon.optimod.models.TomorrowDeliveries;

/**
 * The TDs list view
 */
class DeliveriesListView implements TomorrowDeliveriesListener {

    private final SelectionListener mSelectionListener;

    public DeliveriesListView(SelectionListener selectionListener) {
        mSelectionListener = selectionListener;
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        // TODO: update the list
    }
}
