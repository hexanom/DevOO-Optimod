package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by edouard on 04/11/14.
 */
public class DijkstraAlgorithmTest extends TestCase {

    private XMLMapFactory mMapFactory;
  /*  @Before
    public void setUp() throws Exception {
        String filename = "resources/tests/plan9.xml";
        mMapFactory = new XMLMapFactory(filename);
    }*/

    @Test
    public void testWithXml() throws Exception {
        Map map = mMapFactory.create();
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
        dijkstraAlgorithm.execute(map.getLocationByAddress("0"));
        LinkedList<Location> path = dijkstraAlgorithm.getPath(map.getLocationByAddress("9"));
        print_path(path);
    }

    private void print_path(LinkedList<Location> path) {
        int i = 0;

        while (i < 9) {
            try {

                System.out.println(path.get(i).getAddress());
                i++;
            } catch (Exception e) {
                System.out.println("Fin");
                i++;
            }
        }
    }

    @Test
    public void test_launch_xml_fail() {

        for(int i=0; i<10;i++) {
            String filename =("resources/tests/Ftest_plan_" + i + ".xml");
            mMapFactory = new XMLMapFactory(filename);
            Map map = null;
            try {
                map = mMapFactory.create();
            } catch (Exception e) {
                fail();
            }

             try {
                DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm();
                dijkstraAlgorithm.execute(map.getLocationByAddress("0"));
                LinkedList<Location> path = dijkstraAlgorithm.getPath(map.getLocationByAddress("9"));
                print_path(path);

            } catch (Exception e){
              System.out.println("Error occured");
              assertNull(map);
           }
        }
    }
}
