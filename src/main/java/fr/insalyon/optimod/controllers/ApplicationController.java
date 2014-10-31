package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.RoadMap;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.views.*;

/**
 * Dispatch the User interactions to the UI components
 */
public class ApplicationController implements Controller, FinishListener, MainToolBarListener, MapClickListener, DeliveriesToolbarListener, RoadMapReorderListener, SelectionListener, TabSelectionListener, RoadMapToolbarListener {

    private static final int TERMINATE_SUCCESS = 0;
    private final ApplicationView mView;
    private final MapChangeListener mMapChangeListener;
    private final RoadMapListener mRoadMapListener;
    private final TomorrowDeliveriesListener mTomorrowDeliveriesListener;
    private final MapPositionMatcher mMapPositionMatcher;
    private final SelectionIntentListener mSelectionIntentListener;
    private Location mSelectedLocation;
    private Map mMap;
    private TomorrowDeliveries mTomorrowDeliveries;
    private RoadMap mRoadMap;

    public ApplicationController() {
        mView = new ApplicationView(this);
        // By doing so, we let us the possibility to bind to other views eventually
        mMapChangeListener = mView;
        mRoadMapListener = mView;
        mTomorrowDeliveriesListener = mView;
        mMapPositionMatcher = mView;
        mSelectionIntentListener = mView;
    }

    @Override
    public void onStart() {
        mView.setVisible(true);
    }

    @Override
    public void onFinish() {
        mView.setVisible(false);
        System.exit(TERMINATE_SUCCESS);
    }

    @Override
    public void onAddDeliveryAction() {
        // TODO: Open a new controller that opens a dialog view and adds an actual delivery
        // NOTE: Don't forget the Command-pattern
        mTomorrowDeliveriesListener.onTomorrowDeliveryChanged(mTomorrowDeliveries);
    }

    @Override
    public void onRemoveDeliveryAction() {
        if(mSelectedLocation != null) {
            // TODO: delete from the TDs the location
            // NOTE: Don't forget the Command-pattern
            mTomorrowDeliveriesListener.onTomorrowDeliveryChanged(mTomorrowDeliveries);
        }
    }

    @Override
    public void onImportMapAction() {
        // TODO: Open a dialog & load the xml map into mMap
        // NOTE: Erase the command history
        mMapChangeListener.onMapChanged(mMap);
    }

    @Override
    public void onImportDeliveriesAction() {
        // TODO: Open a dialog & load the xml TDs into mTDs
        // NOTE: Erase the command history
        mTomorrowDeliveriesListener.onTomorrowDeliveryChanged(mTomorrowDeliveries);
    }

    @Override
    public void onUndoAction() {
        // TODO: Command-pattern UNDO
    }

    @Override
    public void onRedoAction() {
        // TODO: Command-pattern REDO
    }

    @Override
    public void onMapClick(int x, int y) {
        Location location = mMapPositionMatcher.matchLocation(x, y);
        onSelectLocation(location);
    }

    @Override
    public void onRoadMapReorder(int elementIndex, int movedToIndex) {
        // TODO: reorder the map with the indexes
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onSelectLocation(Location location) {
        mSelectionIntentListener.onSelectIntentOnLocation(location);
        mSelectedLocation = location;
    }

    @Override
    public void onRoadMapTabSelected() {
        // TODO: Generate paths and assign mRoadmap
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onDeliveriesTabSelected() {
         mRoadMapListener.onRoadMapChanged(null);
    }

    @Override
    public void onPrintRoadMapAction() {
        // TODO: generate a text file from the roadmap
    }
}
