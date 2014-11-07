package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.Delivery;
import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.TomorrowDeliveries;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import fr.insalyon.optimod.models.factories.XMLTomorrowDeliveriesFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * Created by edouard on 04/11/14.
 */
public class LocationsGraphTest {

    private XMLTomorrowDeliveriesFactory mDeliveriesFactory;

    @Before
    public void setUp() throws Exception {
        String mapFilename = "resources/tests/plan10x10.xml";
        XMLMapFactory mMapFactory = new XMLMapFactory(mapFilename);
        Map map = mMapFactory.create();

        String deliveryFilename = "resources/tests/livraison10x10-1.xml";
        mDeliveriesFactory = new XMLTomorrowDeliveriesFactory(deliveryFilename, map);
    }

  /*  @Test
    public void testWithXml() throws Exception {
        TomorrowDeliveries tomorrowDeliveries = mDeliveriesFactory.create();
        List<Delivery> deliveries = tomorrowDeliveries.getDeliveries();
        Location wareHouse = tomorrowDeliveries.getWareHouse();
        LocationsGraph locationsGraph = new LocationsGraph(wareHouse, deliveries);

    }*/

}
