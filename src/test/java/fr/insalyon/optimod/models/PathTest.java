package fr.insalyon.optimod.models;

import org.junit.Test;

import static org.junit.Assert.*;

public class PathTest {

    @Test
    public void getOrderedSections() throws Exception {
        Path mPath = new Path();
        assertTrue(mPath.getOrderedSections().size() == 0);
        Section test_Section_1 = new Section("BobStreet", 3, 5);
        Section test_Section_2 = new Section("BobRoad", 5, 7);
        Location test_Section_1_Ori = new Location("00BobStreet", 0, 0);
        Location test_Section_12_DestOri = new Location("01BobStreet", 5, 5);
        Location test_Section_2_Dest = new Location("05BobStreet", 10, 10);
        test_Section_1.setOrigin(test_Section_1_Ori);
        test_Section_1.setDestination(test_Section_12_DestOri);
        test_Section_2.setOrigin(test_Section_12_DestOri);
        test_Section_2.setDestination(test_Section_2_Dest);
        mPath.appendSection(test_Section_1);
        mPath.appendSection(test_Section_2);
        assertEquals(mPath.getOrderedSections().size(), 2);
        assertEquals(mPath.getOrderedSections().get(0), test_Section_1);
        mPath.removeOrigin();
        mPath.removeDestination();
    }

    @Test
    public void getOrigin() throws Exception {
        Path mPath = new Path();
        assertNull(mPath.getOrigin());
        Location test_Section_1_Ori = new Location("00BobStreet", 0, 0);
        Location test_Section_1_Dest = new Location("01BobStreet", 5, 5);
        Section test_Section_1 = new Section("BobStreet", 5, 5);
        test_Section_1.setOrigin(test_Section_1_Ori);
        test_Section_1.setDestination(test_Section_1_Dest);
        mPath.appendSection(test_Section_1);
        assertEquals(mPath.getOrigin(), test_Section_1_Ori);
        mPath.removeOrigin();
        assertNull(mPath.getOrigin());
    }

    @Test
    public void getDestination() throws Exception {
        Path mPath = new Path();
        assertNull(mPath.getDestination());
        Section test_Section_1 = new Section("BobStreet", 3, 5);
        Section test_Section_2 = new Section("BobRoad", 5, 7);
        Location test_Section_1_Ori = new Location("00BobStreet", 0, 0);
        Location test_Section_12_DestOri = new Location("01BobStreet", 5, 5);
        Location test_Section_2_Dest = new Location("05BobStreet", 10, 10);
        test_Section_1.setOrigin(test_Section_1_Ori);
        test_Section_1.setDestination(test_Section_12_DestOri);
        test_Section_2.setOrigin(test_Section_12_DestOri);
        test_Section_2.setDestination(test_Section_2_Dest);
        mPath.appendSection(test_Section_1);
        mPath.appendSection(test_Section_2);
        assertEquals(mPath.getDestination(), test_Section_2_Dest);
        mPath.removeDestination();
        assertEquals(mPath.getDestination(), test_Section_12_DestOri);
        mPath.removeDestination();
        assertNull(mPath.getDestination());
    }

    @Test
    public void removeOrigin() throws Exception {
        Path mPath = new Path();
        Section test_Section = new Section("BobStreet", 3, 5);
        Location test_Section_Ori = new Location("00BobStreet", 0, 0);
        Location test_Section_Dest = new Location("05BobStreet", 10, 10);
        mPath.appendSection(test_Section);
        test_Section.setOrigin(test_Section_Ori);
        test_Section.setDestination(test_Section_Dest);
        assertNotNull(mPath);
        mPath.removeOrigin();
        assertNull(mPath.getOrigin());
        mPath.removeDestination();


    }

    @Test
    public void removeDestination() throws Exception {
        Path mPath = new Path();
        Section test_Section = new Section("BobStreet", 3, 5);
        Location test_Section_Ori = new Location("00BobStreet", 0, 0);
        Location test_Section_Dest = new Location("05BobStreet", 10, 10);
        test_Section.setOrigin(test_Section_Ori);
        test_Section.setDestination(test_Section_Dest);
        assertNotNull(mPath);
        mPath.removeDestination();
        assertNull(mPath.getDestination());
        mPath.removeOrigin();


    }
}
