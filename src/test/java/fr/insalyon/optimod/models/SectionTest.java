package fr.insalyon.optimod.models;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;

public class SectionTest extends TestCase{


    @Override
    public void setUp() throws Exception {
        super.setUp();
       // List<Location> mMap = new ArrayList<Location>();
        //mMap.add(Ori);
        //mMap.add(Dest);
       Section mSection = new Section("Bobstreet",5,5);
    }

    public void test_getOrigin() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);
        Location Ori = new Location("Bob_0",0,0);
        mSection.setOrigin(Ori);
        assertEquals( mSection.getOrigin(),Ori);
    }

    public void test_getDestination() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);
        Location Dest = new Location("Bob_1",5,0);
        mSection.setDestination(Dest);
        assertEquals( mSection.getDestination(),Dest);
    }

    public void test_getStreetName() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);

        assertEquals( mSection.getStreetName(),"Bobstreet");
    }

    public void test_getSpeed() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);

        assertEquals( mSection.getSpeed(),5.0);
    }

    public void test_getLength() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);

        assertEquals( mSection.getLength(),5.0);
    }

    public void test_getTime() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);

        assertEquals( mSection.getTime(),1.0);
    }
}
