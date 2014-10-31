package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.views.ApplicationView;

/**
 * Dispatch the User interactions to the UI components
 */
public class ApplicationController implements Controller, FinishListener {

    private static final int TERMINATE_SUCCESS = 0;
    private final ApplicationView mApplicationView;

    public ApplicationController() {
        mApplicationView = new ApplicationView();
        mApplicationView.setFinishListener(this);
    }

    @Override
    public void onStart() {
        mApplicationView.setVisible(true);
    }

    @Override
    public void onFinish() {
        mApplicationView.setVisible(false);
        System.exit(TERMINATE_SUCCESS);
    }
}
