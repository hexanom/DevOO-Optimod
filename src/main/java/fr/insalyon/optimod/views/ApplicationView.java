package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.*;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.RoadMap;
import fr.insalyon.optimod.models.TomorrowDeliveries;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;

/**
 * Represents the application as a main window
 */
public class ApplicationView extends JFrame implements WindowListener, MapChangeListener, MapPositionMatcher, RoadMapListener, TomorrowDeliveriesListener, SelectionIntentListener, ActionListener, ChangeListener {
    private final DeliveriesToolbarListener mDeliveriesToolbarListener;
    private final FinishListener mFinishListener;
    private final MainToolBarListener mMainToolbarListener;
    private final MapClickListener mMapClickListener;
    private final RoadMapReorderListener mRoadMapReorderListener;
    private final RoadMapToolbarListener mRoadMapToolbarListener;
    private final SelectionListener mSelectionListener;
    private final TabSelectionListener mTabSelectionListener;
    private JButton mImportMapButton;
    private JMenuItem mImportMapMenuItem;
    private JButton mImportDeliveriesButton;
    private JMenuItem mImportDeliveriesMenuItem;
    private JMenuItem mUndoMenuItem;
    private JMenuItem mRedoMenuItem;
    private JTabbedPane mTabbedPane;
    private JButton mAddDeliveryButton;
    private JMenuItem mAddDeliveryMenuItem;
    private JButton mDeleteDeliveryButton;
    private JMenuItem mDeleteDeliveryMenuItem;
    private JButton mExportRoadMapButton;
    private JMenuItem mExportRoadMapMenuItem;
    private DeliveriesListView mDeliveriesListView;

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
        JComponent mainToolbar = new JPanel();
            mImportMapButton = new JButton("Import Map");
            mImportMapButton.addActionListener(this);
            mainToolbar.add(mImportMapButton);

            mImportDeliveriesButton = new JButton("Import Deliveries");
            mImportDeliveriesButton.setEnabled(false);
            mainToolbar.add(mImportDeliveriesButton);
        add(mainToolbar, BorderLayout.PAGE_START);

        mTabbedPane = new JTabbedPane();
        mTabbedPane.setPreferredSize(new Dimension(200, 400));
        mTabbedPane.addChangeListener(this);
            JComponent deliveriesTab = new JPanel(new BorderLayout());
                JComponent deliveriesToolbar = new JPanel();
                    mAddDeliveryButton = new JButton("+");
                    mAddDeliveryButton.setEnabled(false);
                    deliveriesToolbar.add(mAddDeliveryButton);

                    mDeleteDeliveryButton = new JButton("-");
                    mDeleteDeliveryButton.setEnabled(false);
                    deliveriesToolbar.add(mDeleteDeliveryButton);
                deliveriesTab.add(deliveriesToolbar, BorderLayout.PAGE_END);

                mDeliveriesListView = new DeliveriesListView(mSelectionListener);
                deliveriesTab.add(mDeliveriesListView, BorderLayout.CENTER);
            mTabbedPane.addTab("Deliveries", deliveriesTab);

            JComponent roadMapTab = new JPanel(new BorderLayout());
                JComponent roadMapToolbar = new JPanel();
                    mExportRoadMapButton = new JButton("Export Road Map");
                    roadMapToolbar.add(mExportRoadMapButton);
                roadMapTab.add(roadMapToolbar, BorderLayout.PAGE_END);
            mTabbedPane.addTab("Road Map", roadMapTab);
        add(mTabbedPane, BorderLayout.EAST);

