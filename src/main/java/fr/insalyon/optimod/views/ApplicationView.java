package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.*;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.RoadMap;
import fr.insalyon.optimod.models.TomorrowDeliveries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Represents the application as a main window
 */
public class ApplicationView extends JFrame implements WindowListener, MapChangeListener, MapPositionMatcher, RoadMapListener, TomorrowDeliveriesListener, SelectionIntentListener, ActionListener {
    private final DeliveriesToolbarListener mDeliveriesToolbarListener;
    private final FinishListener mFinishListener;
    private final MainToolBarListener mMainToolbarListener;
    private final MapClickListener mMapClickListener;
    private final RoadMapReorderListener mRoadMapReorderListener;
    private final RoadMapToolbarListener mRoadMapToolbarListener;
    private final SelectionListener mSelectionListener;
    private final TabSelectionListener mTabSelectionListener;
    private JButton mImportMapButton;
    private JButton mImportDeliveriesButton;

    public ApplicationView(ApplicationController controller) {
        mDeliveriesToolbarListener = controller;
        mFinishListener = controller;
        mMainToolbarListener = controller;
        mMapClickListener = controller;
        mRoadMapReorderListener = controller;
        mRoadMapToolbarListener = controller;
        mSelectionListener = controller;
        mTabSelectionListener = controller;
        initWindow();
        initChildren();
    }

    private void initChildren() {
        JToolBar toolbar = new JToolBar();
        mImportMapButton = new JButton("Import Map");
        mImportMapButton.addActionListener(this);
        toolbar.add(mImportMapButton);
        mImportDeliveriesButton = new JButton("Import Deliveries");
        mImportDeliveriesButton.setEnabled(false);
        toolbar.add(mImportDeliveriesButton);
        add(toolbar, BorderLayout.PAGE_START);

        // TODO: construct UI
    }

    private void initWindow() {
        setTitle("Optimod");
        setMinimumSize(new Dimension(800, 600));
        setSize(new Dimension(1200, 800));
        setLayout(new BorderLayout());
        addWindowListener(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        mFinishListener.onFinish();
    }

    @Override
    public void onMapChanged(Map map) {
        mImportDeliveriesButton.setEnabled(true);
        // TODO: repaint map view
    }

    @Override
    public void onRoadMapChanged(RoadMap roadMap) {
        // TODO: repaint mapview (w/ roadmap drawed upon it) & RM list view
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        // TODO: repaint mapview (w/ TDs drawed upon it) & TDs list view
    }

    @Override
    public Location matchLocation(int x, int y) {
        // TODO: Interrogate LocationViews to find out which Location corresponds
        return null;
    }

    @Override
    public void onSelectIntentOnLocation(Location location) {
        // TODO: Select on MapView the location and in the TDs or RM list
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(mImportMapButton)) {
            mMainToolbarListener.onImportMapAction();
        } else if(e.getSource().equals(mImportDeliveriesButton)) {
            mMainToolbarListener.onImportMapAction();
        }
    }

    // Note: we don't want to do anything with those but we have to implement them
    @Override
    public void windowOpened(WindowEvent e) {}
    @Override
    public void windowClosing(WindowEvent e) {}
    @Override
    public void windowIconified(WindowEvent e) {}
    @Override
    public void windowDeiconified(WindowEvent e) {}
    @Override
    public void windowActivated(WindowEvent e) {}
    @Override
    public void windowDeactivated(WindowEvent e) {}
}
