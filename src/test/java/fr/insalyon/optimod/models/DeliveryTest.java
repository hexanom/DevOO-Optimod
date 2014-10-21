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
        assertTrue(new Delivery().getPayload() != null);
    }

    public void test_getPayload() throws Exception {
        assertEquals("TestPayload", mDelivery.getPayload());
    }

    public void test_getTomorrowDeliveries() throws Exception {
        TomorrowDeliveries td = new TomorrowDeliveries();
        td.addDelivery(mDelivery);
        assertEquals(mDelivery.getTomorrowDeliveries(), td);
        td.deleteDelivery(mDelivery);
        assertTrue(mDelivery.getTomorrowDeliveries() == null);
    }

    public void test_getCustomer() throws Exception {
        Customer customer = new Customer();
        customer.addDelivery(mDelivery);
        assertEquals(mDelivery.getCustomer(), customer);
        customer.deleteDelivery(mDelivery);
        assertTrue(mDelivery.getCustomer() == null);
    }
}
