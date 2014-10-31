package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.MapChangeListener;
import fr.insalyon.optimod.controllers.RoadMapListener;
import fr.insalyon.optimod.controllers.TomorrowDeliveriesListener;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.RoadMap;
import fr.insalyon.optimod.models.TomorrowDeliveries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Represents the application as a main window
 */
public class ApplicationView extends JFrame implements WindowListener, MapChangeListener, RoadMapListener, TomorrowDeliveriesListener {
    private JButton mImportMapButton;
    private JButton mImportDeliveriesButton;
    private FinishListener mFinishListener;

    public ApplicationView() {
        initWindow();
        initChildren();
    }

    private void initChildren() {
        JToolBar toolbar = new JToolBar();
        mImportMapButton = new JButton("Import Map");
        toolbar.add(mImportMapButton);
        mImportDeliveriesButton = new JButton("Import Deliveries");
        mImportDeliveriesButton.setEnabled(false);
        toolbar.add(mImportDeliveriesButton);
        add(toolbar, BorderLayout.PAGE_START);
    }

    private void initWindow() {
        setTitle("Optimod");
        setMinimumSize(new Dimension(800, 600));
        setSize(new Dimension(1200, 800));
        setLayout(new BorderLayout());
        addWindowListener(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void setFinishListener(FinishListener listener) {
        mFinishListener = listener;
    }

    @Override
    public void windowClosed(WindowEvent e) {
        mFinishListener.onFinish();
    }

    @Override
    public void onMapChanged(Map map) {

    }

    @Override
    public void onRoadMapChanged(RoadMap roadMap) {

    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {

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
