package fr.insalyon.optimod.views;

import fr.insalyon.optimod.controllers.*;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.RoadMap;
import fr.insalyon.optimod.models.TomorrowDeliveries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * A Canvas showing the map and other stuff
 */
public class MapView extends JPanel implements MapChangeListener, MapPositionMatcher, SelectionIntentListener, RoadMapListener, TomorrowDeliveriesListener, ComponentListener, MouseListener {
    private final MapClickListener mMapClickListener;
    private Map mMap;
    private RoadMap mRoadMap;
    private TomorrowDeliveries mTomorrowDeliveries;
    private Location mSelectedLocation;

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
        invalidate();
    }

    @Override
    public Location matchLocation(int x, int y) {
        // TODO: go through all the subviews and call matchLocation on it, the first one to respond not null wins
        return null;
    }

    @Override
    public void onRoadMapChanged(RoadMap roadMap) {
        mRoadMap = roadMap;
        invalidate();
    }

    @Override
    public void onSelectIntentOnLocation(Location location) {
        mSelectedLocation = location;
        invalidate();
    }

    @Override
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries) {
        mTomorrowDeliveries = tomorrowDeliveries;
        mRoadMap = null;
        invalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension dim = getBounds().getSize();

        if(mMap == null) {
            return;
        }
        // TODO: Draw the map by calling each subview
        // NOTE: Special mention to the selected element
        if(mTomorrowDeliveries == null) {
            return;
        }
        // TODO: Highlight the deliveries concerned
        // NOTE: could be done at the same time as drawing the map before
        if(mRoadMap == null) {
            return;
        }
        // TODO: Trace the path between the nodes
        // NOTE: could be done at the same time as drawing the map before
    }

    @Override
    public void componentResized(ComponentEvent e) {
        invalidate();
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
