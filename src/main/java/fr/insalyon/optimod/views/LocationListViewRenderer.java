package fr.insalyon.optimod.views;

import fr.insalyon.optimod.models.Location;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

/**
 * A JList Renderer for locations
 */
public class LocationListViewRenderer extends JLabel implements ListCellRenderer<Location> {
    private static final Color HIGHLIGHT_COLOR = Color.BLUE;
    private static final Color DELIVERY_HIGHLIGHT_COLOR = Color.GREEN;
    private final ImageIcon mDeliveryIcon;
    private final ImageIcon mWaypointIcon;

    public LocationListViewRenderer() {
        setOpaque(true);
        setIconTextGap(12);
        mDeliveryIcon = createImageIcon("package.png", "Package");
        mWaypointIcon = createImageIcon("location.png", "Location");
    }

    @Override
    public Component getListCellRendererComponent(JList list, Location location, int index, boolean isSelected, boolean cellHasFocus) {
        if(location.getDelivery() == null) {
            setText("Waypoint at " + location.getAddress());
            setIcon(mWaypointIcon);
        } else {
            setText("Delivery at " + location.getAddress());
            setIcon(mDeliveryIcon);
        }
        if (isSelected) {
            if(location.getDelivery() == null) {
                setBackground(HIGHLIGHT_COLOR);
            } else {
                setBackground(DELIVERY_HIGHLIGHT_COLOR);
            }

            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }

    private ImageIcon createImageIcon(String path, String description) {
        URL imgURL = getClass().getClassLoader().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + imgURL);
            return null;
        }
    }
}
