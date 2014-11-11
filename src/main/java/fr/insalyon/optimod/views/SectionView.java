package fr.insalyon.optimod.views;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;

/**
 * Created by edouard on 10/11/14.
 */
public class SectionView {

    private static final double ARROW_PHI = Math.PI / 6d;
    private static final double ARROW_LENGTH = 10;

    private static final int CURVATURE = 20;
    private static final int DEFAULT_WIDTH = 3;
    private static final int DEFAULT_BORDER_WIDTH = 1;
    private static final Color DEFAULT_COLOR = Color.WHITE;
    private static final Color USED_COLOR = Color.red;
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

    /**
     * Paints the view
     * @param g
     */
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Color savedColor = g.getColor();
        Stroke savedStroke = g2d.getStroke();

        // Normalized normal of the section !
        double nX = - (mY2 - mY1);
        double nY = mX2 - mX1;
        double d = Math.sqrt((nX * nX) + (nY * nY));
        nX /= d;
        nY /= d;
        //

        Point center = getCenter();

        QuadCurve2D curve = new QuadCurve2D.Float();
        curve.setCurve(mX1, mY1, center.x + nX * CURVATURE, center.y + nY * CURVATURE, mX2, mY2);

        // Border
        g.setColor(mBorderColor);
        g2d.setStroke(new BasicStroke(mWidth + mBorderWidth));
        g2d.draw(curve);

        //Interior
        g.setColor(mColor);
        g2d.setStroke(new BasicStroke(mWidth));
        g2d.draw(curve);

        // Arrow head
        g2d.setStroke(new BasicStroke());
        g.setColor(Color.BLACK);
        Point bezierMiddle = getBezierMiddle(curve);
        drawArrow(g2d, Math.atan2(mY2 - mY1, mX2 - mX1), bezierMiddle.x, bezierMiddle.y);

        // Draw text
        g.setColor(mTextColor);
        //g.drawString(mLabel, center.x, center.y);

        g.setColor(savedColor);
        g2d.setStroke(savedStroke);
    }

    /**
     * Get the middle point coordinates of a curve
     * Found on http://stackoverflow.com/questions/14174252
     * @param curve
     * @return
     */
    private Point getBezierMiddle(QuadCurve2D curve) {
        double Dx = ( 0.5 * mX1 ) + (0.5 * curve.getCtrlX());
        double Dy = ( 0.5 * mY1 ) + (0.5 * curve.getCtrlY());

        double Ex = ( 0.5 * curve.getCtrlX() ) + (0.5 * mX2);
        double Ey = ( 0.5 * curve.getCtrlY() ) + (0.5 * mY2);

        double Px = ( 0.5 * Dx ) + (0.5 * Ex);
        double Py = ( 0.5 * Dy ) + (0.5 * Ey);

        return new Point((int)Px, (int)Py);
    }

    /**
     * Mark the section as used
     */
    public void used() {
        mColor = USED_COLOR;
    }

    /**
     * Mark the section as un-used
     */
    public void unused() {
        mColor = DEFAULT_COLOR;
    }

    /** Draws an arrow head
     * Found on http://www.coderanch.com/t/339505/GUI/java/drawing-arrows
     * @param g2
     * @param theta Rotation of the arrow
     * @param x0 Arrow tip coord x
     * @param y0 Arrow tip coord y
     */
    private void drawArrow(Graphics2D g2, double theta, double x0, double y0)
    {
        double x = x0 - ARROW_LENGTH * Math.cos(theta + ARROW_PHI);
        double y = y0 - ARROW_LENGTH * Math.sin(theta + ARROW_PHI);
        g2.draw(new Line2D.Double(x0, y0, x, y));
        x = x0 - ARROW_LENGTH * Math.cos(theta - ARROW_PHI);
        y = y0 - ARROW_LENGTH * Math.sin(theta - ARROW_PHI);
        g2.draw(new Line2D.Double(x0, y0, x, y));
    }

    /**
     * Get the center coordinates of the view
     * @return The point of the center of the view
     */
    public Point getCenter() {
        return new Point((int) (((float)mX1 + mX2) / 2f), (int) (((float)mY1 + mY2) / 2f));
    }

    public void setColor(Color color) {
        this.mColor = color;
    }
}
