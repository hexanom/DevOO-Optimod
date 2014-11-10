package fr.insalyon.optimod.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AreaTest {
    private Area mArea;

    @Before
    public void setUp() throws Exception {
        mArea = new Area("TestArea");
    }

    @Test
    public void defaultConstructor() throws Exception {
        assertNotNull(new Area().getName());
    }

    @Test
    public void getName() throws Exception {
        assertEquals(mArea.getName(), "TestArea");
    }

    @Test
    public void addTomorrowDeliveries() throws Exception {
        TomorrowDeliveries td = new TomorrowDeliveries();
        mArea.addTomorowDeliveries(td);
        assertTrue(mArea.getTomorrowDeliveries().size() > 0);
        assertEquals(td.getArea(), mArea);
        mArea.deleteTomorowDeliveries(td);
    }

    @Test
    public void deleteTomorrowDeliveries() throws Exception {
        TomorrowDeliveries td = new TomorrowDeliveries();
        mArea.addTomorowDeliveries(td);
        mArea.deleteTomorowDeliveries(td);
        assertTrue(mArea.getTomorrowDeliveries().size() == 0);
        assertNotSame(td.getArea(), mArea);
    }
}
