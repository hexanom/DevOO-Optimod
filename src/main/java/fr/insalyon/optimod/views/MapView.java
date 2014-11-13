package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.listeners.MapPositionMatcher;
import fr.insalyon.optimod.controllers.listeners.data.MapChangeListener;
import fr.insalyon.optimod.controllers.listeners.data.RoadMapListener;
import fr.insalyon.optimod.controllers.listeners.data.TomorrowDeliveriesListener;
import fr.insalyon.optimod.controllers.listeners.intents.SelectionIntentListener;
import fr.insalyon.optimod.models.*;
import fr.insalyon.optimod.views.listeners.action.MapClickListener;
import fr.insalyon.optimod.views.listeners.action.MapListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * A Canvas showing the map and other stuff
 */
public class MapView extends JPanel implements MapChangeListener, MapPositionMatcher, SelectionIntentListener, RoadMapListener, TomorrowDeliveriesListener, ComponentListener, MouseListener, MapListener {
    private final MapClickListener mMapClickListener;
    private Map mMap;
    private RoadMap mRoadMap;
    private TomorrowDeliveries mTomorrowDeliveries;
    private Location mSelectedLocation;

    private java.util.Map<Location, LocationView> mLocationViews;
    private java.util.Map<Section, SectionView> mSectionViews;

    private boolean mShowSectionNames = true;
    private boolean mShowLocationNames = true;

    private static final int MARGIN = 20;

    private static final long locationsColorsSeed = 123456789l;
    private HashMap<TimeWindow, Color> mTimeWindowColors;

    public MapView(MapClickListener mapClickListener) {
        mMapClickListener = mapClickListener;
        setBackground(Color.WHITE);
        addComponentListener(this);
        addMouseListener(this);
    }

    @Override
    public void onMapChanged(Map map) {
        mMap = map;
        mSelectedLocation = null;
        mTomorrowDeliveries = null;
        mRoadMap = null;
        mLocationViews = new HashMap<>(mMap.getLocations().size());
        mSectionViews = new HashMap<>(mMap.getLocations().size());

        drawLocations();
        drawSections();

        repaint();
    }

    /**
     * Draw map locations on the view
     */
    private void drawLocations() {
        List<Location> locations = mMap.getLocations();
        Dimension size = getSize();
        Rectangle bounds = getBounds(locations);

        // Rescale to fit on the map
        double scaleX = (size.getWidth() - 2d * MARGIN) / bounds.getWidth();
        double scaleY = (size.getHeight() - 2d * MARGIN) / bounds.getHeight();

        for(Location loc : locations) {
            int x = (int) ((loc.getX() - bounds.getMinX())  * scaleX + MARGIN);
            int y = (int) ((loc.getY() - bounds.getMinY()) * scaleY + MARGIN);
            LocationView locationView = new LocationView(loc.getAddress(), x, y);
            locationView.toggleLabelDisplay(mShowLocationNames);
            mLocationViews.put(loc, locationView);
        }
    }

    /**
     * Draw map sections on the view
     */
    private void drawSections() {
        List<Location> locations = mMap.getLocations();
        for(Location origin : locations) {
            for(Section section : origin.getOuts()) {

                LocationView originView = mLocationViews.get(origin);
                LocationView destView = mLocationViews.get(section.getDestination());

                SectionView sectionView = new SectionView(originView.getCenter(), destView.getCenter(), section.getStreetName());
                sectionView.toggleLabelDisplay(mShowSectionNames);
                mSectionViews.put(section, sectionView);
            }
        }
    }

    /**
     * Calcule the coords bounds (min/max X Y) of a list of locations.
     * @param locations
     * @return
     */
    private Rectangle getBounds(List<Location> locations) {
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for(Location loc : locations) {
            minX = Math.min(minX, loc.getX());
            maxX = Math.max(maxX, loc.getX());
            minY = Math.min(minY, loc.getY());
            maxY = Math.max(maxY, loc.getY());
        }

        return new Rectangle(minX, minY, maxX - minX, maxY - minY);
    }

    @Override
    public Location matchLocation(int x, int y) {

        if(mLocationViews == null) {
            return null;
        }

        for(java.util.Map.Entry<Location, LocationView> e : mLocationViews.entrySet()) {
            if(e.getValue().contains(x, y)) {
                return e.getKey();
            }
        }
        return null;
    }

    @Override
    public void onRoadMapChanged(RoadMap roadMap) {
        mRoadMap = roadMap;

        if(mRoadMap != null && mSectionViews != null) {
            //reset colors
            for(SectionView sectionView : mSectionViews.values()) {
                sectionView.unused();
            }

            for(Path path : roadMap.getPaths()) {
                for (Section section : path.getOrderedSections()) {
                    SectionView sectionView = mSectionViews.get(section);
                    sectionView.used();
                }

            }

            repaint();
        }
    }

    /**
     * Updates a location view color corresponding to the state of its associated location.
     * Valid states : warehouse, delivery, selected location, simple location.
     * @param location Can be null, nothing will be done then
     */
    private void updateLocationColor(Location location) {

        if(location == null) {
            return;
        }

        Color col;
        if(location == mSelectedLocation) {
            col = LocationView.SELECTED_COLOR;
        }
        else if(mTomorrowDeliveries != null && location == mTomorrowDeliveries.getWarehouse()) {
            col = LocationView.WAREHOUSE_COLOR;
        }
        else if(location.getDelivery() != null) {
            col = mTimeWindowColors.get(location.getDelivery().getTimeWindow());
        }
        else {
            col = LocationView.DEFAULT_COLOR;
        }

        LocationView locationView = mLocationViews.get(location);
        locationView.setColor(col);
    }

    @Override
    public void onSelectIntentOnLocation(Location location) {

        Location oldLocation = mSelectedLocation;
        mSelectedLocation = location;

        updateLocationColor(oldLocation);
        updateLocationColor(location);

        repaint();
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        mTomorrowDeliveries = tomorrowDeliveries;
        mRoadMap = null;

        mTimeWindowColors = new HashMap<>(tomorrowDeliveries.getTimeWindows().size());
        Random rand = new Random(locationsColorsSeed);

        for(TimeWindow tw : tomorrowDeliveries.getTimeWindows()) {
            mTimeWindowColors.put(tw, new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256)));
        }

        // Set colors
        for(Location loc : mLocationViews.keySet()) {
            updateLocationColor(loc);
        }

        repaint();
    }

    @Override
    public void toggleSectionNames(boolean enabled) {

        mShowSectionNames = enabled;

        if(mSectionViews == null) {
            return;
        }

        for (SectionView sectionView : mSectionViews.values()) {
            sectionView.toggleLabelDisplay(enabled);
        }
        repaint();
    }

    @Override
    public void toggleLocationNames(boolean enabled) {

        mShowLocationNames = enabled;

        if(mLocationViews == null) {
            return;
        }

        for (LocationView locationView : mLocationViews.values()) {
            locationView.toggleLabelDisplay(enabled);
        }
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if(mMap == null) {
            return;
        }

        for(SectionView sectionView : mSectionViews.values()) {
            sectionView.paint(g);
        }

        for(LocationView locationView : mLocationViews.values()) {
            locationView.paint(g);
        }
    }

    @Override
    public void componentResized(ComponentEvent e) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mMapClickListener.onMapClick(e.getX(), e.getY());
    }

    // Note: we don't want to do anything with those but we have to implement them
    @Override
    public void componentMoved(ComponentEvent e) {}
    @Override
    public void componentShown(ComponentEvent e) {}
    @Override
    public void componentHidden(ComponentEvent e) {}
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
