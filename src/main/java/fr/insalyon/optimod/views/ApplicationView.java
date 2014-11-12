package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.ApplicationController;
import fr.insalyon.optimod.controllers.listeners.MapPositionMatcher;
import fr.insalyon.optimod.controllers.listeners.data.HistoryChangedListener;
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
import fr.insalyon.optimod.views.listeners.action.*;
import fr.insalyon.optimod.views.listeners.activity.FinishListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.Calendar;

/**
 * Represents the application as a main window
 */
public class ApplicationView extends JFrame implements WindowListener, MapChangeListener, MapPositionMatcher, RoadMapListener, TomorrowDeliveriesListener, SelectionIntentListener, ActionListener, ChangeListener, FileSelectionIntentListener, ShowErrorIntentListener, SelectionListener, HistoryChangedListener, MapListener {
    private final FinishListener mFinishListener;
    private final MapDisplayListener mMapDisplayListener;
    private final MainToolBarListener mMainToolbarListener;
    private final MapClickListener mMapClickListener;
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
    private JButton mAddBeforeButton;
    private JMenuItem mAddBeforeMenuItem;
    private JButton mDeleteDeliveryButton;
    private JMenuItem mDeleteDeliveryMenuItem;
    private JButton mExportRoadMapButton;
    private JMenuItem mExportRoadMapMenuItem;
    private DeliveriesListView mDeliveriesListView;
    private MapView mMapView;
    private JMenuItem mAddAfterMenuItem;
    private JButton mAddAfterButton;
    private RoadMapListView mRoadMapListView;
    private JLabel mAddressDetail;
    private JLabel mTimeRangeDetail;
    private JCheckBoxMenuItem mDisplayLocationNamesItem;
    private JCheckBoxMenuItem mDisplaySectionNamesItem;

    public ApplicationView(ApplicationController controller) {
        mFinishListener = controller;
        mMainToolbarListener = controller;
        mMapClickListener = controller;
        mRoadMapToolbarListener = controller;
        mSelectionListener = controller;
        mTabSelectionListener = controller;
        mMapDisplayListener = controller;
        initWindow();
        initChildren();
    }

