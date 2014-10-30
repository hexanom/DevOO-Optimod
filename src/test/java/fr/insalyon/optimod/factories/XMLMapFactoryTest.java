package fr.insalyon.optimod.factories;

import junit.framework.TestCase;

import org.junit.Test;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.factories.XMLMapFactory;

public class XMLMapFactoryTest extends TestCase {

	private XMLMapFactory mMapFactory;
	 @Override
	public void setUp() throws Exception {
		super.setUp();
		String filename = "C:/Users/Modou/Desktop/4IF/Devoo/res_drive/Sujet-2014-10-30/Sujet/";
    	filename += "plan10x10.xml";
	    mMapFactory = new XMLMapFactory(filename);
		
	}

	@Test
	public void test_create() throws Exception {
		
		Map map = mMapFactory.create();
		assertTrue(map.getLocations().size() == 100);
		Location location = map.getLocations().get(0);
		assertEquals(location.getMap(), map);
	}

}
