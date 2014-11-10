package fr.insalyon.optimod.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CourierTest {

    private Courier mCourier;

    @Before
    public void setUp() throws Exception {
        mCourier = new Courier();
    }

    @Test
    public void addRoadMap() throws Exception {
        RoadMap rm = new RoadMap();
        mCourier.addRoadMap(rm);
        assertTrue(mCourier.getRoadMaps().size() > 0);
        assertEquals(rm.getCourier(), mCourier);
        mCourier.deleteRoadMap(rm);
    }

    @Test
    public void deleteRoadMap() throws Exception {
        RoadMap rm = new RoadMap();
        mCourier.addRoadMap(rm);
        mCourier.deleteRoadMap(rm);
        assertTrue(mCourier.getRoadMaps().size() == 0);
        assertNotSame(rm.getCourier(), mCourier);
    }

    @Test
    public void getTruck() throws Exception {
        assertTrue(mCourier.getTruck() != null);
    }
}
