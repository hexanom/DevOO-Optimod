package fr.insalyon.optimod.models;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
public class TimeWindowTest {

    @Test
    public void getRoadMap() throws Exception {
        Date mStart = new Date(0);
        Date mEnd = new Date(1);
        TimeWindow mTimeWindow = new TimeWindow(mStart, mEnd);
        RoadMap RM_test = new RoadMap();
        mTimeWindow.setRoadMap(RM_test);
        assertEquals( mTimeWindow.getRoadMap(),RM_test);
    }

    @Test
    public void getDeliveries() throws Exception {
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

    @Test
    public void getStart() throws Exception {
        Date mStart = new Date(0);
        Date mEnd = new Date(1);
        TimeWindow mTimeWindow = new TimeWindow(mStart, mEnd);
        assertEquals( mTimeWindow.getStart(),mStart);
    }

    @Test
    public void getEnd() throws Exception {
        Date mStart = new Date(0);
        Date mEnd = new Date(1);
        TimeWindow mTimeWindow = new TimeWindow(mStart, mEnd);
        assertEquals( mTimeWindow.getEnd(),mEnd);
    }
}
