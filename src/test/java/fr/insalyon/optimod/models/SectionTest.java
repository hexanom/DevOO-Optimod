package fr.insalyon.optimod.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SectionTest {

    @Test
    public void getOrigin() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);
        Location Ori = new Location("Bob_0",0,0);
        mSection.setOrigin(Ori);
        assertEquals(mSection.getOrigin(),Ori);
    }

    @Test
    public void getDestination() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);
        Location Dest = new Location("Bob_1",5,0);
        mSection.setDestination(Dest);
        assertEquals(mSection.getDestination(),Dest);
    }

    @Test
    public void getStreetName() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);

        assertEquals(mSection.getStreetName(),"Bobstreet");
    }

    @Test
    public void getSpeed() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);

        assertEquals(mSection.getSpeed(), 5.0, 0.1);
    }

    @Test
    public void getLength() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);

        assertEquals(mSection.getLength(), 5.0, 0.1);
    }

    @Test
    public void getTime() throws Exception {
        Section mSection = new Section("Bobstreet",5,5);

        assertEquals(mSection.getTime(), 1.0, 0.1);
    }
}