    private void initChildren() {
        JComponent mainToolbar = new JPanel();
        mainToolbar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
            mImportMapButton = new JButton("Import Map");
            mImportMapButton.addActionListener(this);
            mainToolbar.add(mImportMapButton);

            mImportDeliveriesButton = new JButton("Import Deliveries");
            mImportDeliveriesButton.setEnabled(false);
            mImportDeliveriesButton.addActionListener(this);
            mainToolbar.add(mImportDeliveriesButton);


            mExportRoadMapButton = new JButton("Export");
            mExportRoadMapButton.setEnabled(false);
            mExportRoadMapButton.addActionListener(this);
            mainToolbar.add(mExportRoadMapButton);
        add(mainToolbar, BorderLayout.PAGE_START);

        mMapView = new MapView(mMapClickListener);
        add(mMapView, BorderLayout.CENTER);

        mTabbedPane = new JTabbedPane();
        mTabbedPane.setPreferredSize(new Dimension(300, 400));
        mTabbedPane.addChangeListener(this);
            JComponent deliveriesTab = new JPanel(new BorderLayout());
                JScrollPane tdSPane = new JScrollPane();
                    mDeliveriesListView = new DeliveriesListView(this);
                    tdSPane.getViewport().add(mDeliveriesListView);
                deliveriesTab.add(tdSPane, BorderLayout.CENTER);
            mTabbedPane.addTab("Deliveries", deliveriesTab);

            JComponent roadMapTab = new JPanel(new BorderLayout());
                JComponent roadMapToolbar = new JPanel(new FlowLayout());
                    mAddBeforeButton = new JButton("<+");
                    mAddBeforeButton.setEnabled(false);
                    mAddBeforeButton.addActionListener(this);
                    roadMapToolbar.add(mAddBeforeButton);

                    mAddAfterButton = new JButton("+>");
                    mAddAfterButton.setEnabled(false);
                    mAddAfterButton.addActionListener(this);
                    roadMapToolbar.add(mAddAfterButton);

                    mDeleteDeliveryButton = new JButton("-");
                    mDeleteDeliveryButton.setEnabled(false);
                    mDeleteDeliveryButton.addActionListener(this);
                    roadMapToolbar.add(mDeleteDeliveryButton);
                roadMapTab.add(roadMapToolbar, BorderLayout.PAGE_END);

                JScrollPane rmSPane = new JScrollPane();
                    mRoadMapListView = new RoadMapListView(this);
                    rmSPane.getViewport().add(mRoadMapListView);
                roadMapTab.add(rmSPane, BorderLayout.CENTER);
            mTabbedPane.addTab("Road Map", roadMapTab);
        add(mTabbedPane, BorderLayout.EAST);

        JPanel details = new JPanel();
            details.add(new JLabel("Address: "));

            mAddressDetail = new JLabel();
            details.add(mAddressDetail);

            details.add(new JLabel("Deliver between: "));

            mTimeRangeDetail = new JLabel();
            details.add(mTimeRangeDetail);
        add(details, BorderLayout.SOUTH);
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
                mImportDeliveriesMenuItem.addActionListener(this);
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

                mAddBeforeMenuItem = new JMenuItem("Add Delivery Before");
                mAddBeforeMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, keyMask));
                mAddBeforeMenuItem.setEnabled(false);
                mAddBeforeMenuItem.addActionListener(this);
                editMenu.add(mAddBeforeMenuItem);

                mAddAfterMenuItem = new JMenuItem("Add Delivery After");
                mAddAfterMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, keyMask  | InputEvent.SHIFT_DOWN_MASK));
                mAddAfterMenuItem.setEnabled(false);
                mAddAfterMenuItem.addActionListener(this);
                editMenu.add(mAddAfterMenuItem);

                mDeleteDeliveryMenuItem = new JMenuItem("Delete Delivery");
                mDeleteDeliveryMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_D, keyMask));
                mDeleteDeliveryMenuItem.setEnabled(false);
                mDeleteDeliveryMenuItem.addActionListener(this);
                editMenu.add(mDeleteDeliveryMenuItem);
            menuBar.add(editMenu);

            JMenu displayMenu = new JMenu("Display");
                mDisplaySectionNamesItem = new JCheckBoxMenuItem("Display roads names");
                mDisplaySectionNamesItem.addActionListener(this);
                mDisplaySectionNamesItem.setState(true);
                displayMenu.add(mDisplaySectionNamesItem);

                mDisplayLocationNamesItem = new JCheckBoxMenuItem("Display locations names");
                mDisplayLocationNamesItem.addActionListener(this);
                mDisplayLocationNamesItem.setState(true);
                displayMenu.add(mDisplayLocationNamesItem);
            menuBar.add(displayMenu);
        setJMenuBar(menuBar);
    }

    @Override
    public void windowClosed(WindowEvent e) {
        mFinishListener.onFinish();
    }

    @Override
    public void onMapChanged(Map map) {
        if(map != null) {
            mImportDeliveriesButton.setEnabled(true);
            mImportDeliveriesMenuItem.setEnabled(true);
        } else {
            mImportDeliveriesButton.setEnabled(false);
            mImportDeliveriesMenuItem.setEnabled(false);
        }
        mMapView.onMapChanged(map);
    }

    public void switchToDeliveriesTab() {
        mTabbedPane.setSelectedIndex(0);
    }

    @Override
    public void onRoadMapChanged(RoadMap roadMap) {
        if(roadMap != null && roadMap.getPaths().size() > 0) {
            mExportRoadMapButton.setEnabled(true);
            mExportRoadMapMenuItem.setEnabled(true);
        } else {
            mExportRoadMapButton.setEnabled(false);
            mExportRoadMapMenuItem.setEnabled(false);
        }
        mMapView.onRoadMapChanged(roadMap);
        mRoadMapListView.onRoadMapChanged(roadMap);
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        mMapView.onTomorrowDeliveryChanged(tomorrowDeliveries);
        mDeliveriesListView.onTomorrowDeliveryChanged(tomorrowDeliveries);
    }

    @Override
    public Location matchLocation(int x, int y) {
        return mMapView.matchLocation(x, y);
    }

    @Override
    public void onSelectIntentOnLocation(Location location) {
        if(location != null && location.getDelivery() != null) {
            mDeleteDeliveryButton.setEnabled(true);
            mDeleteDeliveryMenuItem.setEnabled(true);
            mAddBeforeButton.setEnabled(true);
            mAddBeforeMenuItem.setEnabled(true);
            mAddAfterButton.setEnabled(true);
            mAddAfterMenuItem.setEnabled(true);
        } else {
            mDeleteDeliveryButton.setEnabled(false);
            mDeleteDeliveryMenuItem.setEnabled(false);
            mAddBeforeButton.setEnabled(false);
            mAddBeforeMenuItem.setEnabled(false);
            mAddAfterButton.setEnabled(false);
            mAddAfterMenuItem.setEnabled(false);
        }
        mMapView.onSelectIntentOnLocation(location);
        mDeliveriesListView.onSelectIntentOnLocation(location);
        mRoadMapListView.onSelectIntentOnLocation(location);
        if(location != null) {
            mAddressDetail.setText(location.getAddress());
            if(location.getDelivery() != null) {
                Calendar start = Calendar.getInstance();
                start.setTime(location.getDelivery().getTimeWindow().getStart());
                Calendar end = Calendar.getInstance();
                end.setTime(location.getDelivery().getTimeWindow().getEnd());
                mTimeRangeDetail.setText(String.format(
                        "%d:%d and %d:%d",
                        start.get(Calendar.HOUR_OF_DAY),
                        start.get(Calendar.MINUTE),
                        end.get(Calendar.HOUR_OF_DAY),
                        end.get(Calendar.MINUTE)));
            } else {
                mTimeRangeDetail.setText("-");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(mImportMapButton) || e.getSource().equals(mImportMapMenuItem)) {
            mMainToolbarListener.onImportMapAction();
        } else if(e.getSource().equals(mImportDeliveriesButton) || e.getSource().equals(mImportDeliveriesMenuItem)) {
            mMainToolbarListener.onImportDeliveriesAction();
        } else if(e.getSource().equals(mAddBeforeButton) || e.getSource().equals(mAddBeforeMenuItem)) {
            mRoadMapToolbarListener.onAddBeforeAction();
        } else if(e.getSource().equals(mAddAfterButton) || e.getSource().equals(mAddAfterMenuItem)) {
            mRoadMapToolbarListener.onAddAfterAction();
        } else if(e.getSource().equals(mDeleteDeliveryButton) || e.getSource().equals(mDeleteDeliveryMenuItem)) {
            mRoadMapToolbarListener.onRemoveDeliveryAction();
        } else if(e.getSource().equals(mExportRoadMapButton) || e.getSource().equals(mExportRoadMapMenuItem)) {
            mRoadMapToolbarListener.onExportRoadMapAction();
        } else if(e.getSource().equals(mUndoMenuItem)) {
            mMainToolbarListener.onUndoAction();
        } else if(e.getSource().equals(mRedoMenuItem)) {
            mMainToolbarListener.onRedoAction();
        } else if(e.getSource().equals(mDisplayLocationNamesItem)) {
            mMapDisplayListener.toggleLocationNames(mDisplayLocationNamesItem.getState());
        } else if(e.getSource().equals(mDisplaySectionNamesItem)) {
            mMapDisplayListener.toggleSectionNames(mDisplaySectionNamesItem.getState());
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if(mTabbedPane.getSelectedIndex() == 0) {
            mExportRoadMapButton.setEnabled(false);
            mTabSelectionListener.onDeliveriesTabSelected();
        } else {
            mExportRoadMapButton.setEnabled(true);
            mTabSelectionListener.onRoadMapTabSelected();
        }
    }

    @Override
    public String onFileSelectionIntent(boolean save) {
        if(save) {
            FileDialog fd = new FileDialog(this, "Save a file", FileDialog.SAVE);
            fd.setVisible(true);
            if (fd.getFile() != null) {
                return fd.getDirectory() + fd.getFile();
            } else {
                return null;
            }
        }
        else {
            FileDialog fd = new FileDialog(this, "Open a file", FileDialog.LOAD);
            fd.setFile("*.xml");
            fd.setVisible(true);
            if (fd.getFile() != null) {
                return fd.getDirectory() + fd.getFile();
            } else {
                return null;
            }
        }
    }

    @Override
    public void onErrorIntent(String title, String content) {
        JOptionPane.showMessageDialog(this, content, title, JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void onSelectLocation(Location location) {
        mSelectionListener.onSelectLocation(location);
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


    @Override
    public void onHistoryChanged(boolean hasHistory, boolean hasFuture) {
        mUndoMenuItem.setEnabled(hasHistory);
        mUndoMenuItem.setEnabled(hasFuture);
    }

    @Override
    public void toggleSectionNames(boolean enabled) {
        mMapView.toggleSectionNames(enabled);
    }

    @Override
    public void toggleLocationNames(boolean enabled) {
        mMapView.toggleLocationNames(enabled);
    }
}
