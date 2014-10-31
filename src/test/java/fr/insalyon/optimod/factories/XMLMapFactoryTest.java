package fr.insalyon.optimod.factories;

import junit.framework.TestCase;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.Section;
import fr.insalyon.optimod.models.factories.XMLMapFactory;

public class XMLMapFactoryTest extends TestCase {

	private XMLMapFactory mMapFactory;
	 @Override
	public void setUp() throws Exception {
		super.setUp();
		String filename = "resources/tests/plan10x10.xml";
	    mMapFactory = new XMLMapFactory(filename);
		
	}
	 
	public void test_create() throws Exception {
		
		//assertTrue(mMapFactory.create() == null);
		Map map = mMapFactory.create();
		 
		assertTrue(map.getLocations().size() == 100);
		Location location = map.getLocations().get(0);
		assertEquals(location.getMap(), map);
		assertEquals(location.getX(), 63);
		assertEquals(location.getY(), 100);
		assertTrue(location.getOuts().size() == 2);
		
		Section section = location.getOuts().get(0);
		assertEquals(section.getStreetName(), "v0");
		assertEquals(section.getSpeed(), 3.900000);
		assertEquals(section.getLength(), 602.100000);
		assertEquals(section.getDestination().getAddress(), "1");
		
		Location location2  = map.getLocations().get(1);
		assertEquals(location2.getX(), 88);
		assertEquals(location2.getY(), 171);
		assertTrue(location2.getOuts().size() == 3);
	}

}
