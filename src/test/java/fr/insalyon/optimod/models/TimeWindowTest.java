package fr.insalyon.optimod.models;

import junit.framework.TestCase;

import java.util.Date;

public class TimeWindowTest extends TestCase {


    @Override
    public void setUp() throws Exception {
        super.setUp();

    }

    public void test_getRoadMap() throws Exception {
        Date mStart = new Date(0);
        Date mEnd = new Date(1);
        TimeWindow mTimeWindow = new TimeWindow(mStart, mEnd);
        RoadMap RM_test = new RoadMap();
        mTimeWindow.setRoadMap(RM_test);
        assertEquals( mTimeWindow.getRoadMap(),RM_test);
    }

    public void test_getDeliveries() throws Exception {
        Date mStart = new Date(0);
        Date mEnd = new Date(1);
        TimeWindow mTimeWindow = new TimeWindow(mStart, mEnd);
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
        assertEquals( mTimeWindow.getDeliveries().size(),0);
    }

    public void test_getStart() throws Exception {
        Date mStart = new Date(0);
        Date mEnd = new Date(1);
        TimeWindow mTimeWindow = new TimeWindow(mStart, mEnd);
        assertEquals( mTimeWindow.getStart(),mStart);
    }
    public void test_getEnd() throws Exception {
        Date mStart = new Date(0);
        Date mEnd = new Date(1);
        TimeWindow mTimeWindow = new TimeWindow(mStart, mEnd);
        assertEquals( mTimeWindow.getEnd(),mEnd);
    }
}
