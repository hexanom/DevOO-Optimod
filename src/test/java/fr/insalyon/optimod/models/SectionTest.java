package fr.insalyon.optimod.models;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SectionTest extends TestCase{
    private Section mSection;
    private Location Ori;
    private Location Dest;
    private Map mMap;

    @Override
    public void setUp() throws Exception {
        super.setUp();
       // List<Location> mMap = new ArrayList<Location>();
        //mMap.add(Ori);
        //mMap.add(Dest);
        mSection = new Section("Bobstreet",5,5);
    }

    public void test_getOrigin() throws Exception {
        Ori = new Location("Bob_0",0,0);
        mSection.setOrigin(Ori);
        assertEquals( mSection.getOrigin(),Ori);
    }

    public void test_getDestination() throws Exception {
        Dest = new Location("Bob_1",5,0);
        mSection.setDestination(Dest);
        assertEquals( mSection.getDestination(),Dest);
    }

    public void test_getStreetName() throws Exception {

        assertEquals( mSection.getStreetName(),"Bobstreet");
    }

    public void test_getSpeed() throws Exception {

        assertEquals( mSection.getSpeed(),5);
    }

    public void test_getLength() throws Exception {

        assertEquals( mSection.getLength(),5);
    }

    public void test_getTime() throws Exception {

        assertEquals( mSection.getTime(),1);
    }
}