        // TODO: construct UI
    }

    private void initWindow() {
        setTitle("Optimod");
        setMinimumSize(new Dimension(800, 600));
        setSize(new Dimension(1200, 800));
        setLayout(new BorderLayout());
        addWindowListener(this);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        getRootPane().putClientProperty("apple.awt.brushMetalLook", true);

        int keyMask = Toolkit.getDefaultToolkit().getMenuShortcutKeyMask();
        JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("File");
                mImportMapMenuItem = new JMenuItem("Import Map");
                mImportMapMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, keyMask));
                mImportMapMenuItem.addActionListener(this);
                fileMenu.add(mImportMapMenuItem);

                mImportDeliveriesMenuItem = new JMenuItem("Import Deliveries");
                mImportDeliveriesMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, keyMask | InputEvent.SHIFT_DOWN_MASK));
                mImportDeliveriesMenuItem.setEnabled(false);
                mImportMapMenuItem.addActionListener(this);
                fileMenu.add(mImportDeliveriesMenuItem);

                fileMenu.addSeparator();

                mExportRoadMapMenuItem = new JMenuItem("Export Road Map");
                mExportRoadMapMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, keyMask));
                mExportRoadMapMenuItem.setEnabled(false);
                mExportRoadMapMenuItem.addActionListener(this);
                fileMenu.add(mExportRoadMapMenuItem);
            menuBar.add(fileMenu);

            JMenu editMenu = new JMenu("Edit");
                mUndoMenuItem = new JMenuItem("Undo");
                mUndoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, keyMask));
                mUndoMenuItem.setEnabled(false);
                mUndoMenuItem.addActionListener(this);
                editMenu.add(mUndoMenuItem);

                mRedoMenuItem = new JMenuItem("Redo");
                mRedoMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, keyMask | InputEvent.SHIFT_DOWN_MASK));
                mRedoMenuItem.setEnabled(false);
                mRedoMenuItem.addActionListener(this);
                editMenu.add(mRedoMenuItem);

                editMenu.addSeparator();

                mAddDeliveryMenuItem = new JMenuItem("Add Delivery");
                mAddDeliveryMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, keyMask));
                mAddDeliveryMenuItem.setEnabled(false);
                mAddDeliveryMenuItem.addActionListener(this);
                editMenu.add(mAddDeliveryMenuItem);

                mDeleteDeliveryMenuItem = new JMenuItem("Delete Delivery");
                mDeleteDeliveryMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, keyMask));
                mDeleteDeliveryMenuItem.setEnabled(false);
                mDeleteDeliveryMenuItem.addActionListener(this);
                editMenu.add(mDeleteDeliveryMenuItem);
            menuBar.add(editMenu);
        setJMenuBar(menuBar);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        mFinishListener.onFinish();
    }

    @Override
    public void onMapChanged(Map map) {
        mImportDeliveriesButton.setEnabled(true);
        mImportDeliveriesMenuItem.setEnabled(true);
        // TODO: repaint map view
    }

    @Override
    public void onRoadMapChanged(RoadMap roadMap) {
        // TODO: repaint mapview (w/ roadmap drawed upon it) & RM list view
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        mDeliveriesListView.onTomorrowDeliveryChanged(tomorrowDeliveries);
        // TODO: repaint mapview (w/ TDs drawed upon it)
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
        if(e.getSource().equals(mImportMapButton) || e.getSource().equals(mImportMapMenuItem)) {
            mMainToolbarListener.onImportMapAction();
        } else if(e.getSource().equals(mImportDeliveriesButton) || e.getSource().equals(mImportDeliveriesMenuItem)) {
            mMainToolbarListener.onImportMapAction();
        } else if(e.getSource().equals(mAddDeliveryButton) || e.getSource().equals(mAddDeliveryMenuItem)) {
            mDeliveriesToolbarListener.onAddDeliveryAction();
        } else if(e.getSource().equals(mDeleteDeliveryButton) || e.getSource().equals(mDeleteDeliveryMenuItem)) {
            mDeliveriesToolbarListener.onRemoveDeliveryAction();
        } else if(e.getSource().equals(mExportRoadMapButton) || e.getSource().equals(mExportRoadMapMenuItem)) {
            mRoadMapToolbarListener.onExportRoadMapAction();
        } else if(e.getSource().equals(mUndoMenuItem)) {
            mMainToolbarListener.onUndoAction();
        } else if(e.getSource().equals(mRedoMenuItem)) {
            mMainToolbarListener.onRedoAction();
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(mTabbedPane.getSelectedIndex() == 0) {
            mTabSelectionListener.onDeliveriesTabSelected();
        } else {
            mTabSelectionListener.onRoadMapTabSelected();
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
