package fr.insalyon.optimod.views;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Section;

import java.awt.*;

/**
 * Created by edouard on 10/11/14.
 */
public class SectionView {

    private static final int DEFAULT_WIDTH = 3;
    private static final int DEFAULT_BORDER_WIDTH = 2;
    public static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color DEFAULT_BORDER_COLOR = Color.BLACK;
    private static final Color DEFAULT_TEXT_COLOR = Color.BLACK;

    private Color mColor;
    private Color mBorderColor;
    private Color mTextColor;
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
        mTextColor = DEFAULT_TEXT_COLOR;
        mX1 = origin.x;
        mY1 = origin.y;
        mX2 = dest.x;
        mY2 = dest.y;
        mLabel = label;
    }

    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color savedColor = g.getColor();
        Stroke savedStroke = g2d.getStroke();

        if(mX1 == 384 && mY1 == 71 && mX2 == 467 && mY2 == 100) {
            System.out.println("I am " + mColor);
        }

        // Border
        g.setColor(mBorderColor);
        g2d.setStroke(new BasicStroke(mWidth + mBorderWidth));
        g.drawLine(mX1, mY1, mX2, mY2);

        // Interior
        g.setColor(mColor);
        g2d.setStroke(new BasicStroke(mWidth));
        g.drawLine(mX1, mY1, mX2, mY2);

        // Draw text
        Point center = getCenter();
        g.setColor(mTextColor);
        g.drawString(mLabel, center.x, center.y);

        g.setColor(savedColor);
        g2d.setStroke(savedStroke);
    }

    public Point getCenter() {
        return new Point((int) (((float)mX1 + mX2) / 2f), (int) (((float)mY1 + mY2) / 2f));
    }

    public void setColor(Color color) {
        this.mColor = color;
    }
}
