package fr.insalyon.optimod.models;

import junit.framework.TestCase;

public class PathTest extends TestCase {
    private Path mPath;
    private Section test_Section_1;
    private Section test_Section_2;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mPath = new Path();
    }

    public void test_getTimeWindow() throws Exception {
        TimeWindow tw = new TimeWindow();
        mPath.setTimeWindow(tw);
        assertEquals(mPath.getTimeWindow(), tw);
    }

    public void test_getOrderedSections() throws Exception {
        assertNull(mPath.getOrderedSections());
        test_Section_1 = new Section("BobStreet",5,5);
        test_Section_2 = new Section("BobRoad",5,5);
        mPath.appendSection(test_Section_1);
        mPath.appendSection(test_Section_2);
        assertEquals(mPath.getOrderedSections().size(),2);
    }

    public void test_getOrigin() throws Exception {
        assertNull(mPath.getOrigin());
        test_Section_1 = new Section("BobStreet",5,5);
        mPath.appendSection(test_Section_1);
        assertEquals(mPath.getOrigin(),test_Section_1);
        mPath.removeOrigin();
        assertNull(mPath.getOrigin());
    }

    public void test_getDestination() throws Exception {
        assertNull(mPath.getDestination());
        test_Section_1 = new Section("BobStreet",5,5);
        test_Section_2 = new Section("BobRoad",5,5);
        mPath.appendSection(test_Section_1);
        mPath.appendSection(test_Section_2);
        assertEquals(mPath.getDestination(),test_Section_2);
        mPath.removeDestination();
        assertNull(mPath.getDestination());
    }



}
