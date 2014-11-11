package fr.insalyon.optimod.views;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Location;

import javax.swing.*;
import java.awt.*;

/**
 * A JList Renderer for locations
 */
public class LocationListViewRenderer extends JLabel implements ListCellRenderer<Location> {
    private static final Color HIGHLIGHT_COLOR = Color.BLUE;

    public LocationListViewRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Location location, int index, boolean isSelected, boolean cellHasFocus) {
        setText(location.getAddress());
        if (isSelected) {
            setBackground(HIGHLIGHT_COLOR);
            setForeground(Color.white);
        } else {
            setBackground(Color.white);
            setForeground(Color.black);
        }
        return this;
    }
}
