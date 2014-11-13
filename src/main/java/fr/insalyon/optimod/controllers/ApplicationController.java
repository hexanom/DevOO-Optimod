package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.controllers.actions.Action;
import fr.insalyon.optimod.controllers.actions.AddDeliveryAction;
import fr.insalyon.optimod.controllers.actions.DeleteDeliveryAction;
import fr.insalyon.optimod.controllers.listeners.MapPositionMatcher;
import fr.insalyon.optimod.controllers.listeners.data.MapChangeListener;
import fr.insalyon.optimod.controllers.listeners.data.RoadMapListener;
import fr.insalyon.optimod.controllers.listeners.data.TomorrowDeliveriesListener;
import fr.insalyon.optimod.controllers.listeners.intents.FileSelectionIntentListener;
import fr.insalyon.optimod.controllers.listeners.intents.MapDisplayListener;
import fr.insalyon.optimod.controllers.listeners.intents.SelectionIntentListener;
import fr.insalyon.optimod.controllers.listeners.intents.ShowErrorIntentListener;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.RoadMap;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import fr.insalyon.optimod.models.factories.XMLTomorrowDeliveriesFactory;
import fr.insalyon.optimod.views.ApplicationView;
import fr.insalyon.optimod.views.listeners.action.*;
import fr.insalyon.optimod.views.listeners.activity.FinishListener;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Dispatch the User interactions to the UI components
 */
public class ApplicationController extends HistoryEnabledController implements FinishListener, MainToolBarListener, MapClickListener, SelectionListener, TabSelectionListener, RoadMapToolbarListener, MapDisplayListener {

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
    private boolean mSelectionMode = false;
    private boolean mAddBefore = false;
    private Location mSelectedLocation = null;

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
        setHistoryListener(mView);
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
        mSelectionMode = true;
        mAddBefore = true;
    }

    @Override
    public void onAddAfterAction() {
        mSelectionMode = true;
        mAddBefore = false;
    }

    @Override
    public void onRemoveDeliveryAction() {
        mSelectionMode = false;
        if(mSelectedLocation != null && mSelectedLocation.getDelivery() != null) {
            doAction(new DeleteDeliveryAction(mRoadMap,
                    mRoadMap.getDeliveryLocationBefore(mSelectedLocation),
                    mSelectedLocation,
                    mRoadMap.getDeliveryLocationAfter(mSelectedLocation)));
            mRoadMapListener.onRoadMapChanged(mRoadMap);
            mTomorrowDeliveriesListener.onTomorrowDeliveryChanged(mTomorrowDeliveries);
        }
    }

    @Override
    public void onImportMapAction() {
        clearHistory();
        mSelectionMode = false;
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
        clearHistory();
        mSelectionMode = false;
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
        mSelectionMode = false;
        undo();
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onRedoAction() {
        mSelectionMode = false;
        redo();
        mRoadMapListener.onRoadMapChanged(mRoadMap);
    }

    @Override
    public void onMapClick(int x, int y) {
        Location location = mMapPositionMatcher.matchLocation(x, y);
        onSelectLocation(location);
    }

    @Override
    public void onSelectLocation(Location location) {
        if(mSelectionMode && mSelectedLocation != null && mSelectedLocation.getDelivery() != null) {
            Action addAction;
            if(mAddBefore) {
                addAction = new AddDeliveryAction(mRoadMap,
                        mRoadMap.getDeliveryLocationBefore(mSelectedLocation),
                        location,
                        mSelectedLocation);
            } else {
                addAction = new AddDeliveryAction(mRoadMap,
                        mSelectedLocation,
                        location,
                        mRoadMap.getDeliveryLocationAfter(mSelectedLocation));
            }
            doAction(addAction);

            if(!mRoadMap.isRespectingTimeWindows()) {
                mShowErrorIntentListener.onErrorIntent("RoadMap Error", "Roadmap is not respecting the time windows");
                onUndoAction();
                return;
            }

            mRoadMapListener.onRoadMapChanged(mRoadMap);
            mTomorrowDeliveriesListener.onTomorrowDeliveryChanged(mTomorrowDeliveries);
            mSelectionMode = false;
        }
        mSelectedLocation = location;
        mSelectionIntentListener.onSelectIntentOnLocation(location);
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
        clearHistory();
        mSelectionMode = false;
        if(mRoadMapListener != null) { // mRoadMapListener is null when initializing...
            mRoadMapListener.onRoadMapChanged(new RoadMap());

        }
    }

    @Override
    public void onExportRoadMapAction() {
        String path = mFileSelectionIntentListener.onFileSelectionIntent(true);

        if(path != null && mRoadMap != null) {

            String textualDescription = mRoadMap.exportRoadmap();

            try {
                Files.write(Paths.get(path), textualDescription.getBytes());
            } catch (Exception e) {
                mShowErrorIntentListener.onErrorIntent("Export Error", e.getMessage());
                e.printStackTrace();
            }
        }

    }

    @Override
    public void toggleSectionNames(boolean enabled) {
        mView.toggleSectionNames(enabled);
    }

    @Override
    public void toggleLocationNames(boolean enabled) {
        mView.toggleLocationNames(enabled);
    }
}
