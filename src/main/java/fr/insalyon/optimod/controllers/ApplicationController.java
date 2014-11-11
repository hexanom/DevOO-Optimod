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
import fr.insalyon.optimod.tsp.DijkstraAlgorithm;
import fr.insalyon.optimod.tsp.LocationsGraph;
import fr.insalyon.optimod.tsp.NoPathFoundException;
import fr.insalyon.optimod.tsp.TSP;
import fr.insalyon.optimod.views.ApplicationView;
import fr.insalyon.optimod.views.listeners.action.*;
import fr.insalyon.optimod.views.listeners.activity.FinishListener;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

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
        String path = mFileSelectionIntentListener.onFileSelectionIntent(false);
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
        String path = mFileSelectionIntentListener.onFileSelectionIntent(false);
        if(path != null) {
            URI uri = Paths.get(path).toUri();
            XMLTomorrowDeliveriesFactory factory = new XMLTomorrowDeliveriesFactory(uri, mMap);
            try {
                mTomorrowDeliveries = factory.create();
                mTomorrowDeliveriesListener.onTomorrowDeliveryChanged(mTomorrowDeliveries);
                mRoadMap = new RoadMap();
                mRoadMapListener.onRoadMapChanged(mRoadMap);
                mView.switchToDeliveriesTab();
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
        if(location != null && location.getDelivery() != null) {
            mSelectedDelivery = location.getDelivery();
        }

    }

    @Override
    public void onRoadMapTabSelected() {

        if(mTomorrowDeliveries == null || mTomorrowDeliveries.getTimeWindows().isEmpty()) {
            return;
        }

        try {
            mRoadMap = RoadMap.fromTomorrowDeliveries(mTomorrowDeliveries);
            mRoadMapListener.onRoadMapChanged(mRoadMap);
        } catch(Exception e) {
            mShowErrorIntentListener.onErrorIntent("RoadMap Calculation Error", e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void onDeliveriesTabSelected() {
        if(mRoadMapListener != null) { // mRoadMapListener is null when initializing...
            mRoadMapListener.onRoadMapChanged(new RoadMap());
        }
    }

    @Override
    public void onExportRoadMapAction() {
        String path = mFileSelectionIntentListener.onFileSelectionIntent(true);

        if(path != null) {

            String textualDescription = mRoadMap.exportRoadmap();

            try {
                Files.write(Paths.get(path), textualDescription.getBytes());
            } catch (Exception e) {
                mShowErrorIntentListener.onErrorIntent("Export Error", e.getMessage());
                e.printStackTrace();
            }
        }

    }
}
