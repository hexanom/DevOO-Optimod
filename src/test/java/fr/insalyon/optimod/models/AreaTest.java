package fr.insalyon.optimod.models;

import junit.framework.TestCase;

public class AreaTest extends TestCase {
    private Area mArea;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mArea = new Area("TestArea");
    }

    public void testDefaultConstructor() throws Exception {
        assertNotNull(new Area().getName());
    }

    public void test_getName() throws Exception {
        assertEquals(mArea.getName(), "TestArea");
    }

    public void test_addTomorrowDeliveries() throws Exception {
        TomorrowDeliveries td = new TomorrowDeliveries();
        mArea.addTomorowDeliveries(td);
        assertTrue(mArea.getTomorrowDeliveries().size() > 0);
        assertEquals(td.getArea(), mArea);
        mArea.deleteTomorowDeliveries(td);
    }

    public void test_deleteTomorrowDeliveries() throws Exception {
        TomorrowDeliveries td = new TomorrowDeliveries();
        mArea.addTomorowDeliveries(td);
        mArea.deleteTomorowDeliveries(td);
        assertTrue(mArea.getTomorrowDeliveries().size() == 0);
        assertNotSame(td.getArea(), mArea);
    }
}
