package fr.insalyon.optimod.factories;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.models.factories.XMLTomorrowDeliveriesFactory;
import junit.framework.TestCase;

public class XMLTomorrowDeliveriesFactoryTest extends TestCase {

	private XMLTomorrowDeliveriesFactory mTomorrowDeliveriesFactory;
	@Override
	public void setUp() throws Exception {
		super.setUp();
		String filename = "C:/Users/Modou/Desktop/4IF/Devoo/res_drive/Sujet-2014-10-30/Sujet/";
		filename += "livraison10x10-1.xml";
		mTomorrowDeliveriesFactory = new XMLTomorrowDeliveriesFactory(filename);

	}

	public void test_create() throws Exception {

		TomorrowDeliveries tomorrowDeliveries = mTomorrowDeliveriesFactory.create();
		assertTrue(tomorrowDeliveries.getDeliveries().size() == 8);
		Delivery deliv = tomorrowDeliveries.getDeliveries().get(0);
		assertEquals(deliv.getAddress(), "13");
	}

}
