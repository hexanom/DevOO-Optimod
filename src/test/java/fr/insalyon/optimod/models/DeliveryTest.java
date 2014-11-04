package fr.insalyon.optimod.models;

import junit.framework.TestCase;

public class DeliveryTest extends TestCase{
    private Delivery mDelivery;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mDelivery = new Delivery("TestPayload");
    }

    public void testDefaultConstructor() throws Exception {
        assertNotNull(new Delivery().getPayload());
    }

    public void test_getPayload() throws Exception {
        assertEquals("TestPayload", mDelivery.getPayload());
    }

    public void test_getTomorrowDeliveries() throws Exception {
        TomorrowDeliveries td = new TomorrowDeliveries();
        td.addDelivery(mDelivery);
        assertEquals(mDelivery.getTomorrowDeliveries(), td);
        td.deleteDelivery(mDelivery);
        assertNull(mDelivery.getTomorrowDeliveries());
    }

    public void test_getCustomer() throws Exception {
        Customer customer = new Customer();
        customer.addDelivery(mDelivery);
        assertEquals(mDelivery.getCustomer(), customer);
        customer.deleteDelivery(mDelivery);
        assertNull(mDelivery.getCustomer());
    }

    public void test_getTimeWindow() throws Exception {
        TimeWindow tw = new TimeWindow();
        tw.addDelivery(mDelivery);
        assertEquals(mDelivery.getTimeWindow(), tw);
        tw.deleteDelivery(mDelivery);
        assertNull(mDelivery.getTimeWindow());
    }



}
