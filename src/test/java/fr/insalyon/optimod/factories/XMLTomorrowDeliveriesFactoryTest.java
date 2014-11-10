package fr.insalyon.optimod.factories;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import fr.insalyon.optimod.models.factories.XMLTomorrowDeliveriesFactory;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class XMLTomorrowDeliveriesFactoryTest {

    private XMLTomorrowDeliveriesFactory mTomorrowDeliveriesFactory;

    private static final String xmlDeliveries = "livraison10x10-1.xml";

    private static final String xmlMap = "plan10x10.xml";

    @Before
    public void setUp() throws Exception {
        URI uriDeliveries = getClass().getClassLoader().getResource(xmlDeliveries).toURI();
        URI uriMap = getClass().getClassLoader().getResource(xmlMap).toURI();
        Map map = new XMLMapFactory(uriMap).create();
        mTomorrowDeliveriesFactory = new XMLTomorrowDeliveriesFactory(uriDeliveries, map);

    }

    @Test
    public void create() throws Exception {

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
