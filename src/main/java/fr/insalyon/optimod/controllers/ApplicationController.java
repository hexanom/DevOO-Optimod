package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.views.*;

/**
 * Dispatch the User interactions to the UI components
 */
public class ApplicationController implements Controller, FinishListener, MainToolBarListener, MapClickListener, DeliveriesToolbarListener, RoadMapReorderListener {

    private static final int TERMINATE_SUCCESS = 0;
    private final ApplicationView mView;
    private final MapChangeListener mMapChangeListener;
    private final RoadMapListener mRoadMapListener;
    private final TomorrowDeliveriesListener mTomorrowDeliveriesListener;

    public ApplicationController() {
        mView = new ApplicationView();
        mView.setFinishListener(this);
        // By doing so, we let us the possibility to bind to other views eventually
        mMapChangeListener = mView;
        mRoadMapListener = mView;
        mTomorrowDeliveriesListener = mView;
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

    }

    @Override
    public void onRemoveDeliveryAction() {

    }

    @Override
    public void onImportMapAction() {

    }

    @Override
    public void onImportDeliveriesAction() {

    }

    @Override
    public void onUndoAction() {

    }

    @Override
    public void onRedoAction() {

    }

    @Override
    public void onMapClick(int x, int y) {

    }

    @Override
    public void onRoadMapReorder(int elementIndex, int movedToIndex) {

    }
}
