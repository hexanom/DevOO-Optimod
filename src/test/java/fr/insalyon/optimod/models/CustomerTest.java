package fr.insalyon.optimod.models;

import junit.framework.TestCase;

public class CustomerTest extends TestCase {
    private Customer mCustomer;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mCustomer = new Customer("TestCustomer");
    }

    public void testDefaultConstructor() throws Exception {
        assertTrue(new Customer().getName() != null);
    }

    public void test_getName() throws Exception {
        assertEquals(mCustomer.getName(), "TestCustomer");
    }

    public void test_addDelivery() throws Exception {
        Delivery delivery = new Delivery();
        mCustomer.addDelivery(delivery);
        assertTrue(mCustomer.getDeliveries().size() > 0);
        assertEquals(delivery.getCustomer(), mCustomer);
        mCustomer.deleteDelivery(delivery);
    }

    public void test_deleteTomorrowDeliveries() throws Exception {
        Delivery delivery = new Delivery();
        mCustomer.addDelivery(delivery);
        mCustomer.deleteDelivery(delivery);
        assertTrue(mCustomer.getDeliveries().size() == 0);
        assertTrue(delivery.getCustomer() != mCustomer);
    }
}
