package fr.insalyon.optimod.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CustomerTest {
    private Customer mCustomer;

    @Before
    public void setUp() throws Exception {
        mCustomer = new Customer("TestCustomer");
    }

    @Test
    public void defaultConstructor() throws Exception {
        assertNotNull(new Customer().getName());
    }

    @Test
    public void getName() throws Exception {
        assertEquals(mCustomer.getName(), "TestCustomer");
    }

    @Test
    public void addDelivery() throws Exception {
        Delivery delivery = new Delivery();
        mCustomer.addDelivery(delivery);
        assertTrue(mCustomer.getDeliveries().size() > 0);
        assertEquals(delivery.getCustomer(), mCustomer);
        mCustomer.deleteDelivery(delivery);
    }

    @Test
    public void deleteTomorrowDeliveries() throws Exception {
        Delivery delivery = new Delivery();
        mCustomer.addDelivery(delivery);
        mCustomer.deleteDelivery(delivery);
        assertTrue(mCustomer.getDeliveries().size() == 0);
        assertNotSame(delivery.getCustomer(), mCustomer);
    }
}
