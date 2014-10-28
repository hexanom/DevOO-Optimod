package fr.insalyon.optimod.models;

import junit.framework.TestCase;

import java.util.Date;
import java.util.TreeSet;

public class RoadMapTest extends TestCase {


    @Override
    public void setUp() throws Exception {
        super.setUp();
        ;

    }

    public void test_getTomorrowDeliveries() throws Exception {
        RoadMap mRoadMap = new RoadMap();
        TomorrowDeliveries td = new TomorrowDeliveries();
        mRoadMap.setTomorrowDeliveries(td);
        assertEquals(mRoadMap.getTomorrowDeliveries(), td);
    }

    public void test_getCourier() throws Exception {
        RoadMap mRoadMap = new RoadMap();
        Courier courier = new Courier();
        mRoadMap.setCourier(courier);
        assertEquals(mRoadMap.getCourier(),courier);

    }

    public void test_getTimeWindow() throws Exception {
        RoadMap mRoadMap = new RoadMap();
        Date mStart_1 = new Date(0);
        Date mEnd_1 = new Date(1);
        Date mStart_2 = new Date(2);
        Date mEnd_2 = new Date(3);
        TimeWindow tw_1 = new TimeWindow(mStart_1,mEnd_1 );
        TimeWindow tw_2 = new TimeWindow(mStart_2,mEnd_2);
        mRoadMap.addTimeWindow(tw_1);
        assertEquals(mRoadMap.getTimeWindows().first(),tw_1);
        mRoadMap.addTimeWindow(tw_2);
        assertEquals(mRoadMap.getTimeWindows().size(),2);
        mRoadMap.deleteTimeWindow(tw_1);
        assertEquals(mRoadMap.getTimeWindows().first(),tw_2);
        assertEquals(mRoadMap.getTimeWindows().size(),1);
        mRoadMap.deleteTimeWindow(tw_2);
        assertEquals(mRoadMap.getTimeWindows().size(),0);

    }



}
