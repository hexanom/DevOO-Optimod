package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.listeners.data.RoadMapListener;
import fr.insalyon.optimod.controllers.listeners.intents.SelectionIntentListener;
import fr.insalyon.optimod.models.*;
import fr.insalyon.optimod.views.listeners.action.SelectionListener;
import fr.insalyon.optimod.views.utils.GenericListModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The roadmap list view
 */
public class RoadMapListView extends JList<Location> implements RoadMapListener, ListSelectionListener, SelectionIntentListener {
    private final SelectionListener mSelectionListener;
    private List<Location> mLocations;

    public RoadMapListView(SelectionListener selectionListener) {
        mSelectionListener = selectionListener;
        setCellRenderer(new LocationListViewRenderer());
        addListSelectionListener(this);
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        if(getSelectedIndex() >= 0) {
            Location selectedLocation = mLocations.get(getSelectedIndex());
            mSelectionListener.onSelectLocation(selectedLocation);
        }
    }

    @Override
    public void onRoadMapChanged(RoadMap roadMap) {
        mLocations = new ArrayList<>();
        if(roadMap != null) {
            Section last = null;
            for(Path p : roadMap.getPaths()) {
                for(Section s : p.getOrderedSections()) {
                    mLocations.add(s.getOrigin());
                    last = s;
                }
            }
            if(last != null) {
                mLocations.add(last.getDestination());
            }
        }
        setModel(new GenericListModel<>(mLocations));
    }

    @Override
    public void onSelectIntentOnLocation(Location location) {
        if(location != null) {
            setSelectedValue(location, true);
        } else {
            clearSelection();
        }
    }
}
