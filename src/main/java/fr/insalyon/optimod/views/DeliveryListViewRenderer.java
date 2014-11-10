package fr.insalyon.optimod.views;

import fr.insalyon.optimod.models.Delivery;

import javax.swing.*;
import java.awt.*;

/**
 * A JList Renderer for deliveries
 */
public class DeliveryListViewRenderer extends JLabel implements ListCellRenderer<Delivery> {
    private static final Color HIGHLIGHT_COLOR = Color.BLUE;

    public DeliveryListViewRenderer() {
        setOpaque(true);
        setIconTextGap(12);
    }

    @Override
    public Component getListCellRendererComponent(JList list, Delivery delivery, int index, boolean isSelected, boolean cellHasFocus) {
        setText(delivery.getLocation().getAddress());
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
