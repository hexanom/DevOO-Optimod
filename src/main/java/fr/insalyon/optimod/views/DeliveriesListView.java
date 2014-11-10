package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.listeners.intents.SelectionIntentListener;
import fr.insalyon.optimod.controllers.listeners.data.TomorrowDeliveriesListener;
import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.views.listeners.action.SelectionListener;
import fr.insalyon.optimod.views.utils.GenericListModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The TDs list view
 */
public class DeliveriesListView extends JList<Delivery> implements TomorrowDeliveriesListener, ListSelectionListener, SelectionIntentListener {
    private final SelectionListener mSelectionListener;
    private List<Delivery> mDeliveries;

    public DeliveriesListView(SelectionListener selectionListener) {
        mSelectionListener = selectionListener;
        setCellRenderer(new DeliveryListViewRenderer());
        addListSelectionListener(this);
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        if(tomorrowDeliveries != null) {
            mDeliveries = tomorrowDeliveries.getDeliveries();
        } else {
            mDeliveries = new ArrayList<Delivery>();
        }
        setModel(new GenericListModel<Delivery>(mDeliveries));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(getSelectedIndex() >= 0) {
            Delivery selectedDelivery = mDeliveries.get(getSelectedIndex());
            mSelectionListener.onSelectLocation(selectedDelivery.getLocation());
        }
    }

    @Override
    public void onSelectIntentOnLocation(Location location) {
        if (location != null && location.getDelivery() != null) {
            setSelectedValue(location.getDelivery(), true);
        } else {
            clearSelection();
        }
    }
}
