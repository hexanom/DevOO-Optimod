package fr.insalyon.optimod.views;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Section;

import java.awt.*;

/**
 * Created by edouard on 10/11/14.
 */
public class SectionView {

    public static final Color SECTION_COLOR = Color.WHITE;
    public static final Color PATH_COLOR = Color.RED;

    private static final int DEFAULT_WIDTH = 3;
    private static final int DEFAULT_BORDER_WIDTH = 1;
    private static final Color DEFAULT_COLOR = SECTION_COLOR;
    private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;

    private Color mColor;
    private Color mBorderColor;
    private int mWidth;
    private int mBorderWidth;

    private int mX1;
    private int mY1;
    private int mX2;
    private int mY2;
    private String mLabel;

    public SectionView(Point origin, Point dest, String label) {
        mColor = DEFAULT_COLOR;
        mBorderColor = DEFAULT_BORDER_COLOR;
        mWidth = DEFAULT_WIDTH;
        mBorderWidth = DEFAULT_BORDER_WIDTH;
        mX1 = (int) origin.getX();
        mY1 = (int) origin.getY();
        mX2 = (int) dest.getX();
        mY2 = (int) dest.getY();
        mLabel = label;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color savedColor = g.getColor();
        Stroke savedStroke = g2d.getStroke();

        // Border
        g.setColor(mBorderColor);
        g2d.setStroke(new BasicStroke(mWidth + mBorderWidth));
        g.drawLine(mX1, mY1, mX2, mY2);

        // Interior
        g.setColor(mColor);
        g2d.setStroke(new BasicStroke(mWidth));
        g.drawLine(mX1, mY1, mX2, mY2);

        g.setColor(savedColor);
        g2d.setStroke(savedStroke);
    }

    public void setColor(Color mColor) {
        this.mColor = mColor;
    }
}
