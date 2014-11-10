package fr.insalyon.optimod.views;

import fr.insalyon.optimod.models.Location;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Point2D;

/**
 * Created by edouard on 10/11/14.
 */
public class LocationView {

    public static final Color LOCATION_COLOR = Color.BLUE;
    public static final Color DELIVERY_COLOR = Color.RED;

    private static final int DEFAULT_RADIUS = 15;
    private static final int DEFAULT_BORDER_WIDTH = 5;
    private static final Color DEFAULT_COLOR = LOCATION_COLOR;
    private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

    private Color mColor;
    private Color mBorderColor;
    private Color mTextColor;
    private int mRadius;
    private int mBorderWidth;

    private String mLabel;
    private int mX;
    private int mY;

    public LocationView(Location location) {
        mColor = DEFAULT_COLOR;
        mRadius = DEFAULT_RADIUS;
        mBorderColor = DEFAULT_BORDER_COLOR;
        mBorderWidth = DEFAULT_BORDER_WIDTH;
        mTextColor = DEFAULT_TEXT_COLOR;

        mLabel = location.getAddress();
        mX = location.getX();
        mY = location.getY();
    }

    public void paint(Graphics g) {
        Color savedColor = g.getColor();

        // Draw border
        g.setColor(mBorderColor);
        int englobingOvalRadius = mRadius + mBorderWidth;
        int mXborder = mX - mBorderWidth / 2;
        int mYborder = mY - mBorderWidth / 2;
        g.drawOval(mXborder, mYborder, englobingOvalRadius, englobingOvalRadius);
        g.fillOval(mXborder, mYborder, englobingOvalRadius, englobingOvalRadius);

        // Draw the pill
        g.setColor(mColor);
        g.drawOval(mX, mY, mRadius, mRadius);
        g.fillOval(mX, mY, mRadius, mRadius);

        // Draw text
        Point center = getCenter();
        g.setColor(mTextColor);
        g.drawString(mLabel, center.x, center.y - mRadius);

        g.setColor(savedColor);
    }

    public boolean contains(int x, int y) {
        Point center = getCenter();
        return Math.pow((x - center.x), 2) + Math.pow((y - center.y), 2) <= Math.pow(mRadius, 2);
    }

    public Point getCenter() {
        int halfRadius = (int) (((float) mRadius) / 2f);
        return new Point(mX + halfRadius, mY + halfRadius);
    }

    public void setColor(Color mColor) {
        this.mColor = mColor;
    }
}
