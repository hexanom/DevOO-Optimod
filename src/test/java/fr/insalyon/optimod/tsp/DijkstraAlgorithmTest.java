package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;

/**
 * Created by edouard on 04/11/14.
 */
public class DijkstraAlgorithmTest {

    private XMLMapFactory mMapFactory;
    @Before
    public void setUp() throws Exception {
        String filename = "resources/tests/plan10x10.xml";
        mMapFactory = new XMLMapFactory(filename);
    }

    @Test
    public void testWithXml() throws Exception {
        Map map = mMapFactory.create();
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        dijkstraAlgorithm.execute(map.getLocationByAddress("20"));
        LinkedList<Location> path = dijkstraAlgorithm.getPath(map.getLocationByAddress("80"));
    }

}
