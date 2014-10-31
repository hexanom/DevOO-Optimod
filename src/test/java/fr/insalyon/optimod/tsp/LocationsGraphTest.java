package fr.insalyon.optimod.tsp;

import fr.insalyon.optimod.models.Location;
import fr.insalyon.optimod.models.Map;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by edouard on 31/10/14.
 */
public class LocationsGraphTest {

    private Map map;
    private List<Location> locations;

    @Before
    public void before() {

        this.map = new Map();

        List<Location> locations = new ArrayList<Location>();
        Location l1 = new Location("0", 0, 0);
        l1.setMap(this.map);
        Location l2 = new Location("1", 1, 1);
        l2.setMap(this.map);
        Location l3 = new Location("2", 2, 2);
        l3.setMap(this.map);
        Location l4 = new Location("3", 3, 3);
        l4.setMap(this.map);
        Location l5 = new Location("4", 4, 4);
        l5.setMap(this.map);
        locations.add(l1);
        locations.add(l2);
        locations.add(l3);
        locations.add(l4);
        locations.add(l5);
        this.locations = locations;
    }

    @Test
    public void testSuccessors() {
        this.locations.get(0).connectTo(this.locations.get(1), "r1", 10, 10);
        this.locations.get(0).connectTo(this.locations.get(2), "r3", 5, 2);

        LocationsGraph locationsGraph = new LocationsGraph(this.locations);

        assert(locationsGraph.getNbSucc(0) == 2);
    }

    @Test
    public void testSuccessorsFiltering() {
        this.locations.get(0).connectTo(this.locations.get(1), "r1", 10, 10);
        this.locations.get(0).connectTo(this.locations.get(2), "r2", 5, 2);
        this.locations.get(0).connectTo(new Location(), "r3", 5, 2);

        LocationsGraph locationsGraph = new LocationsGraph(this.locations);

        assert(locationsGraph.getNbSucc(0) == 2);
    }

    @Test
    public void testNbVertices() {
        LocationsGraph locationsGraph = new LocationsGraph(this.locations);
        assert(locationsGraph.getNbVertices() == 5);
    }

    @Test
    public void testMaxArcCost() {
        this.locations.get(0).connectTo(this.locations.get(1), "r1", 1, 100);
        this.locations.get(0).connectTo(new Location(), "r2", 1, 1);
        this.locations.get(1).connectTo(new Location(), "r3", 2, 50000);
        this.locations.get(1).connectTo(this.locations.get(2), "r4", 25, 100);
        this.locations.get(2).connectTo(this.locations.get(3), "r5", 5, 100);
        this.locations.get(4).connectTo(this.locations.get(2), "r6", 10, 100);
        this.locations.get(4).connectTo(this.locations.get(3), "r7", 2, 100);

        LocationsGraph locationsGraph = new LocationsGraph(this.locations);
        assert(locationsGraph.getMaxArcCost() == 100);
        assert(locationsGraph.getMinArcCost() == 4);
    }
}
