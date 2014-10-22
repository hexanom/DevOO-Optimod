package fr.insalyon.optimod.models;

import junit.framework.TestCase;

public class MapTest extends TestCase {
    private Map mMap;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mMap = new Map();
    }

    public void test_addLocation() throws Exception {
        Location location = new Location();
        mMap.addLocation(location);
        assertTrue(mMap.getLocations().size() > 0);
        assertEquals(location.getMap(), mMap);
        mMap.deleteLocation(location);
    }

    public void test_deleteLocation() throws Exception {
        Location location = new Location();
        mMap.addLocation(location);
        mMap.deleteLocation(location);
        assertTrue(mMap.getLocations().size() == 0);
        assertNotSame(location.getMap(), mMap);
    }
}
