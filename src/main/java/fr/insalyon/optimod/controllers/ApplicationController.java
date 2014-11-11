package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.controllers.listeners.MapPositionMatcher;
import fr.insalyon.optimod.controllers.listeners.data.MapChangeListener;
import fr.insalyon.optimod.controllers.listeners.data.RoadMapListener;
import fr.insalyon.optimod.controllers.listeners.data.TomorrowDeliveriesListener;
import fr.insalyon.optimod.controllers.listeners.intents.FileSelectionIntentListener;
import fr.insalyon.optimod.controllers.listeners.intents.SelectionIntentListener;
import fr.insalyon.optimod.controllers.listeners.intents.ShowErrorIntentListener;
import fr.insalyon.optimod.models.*;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import fr.insalyon.optimod.models.factories.XMLTomorrowDeliveriesFactory;
import fr.insalyon.optimod.views.ApplicationView;
import fr.insalyon.optimod.views.listeners.action.*;
import fr.insalyon.optimod.views.listeners.activity.FinishListener;

import java.net.URI;
import java.nio.file.Paths;

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
    private final FileSelectionIntentListener mFileSelectionIntentListener;
    private final ShowErrorIntentListener mShowErrorIntentListener;
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
        mFileSelectionIntentListener = mView;
        mShowErrorIntentListener = mView;
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
    public void onAddBeforeAction() {
        // TODO
        // NOTE: Don't forget the Command-pattern
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onAddAfterAction() {
        // TODO
        // NOTE: Don't forget the Command-pattern
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onRemoveDeliveryAction() {
        // TODO
        // NOTE: Don't forget the Command-pattern
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onImportMapAction() {
        String path = mFileSelectionIntentListener.onFileSelectionIntent();
        if(path != null) {
            URI uri = Paths.get(path).toUri();
            XMLMapFactory factory = new XMLMapFactory(uri);
            try {
                mMap = factory.create();
                mMapChangeListener.onMapChanged(mMap);
                mTomorrowDeliveries = new TomorrowDeliveries();
                mTomorrowDeliveriesListener.onTomorrowDeliveryChanged(mTomorrowDeliveries);
                mRoadMap = new RoadMap();
                mRoadMapListener.onRoadMapChanged(mRoadMap);
            } catch (Exception e) {
                mShowErrorIntentListener.onErrorIntent("Import Error", e.getMessage());
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onImportDeliveriesAction() {
        String path = mFileSelectionIntentListener.onFileSelectionIntent();
        if(path != null) {
            URI uri = Paths.get(path).toUri();
            XMLTomorrowDeliveriesFactory factory = new XMLTomorrowDeliveriesFactory(uri, mMap);
            try {
                mTomorrowDeliveries = factory.create();
                mTomorrowDeliveriesListener.onTomorrowDeliveryChanged(mTomorrowDeliveries);
                mRoadMap = new RoadMap();
                mRoadMapListener.onRoadMapChanged(mRoadMap);
            } catch (Exception e) {
                mShowErrorIntentListener.onErrorIntent("Import Error", e.getMessage());
                e.printStackTrace();
            }
        }
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
    }

    @Override
    public void onRoadMapTabSelected() {
        // TODO: Generate paths and assign mRoadmap
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onDeliveriesTabSelected() {
        if(mRoadMapListener != null) { // mRoadMapListener is null when initializing...
            mRoadMapListener.onRoadMapChanged(new RoadMap());
        }
    }

    @Override
    public void onExportRoadMapAction() {
        // TODO: generate a text file from the roadmap
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }
}
