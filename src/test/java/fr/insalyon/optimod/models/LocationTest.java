package fr.insalyon.optimod.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LocationTest {
    private Map mMap;
    private Location mLocation;

    @Before
    public void setUp() throws Exception {
        mMap = new Map();
        mLocation = new Location("TestLocation", 1, 2);
        mMap.addLocation(mLocation);
    }

    @Test
    public void defaultConstructor() throws Exception {
        assertNotNull(mLocation.getAddress());
        assertNotNull(mLocation.getX());
        assertNotNull(mLocation.getY());
    }

    @Test
    public void getAddress() throws Exception {
        assertEquals("TestLocation", mLocation.getAddress());
    }

    @Test
    public void getX() throws Exception {
        assertEquals(1, mLocation.getX());
    }

    @Test
    public void getY() throws Exception {
        assertEquals(2, mLocation.getY());
    }

    @Test
    public void connectTo() throws Exception {
        Location otherLocation = new Location();
        assertNull(mLocation.connectTo(otherLocation));
        assertTrue(mLocation.getOuts().size() == 0);
        assertTrue(mLocation.getIns().size() == 0);
        mMap.addLocation(otherLocation);
        Section section = mLocation.connectTo(otherLocation, "TestStreet", 10, 100);
        assertNotNull(section);
        assertEquals("TestStreet", section.getStreetName());
        assertEquals(10.0, section.getSpeed(), 0.1);
        assertEquals(100.0, section.getLength(), 0.1);
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

    @Test
    public void getConnectedSections() throws Exception {
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

    @Test
    public void getMap() throws Exception {
        assertEquals(mMap, mLocation.getMap());
    }
}
