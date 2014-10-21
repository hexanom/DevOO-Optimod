package fr.insalyon.optimod.models;

import junit.framework.TestCase;

public class LocationTest extends TestCase {
    private Map mMap;
    private Location mLocation;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mMap = new Map();
        mLocation = new Location("TestLocation", 1, 2);
        mMap.addLocation(mLocation);
    }

    public void testDefaultConstructor() throws Exception {
        assertNotNull(mLocation.getAddress());
        assertNotNull(mLocation.getX());
        assertNotNull(mLocation.getY());
    }

    public void test_getAddress() throws Exception {
        assertEquals("TestLocation", mLocation.getAddress());
    }

    public void test_getX() throws Exception {
        assertEquals(1, mLocation.getX());
    }

    public void test_getY() throws Exception {
        assertEquals(2, mLocation.getY());
    }

    public void test_connectTo() throws Exception {
        Location otherLocation = new Location();
        assertNull(mLocation.connectTo(otherLocation));
        assertTrue(mLocation.getOuts().size() == 0);
        assertTrue(mLocation.getIns().size() == 0);
        mMap.addLocation(otherLocation);
        Section section = mLocation.connectTo(otherLocation);
        assertNotNull(section);
        assertTrue(mLocation.getOuts().size() > 0);
        assertEquals(section.getOrigin(), mLocation);
        assertEquals(section.getDestination(), otherLocation);
        mLocation.deleteConnectedSection(section);
        section = otherLocation.connectTo(mLocation);
        assertNotNull(section);
        assertTrue(mLocation.getIns().size() > 0);
        assertEquals(section.getOrigin(), otherLocation);
        assertEquals(section.getDestination(), mLocation);
        mLocation.deleteConnectedSection(section);
    }

    public void test_getConnectedSections() throws Exception {
        Location otherLocation = new Location();
        mMap.addLocation(otherLocation);
        assertTrue(mLocation.getConnectedSections().size() == 0);
        Section s1 = mLocation.connectTo(otherLocation);
        assertTrue(mLocation.getConnectedSections().size() == 1);
        Section s2 = otherLocation.connectTo(mLocation);
        assertTrue(mLocation.getConnectedSections().size() == 2);
        mLocation.deleteConnectedSection(s1);
        assertTrue(mLocation.getConnectedSections().size() == 1);
        mLocation.deleteConnectedSection(s2);
        assertTrue(mLocation.getConnectedSections().size() == 0);
    }

    public void test_getMap() throws Exception {
        assertEquals(mMap, mLocation.getMap());
    }
}
