package fr.insalyon.optimod.views;

import fr.insalyon.optimod.models.Location;

import javax.swing.*;
import java.awt.*;

/**
 * A JList Renderer for locations
 */
public class LocationListViewRenderer extends JLabel implements ListCellRenderer<Location> {
    private static final Color HIGHLIGHT_COLOR = Color.BLUE;
    private static final Color DELIVERY_HIGHLIGHT_COLOR = Color.GREEN;

    public LocationListViewRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Location location, int index, boolean isSelected, boolean cellHasFocus) {
        setText(location.getAddress());
        if (isSelected) {
            if(location.getDelivery() == null) {
                setBackground(HIGHLIGHT_COLOR);
            } else {
                setBackground(DELIVERY_HIGHLIGHT_COLOR);
            }

            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            if(location.getDelivery() == null) {
                setForeground(Color.black);
            } else {
                setForeground(DELIVERY_HIGHLIGHT_COLOR);
            }
        }
        return this;
    }
}
