package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.TomorrowDeliveriesListener;
import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.views.utils.GenericListModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;

/**
 * The TDs list view
 */
public class DeliveriesListView extends JList<Delivery> implements TomorrowDeliveriesListener, ListSelectionListener {
    private final SelectionListener mSelectionListener;

    public DeliveriesListView(SelectionListener selectionListener) {
        mSelectionListener = selectionListener;
        setCellRenderer(new DeliveryListViewRenderer());
        addListSelectionListener(this);
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        if(tomorrowDeliveries != null) {
            setModel(new GenericListModel<Delivery>(tomorrowDeliveries.getDeliveries()));
        } else {
            setModel(new GenericListModel<Delivery>(new ArrayList<Delivery>()));
        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        mSelectionListener.onSelectLocation((Location) e.getSource());
    }
}
