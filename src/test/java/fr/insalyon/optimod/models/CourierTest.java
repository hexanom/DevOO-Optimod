package fr.insalyon.optimod.models;

import junit.framework.TestCase;

public class CourierTest extends TestCase {
    private Courier mCourier;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mCourier = new Courier();
    }

    public void test_addRoadMap() throws Exception {
        RoadMap rm = new RoadMap();
        mCourier.addRoadMap(rm);
        assertTrue(mCourier.getRoadMaps().size() > 0);
        assertEquals(rm.getCourier(), mCourier);
        mCourier.deleteRoadMap(rm);
    }

    public void test_deleteRoadMap() throws Exception {
        RoadMap rm = new RoadMap();
        mCourier.addRoadMap(rm);
        mCourier.deleteRoadMap(rm);
        assertTrue(mCourier.getRoadMaps().size() == 0);
        assertTrue(rm.getCourier() != mCourier);
    }
}
