package fr.insalyon.optimod.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeliveryTest {
    private Delivery mDelivery;

    @Before
    public void setUp() throws Exception {
        mDelivery = new Delivery("TestPayload");
    }

    @Test
    public void defaultConstructor() throws Exception {
        assertNotNull(new Delivery().getPayload());
    }

    @Test
    public void getPayload() throws Exception {
        assertEquals("TestPayload", mDelivery.getPayload());
    }

    @Test
    public void getTomorrowDeliveries() throws Exception {
        TomorrowDeliveries td = new TomorrowDeliveries();
        td.addDelivery(mDelivery);
        assertEquals(mDelivery.getTomorrowDeliveries(), td);
        td.deleteDelivery(mDelivery);
        assertNull(mDelivery.getTomorrowDeliveries());
    }

    @Test
    public void getCustomer() throws Exception {
        Customer customer = new Customer();
        customer.addDelivery(mDelivery);
        assertEquals(mDelivery.getCustomer(), customer);
        customer.deleteDelivery(mDelivery);
        assertNull(mDelivery.getCustomer());
    }

    @Test
    public void getTimeWindow() throws Exception {
        TimeWindow tw = new TimeWindow();
        tw.addDelivery(mDelivery);
        assertEquals(mDelivery.getTimeWindow(), tw);
        tw.deleteDelivery(mDelivery);
        assertNull(mDelivery.getTimeWindow());
    }



}
