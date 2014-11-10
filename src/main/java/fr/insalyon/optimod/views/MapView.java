package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.listeners.MapPositionMatcher;
import fr.insalyon.optimod.controllers.listeners.data.MapChangeListener;
import fr.insalyon.optimod.controllers.listeners.data.RoadMapListener;
import fr.insalyon.optimod.controllers.listeners.data.TomorrowDeliveriesListener;
import fr.insalyon.optimod.controllers.listeners.intents.SelectionIntentListener;
import fr.insalyon.optimod.models.*;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.views.listeners.action.MapClickListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

/**
 * A Canvas showing the map and other stuff
 */
public class MapView extends JPanel implements MapChangeListener, MapPositionMatcher, SelectionIntentListener, RoadMapListener, TomorrowDeliveriesListener, ComponentListener, MouseListener {
    private final MapClickListener mMapClickListener;
    private Map mMap;
    private RoadMap mRoadMap;
    private TomorrowDeliveries mTomorrowDeliveries;
    private Location mSelectedLocation;

    private java.util.Map<Location, LocationView> mLocationViews;
    private java.util.Map<Section, SectionView> mSectionViews;

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

        List<Location> locations = mMap.getLocations();
        for(Location loc : locations) {
            LocationView locationView = new LocationView(loc);
            mLocationViews.put(loc, locationView);
        }

        // We need this in 2 loops, dont "optimize" !
        for(Location origin : locations) {
            for(Section section : origin.getOuts()) {

                LocationView originView = mLocationViews.get(origin);
                LocationView destView = mLocationViews.get(section.getDestination());

                SectionView sectionView = new SectionView(originView.getCenter(), destView.getCenter(), section.getStreetName());
                mSectionViews.put(section, sectionView);
            }
        }

        repaint();
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
        repaint();
    }

    @Override
    public void onSelectIntentOnLocation(Location location) {
        mSelectedLocation = location;
        repaint();
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        mTomorrowDeliveries = tomorrowDeliveries;
        mRoadMap = null;

        // Reset colors
        for(LocationView loc : mLocationViews.values()) {
            loc.setColor(LocationView.LOCATION_COLOR);
        }

        for(Delivery delivery : tomorrowDeliveries.getDeliveries()) {
            Location deliveryLocation = mMap.getLocationByAddress(delivery.getAddress());
            mLocationViews.get(deliveryLocation).setColor(LocationView.DELIVERY_COLOR);
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
        // TODO: Highlight the deliveries concerned
        // TODO: Draw labels
        // TODO: Trace the path between the nodes
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
