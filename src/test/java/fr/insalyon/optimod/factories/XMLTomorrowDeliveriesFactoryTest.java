package fr.insalyon.optimod.factories;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import fr.insalyon.optimod.models.factories.XMLTomorrowDeliveriesFactory;
import junit.framework.TestCase;

import java.net.URI;
import java.text.SimpleDateFormat;

public class XMLTomorrowDeliveriesFactoryTest extends TestCase {

    private XMLTomorrowDeliveriesFactory mTomorrowDeliveriesFactory;
    @Override
    public void setUp() throws Exception {
        super.setUp();
        String deliveriesFilename = "livraison10x10-1.xml";
        URI uriDeliveries = getClass().getClassLoader().getResource(deliveriesFilename).toURI();
        String mapFilename = "plan10x10.xml";
        URI uriMap = getClass().getClassLoader().getResource(mapFilename).toURI();
        mTomorrowDeliveriesFactory = new XMLTomorrowDeliveriesFactory(uriDeliveries,
                new XMLMapFactory(uriMap).create());

    }

    public void test_create() throws Exception {

        //assertTrue(mTomorrowDeliveriesFactory.create() == null); // for incorrect xml file
        TomorrowDeliveries tomorrowDeliveries = mTomorrowDeliveriesFactory.create();
        assertTrue(tomorrowDeliveries.getDeliveries().size() == 8);
        Delivery deliv = tomorrowDeliveries.getDeliveries().get(0);
        assertEquals(deliv.getLocation().getAddress(), "13");
        assertTrue(deliv.getTimeWindow().getDeliveries().size() == 8);

        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        assertEquals(df.format(deliv.getTimeWindow().getStart()), "08:00:00");
        assertEquals(df.format(deliv.getTimeWindow().getEnd()), "12:00:00");
    }


}
