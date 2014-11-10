package fr.insalyon.optimod.factories;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.Section;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class XMLMapFactoryTest {

    private XMLMapFactory mMapFactory;

    private static final String xmlFile = "plan10x10.xml";

    @Before
    public void setUp() throws Exception {
        URI uri = getClass().getClassLoader().getResource(xmlFile).toURI();
        mMapFactory = new XMLMapFactory(uri);
    }

    @Test
    public void create() throws Exception {

        Map map = mMapFactory.create();

        assertTrue(map.getLocations().size() == 100);
        Location location = map.getLocations().get(0);
        assertEquals(location.getMap(), map);
        assertEquals(location.getX(), 63);
        assertEquals(location.getY(), 100);
        assertTrue(location.getOuts().size() == 2);

        Section section = location.getOuts().get(0);
        assertEquals(section.getStreetName(), "v0");
        assertEquals(section.getSpeed(), 3.900000, 0.1);
        assertEquals(section.getLength(), 602.100000, 0.1);
        assertEquals(section.getDestination().getAddress(), "1");

        Location location2  = map.getLocations().get(1);
        assertEquals(location2.getX(), 88);
        assertEquals(location2.getY(), 171);
        assertTrue(location2.getOuts().size() == 3);
    }

}
