package fr.insalyon.optimod.models;

import junit.framework.TestCase;

import java.util.Date;

public class TimeWindowTest extends TestCase {

    private TimeWindow mTimeWindow;
    private Date mStart;
    private Date mEnd;

    @Override
    public void setUp() throws Exception {

        mStart = new Date(0);
        mEnd = new Date(1);

        super.setUp();
        mTimeWindow = new TimeWindow(mStart, mEnd);
    }

    public void test_getRoadMap() throws Exception {
        RoadMap RM_test = new RoadMap();
        mTimeWindow.setRoadMap(RM_test);
        assertEquals( mTimeWindow.getRoadMap(),RM_test);
    }

    public void test_getDeliveries() throws Exception {
        Delivery Deliv_test_0 = new Delivery();
        Delivery Deliv_test_1 = new Delivery();
        mTimeWindow.addDelivery(Deliv_test_0);
        assertEquals( mTimeWindow.getDeliveries().size(),1);
        mTimeWindow.addDelivery(Deliv_test_1);
        assertEquals( mTimeWindow.getDeliveries().get(0),Deliv_test_0);
        assertEquals( mTimeWindow.getDeliveries().get(1),Deliv_test_1);
        mTimeWindow.deleteDelivery(Deliv_test_0);
        assertEquals( mTimeWindow.getDeliveries().get(0),Deliv_test_1);
        mTimeWindow.deleteDelivery(Deliv_test_1);
        assertNull(mTimeWindow.getDeliveries());
    }

    public void test_getStart() throws Exception {
        assertEquals( mTimeWindow.getStart(),mStart);
    }
    public void test_getEnd() throws Exception {
        assertEquals( mTimeWindow.getEnd(),mEnd);
    }
}
