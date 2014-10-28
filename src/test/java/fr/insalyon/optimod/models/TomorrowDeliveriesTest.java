package fr.insalyon.optimod.models;

import junit.framework.TestCase;

public class TomorrowDeliveriesTest extends TestCase {

    @Override
    public void setUp() throws Exception {
        super.setUp();

    }

    public void test_getArea() throws Exception {
        TomorrowDeliveries mTomorrowDeliveries = new  TomorrowDeliveries();
        Area test_Area = new Area();
        mTomorrowDeliveries.setArea(test_Area);
        assertEquals(mTomorrowDeliveries.getArea(),test_Area);
    }

    public void test_addDelivery() throws Exception {
        TomorrowDeliveries mTomorrowDeliveries = new  TomorrowDeliveries();
        Delivery test_Delivery_1 = new Delivery();
        Delivery test_Delivery_2 = new Delivery();
        mTomorrowDeliveries.addDelivery(test_Delivery_1);
        assertEquals(mTomorrowDeliveries.getDeliveries().get(0),test_Delivery_1);
        mTomorrowDeliveries.addDelivery(test_Delivery_1);
        //Non vérification de l'unicité de la commande
        assertEquals(mTomorrowDeliveries.getDeliveries().get(0),test_Delivery_1);
        assertEquals(mTomorrowDeliveries.getDeliveries().get(1),test_Delivery_1);
    }
}

