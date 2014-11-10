package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import fr.insalyon.optimod.models.factories.XMLTomorrowDeliveriesFactory;
import org.junit.Before;
import org.junit.Test;

import java.net.URI;
import java.net.URL;
import java.util.List;

/**
 * Created by edouard on 04/11/14.
 */
public class LocationsGraphTest {

    private XMLTomorrowDeliveriesFactory mDeliveriesFactory;

    @Before
    public void setUp() throws Exception {
        String mapFilename = "plan10x10.xml";
        URI mapURL = getClass().getClassLoader().getResource(mapFilename).toURI();
        XMLMapFactory mMapFactory = new XMLMapFactory(mapURL);
        Map map = mMapFactory.create();

        String deliveryFilename = "livraison10x10-1.xml";
        URI deliveryURL = getClass().getClassLoader().getResource(deliveryFilename).toURI();
        mDeliveriesFactory = new XMLTomorrowDeliveriesFactory(deliveryURL, map);
    }

  /*  @Test
    public void testWithXml() throws Exception {
        TomorrowDeliveries tomorrowDeliveries = mDeliveriesFactory.create();
        List<Delivery> deliveries = tomorrowDeliveries.getDeliveries();
        Location warehouse = tomorrowDeliveries.getWarehouse();
        LocationsGraph locationsGraph = new LocationsGraph(warehouse, deliveries);

    }*/

}
