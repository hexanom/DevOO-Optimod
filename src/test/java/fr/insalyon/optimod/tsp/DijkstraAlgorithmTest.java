package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import fr.insalyon.optimod.models.factories.XMLMapFactory;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.LinkedList;

/**
 * Created by edouard on 04/11/14.
 */
public class DijkstraAlgorithmTest extends TestCase {

    private XMLMapFactory mMapFactory;
    static final int[][] pathData={{0,9},
            {0,2,4,6,8,9},
            {0,5,9},
            };

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
    public void test_launch_xml_fail() throws Exception {

        for(int i=0; i<10;i++) {
            String filename =("Ftest_plan_" + i + ".xml");
            URI uri = getClass().getClassLoader().getResource(filename).toURI();
            mMapFactory = new XMLMapFactory(uri);
            Map map = null;
            try {
                map = mMapFactory.create();
                fail();
            } catch (Exception e) {
                assertNull(map);
            }
        }
    }

    public void test_launch_xml_success() throws Exception {
        for(int i=0;i<3;i++){
            String filename =("Stest_plan_" + i + ".xml");
            URI uri = getClass().getClassLoader().getResource(filename).toURI();
            mMapFactory = new XMLMapFactory(uri);
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
                assertTrue(check_dijkstraPath(path,i));
            } catch (Exception e){

                fail();
            }
        }
    }

    private boolean check_dijkstraPath( LinkedList<Location> path,int i){

        for(int k=0; k<path.size(); k++){
           if(Integer.valueOf(path.get(k).getAddress()) != pathData[i][k]){
               return false;
           }

        }
        return true;
    }
}
