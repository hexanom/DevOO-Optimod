package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.listeners.data.TomorrowDeliveriesListener;
import fr.insalyon.optimod.controllers.listeners.intents.SelectionIntentListener;
import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.views.listeners.action.SelectionListener;
import fr.insalyon.optimod.views.utils.GenericListModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

/**
 * The TDs list view
 */
public class DeliveriesListView extends JList<Location> implements TomorrowDeliveriesListener, ListSelectionListener, SelectionIntentListener {
    private final SelectionListener mSelectionListener;
    private ArrayList<Location> mLocations;

    public DeliveriesListView(SelectionListener selectionListener) {
        mSelectionListener = selectionListener;
        setCellRenderer(new LocationListViewRenderer());
        addListSelectionListener(this);
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        mLocations = new ArrayList<>();
        if(tomorrowDeliveries != null) {
            for(Delivery d : tomorrowDeliveries.getDeliveries()) {
                mLocations.add(d.getLocation());
            }
        }
        setModel(new GenericListModel<>(mLocations));
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(getSelectedIndex() >= 0) {
            Location location = mLocations.get(getSelectedIndex());
            mSelectionListener.onSelectLocation(location);
        }
    }

    @Override
    public void onSelectIntentOnLocation(Location location) {
        if (location != null && location.getDelivery() != null) {
            setSelectedValue(location, true);
        } else {
            clearSelection();
        }
    }
}
