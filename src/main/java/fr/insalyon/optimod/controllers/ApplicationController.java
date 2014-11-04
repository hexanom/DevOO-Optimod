package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.controllers.actions.DeleteDeliveryAction;
import fr.insalyon.optimod.controllers.listeners.MapPositionMatcher;
import fr.insalyon.optimod.controllers.listeners.data.MapChangeListener;
import fr.insalyon.optimod.controllers.listeners.data.RoadMapListener;
import fr.insalyon.optimod.controllers.listeners.data.TomorrowDeliveriesListener;
import fr.insalyon.optimod.controllers.listeners.intents.SelectionIntentListener;
import fr.insalyon.optimod.models.*;
import fr.insalyon.optimod.views.*;
import fr.insalyon.optimod.views.listeners.action.*;
import fr.insalyon.optimod.views.listeners.activity.FinishListener;

/**
 * Dispatch the User interactions to the UI components
 */
public class ApplicationController extends HistoryEnabledController implements FinishListener, MainToolBarListener, MapClickListener, SelectionListener, TabSelectionListener, RoadMapToolbarListener {

    private static final int TERMINATE_SUCCESS = 0;
    private final ApplicationView mView;
    private final MapChangeListener mMapChangeListener;
    private final RoadMapListener mRoadMapListener;
    private final TomorrowDeliveriesListener mTomorrowDeliveriesListener;
    private final MapPositionMatcher mMapPositionMatcher;
    private final SelectionIntentListener mSelectionIntentListener;
    private Delivery mSelectedDelivery;
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
        if(mSelectedDelivery != null) {
            doAction(new DeleteDeliveryAction(mTomorrowDeliveries, mSelectedDelivery));
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
        undo();
    }

    @Override
    public void onRedoAction() {
        redo();
    }

    @Override
    public void onMapClick(int x, int y) {
        Location location = mMapPositionMatcher.matchLocation(x, y);
        onSelectLocation(location);
    }

    @Override
    public void onSelectLocation(Location location) {
        mSelectionIntentListener.onSelectIntentOnLocation(location);
        if(location instanceof Delivery) {
            mSelectedDelivery = (Delivery) location;
        } else {
            mSelectedDelivery = null;
        }

    }

    @Override
    public void onRoadMapTabSelected() {
        // TODO: Generate paths and assign mRoadmap
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onDeliveriesTabSelected() {
        if(mRoadMapListener != null) { // mRoadMapListener is null when initializing...
            mRoadMapListener.onRoadMapChanged(null);
        }
    }

    @Override
    public void onExportRoadMapAction() {
        // TODO: generate a text file from the roadmap
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }
}
