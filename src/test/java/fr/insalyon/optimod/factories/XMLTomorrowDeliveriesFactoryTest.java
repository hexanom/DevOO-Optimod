package fr.insalyon.optimod.factories;

import java.text.SimpleDateFormat;

import junit.framework.TestCase;
import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.models.factories.XMLTomorrowDeliveriesFactory;

public class XMLTomorrowDeliveriesFactoryTest extends TestCase {

	private XMLTomorrowDeliveriesFactory mTomorrowDeliveriesFactory;
	@Override
	public void setUp() throws Exception {
		super.setUp();
		String filename = "resources/tests/livraison10x10-1.xml";
		mTomorrowDeliveriesFactory = new XMLTomorrowDeliveriesFactory(filename);

	}

	public void test_create() throws Exception {

		//assertTrue(mTomorrowDeliveriesFactory.create() == null); // for incorrect xml file
		TomorrowDeliveries tomorrowDeliveries = mTomorrowDeliveriesFactory.create();
		assertTrue(tomorrowDeliveries.getDeliveries().size() == 8);
		Delivery deliv = tomorrowDeliveries.getDeliveries().get(0);
		assertEquals(deliv.getAddress(), "13");
		assertTrue(deliv.getTimeWindow().getDeliveries().size() == 8);
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
		assertEquals(df.format(deliv.getTimeWindow().getStart()), "08:00:00");
		assertEquals(df.format(deliv.getTimeWindow().getEnd()), "12:00:00");
	}
	

}
