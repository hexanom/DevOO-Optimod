package fr.insalyon.optimod.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MapTest {
    private Map mMap;

    @Before
    public void setUp() throws Exception {
        mMap = new Map();
    }

    @Test
    public void addLocation() throws Exception {
        Location location = new Location();
        mMap.addLocation(location);
        assertTrue(mMap.getLocations().size() > 0);
        assertEquals(location.getMap(), mMap);
        mMap.deleteLocation(location);
    }

    @Test
    public void deleteLocation() throws Exception {
        Location location = new Location();
        mMap.addLocation(location);
        mMap.deleteLocation(location);
        assertTrue(mMap.getLocations().size() == 0);
        assertNotSame(location.getMap(), mMap);
    }
}
