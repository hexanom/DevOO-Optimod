package fr.insalyon.optimod.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TomorrowDeliveriesTest {

    @Test
    public void getArea() throws Exception {
        TomorrowDeliveries mTomorrowDeliveries = new  TomorrowDeliveries();
        Area test_Area = new Area();
        mTomorrowDeliveries.setArea(test_Area);
        assertEquals(mTomorrowDeliveries.getArea(),test_Area);
    }

    @Test
    public void addDelivery() throws Exception {
        TomorrowDeliveries mTomorrowDeliveries = new  TomorrowDeliveries();
        Delivery test_Delivery_1 = new Delivery();
        Delivery test_Delivery_2 = new Delivery();
        mTomorrowDeliveries.addDelivery(test_Delivery_1);
        assertEquals(mTomorrowDeliveries.getDeliveries().get(0),test_Delivery_1);
        mTomorrowDeliveries.addDelivery(test_Delivery_1);
        assertEquals(mTomorrowDeliveries.getDeliveries().get(0),test_Delivery_1);
        assertEquals(mTomorrowDeliveries.getDeliveries().get(1),test_Delivery_1);
        mTomorrowDeliveries.addDelivery(test_Delivery_2);
        assertEquals(mTomorrowDeliveries.getDeliveries().get(2),test_Delivery_2);
    }

    @Test
    public void deleteDelivery() throws Exception {
        TomorrowDeliveries mTomorrowDeliveries = new  TomorrowDeliveries();
        Delivery test_Delivery_1 = new Delivery();
        Delivery test_Delivery_2 = new Delivery();
        mTomorrowDeliveries.addDelivery(test_Delivery_1);
        assertEquals(mTomorrowDeliveries.getDeliveries().get(0),test_Delivery_1);
        mTomorrowDeliveries.addDelivery(test_Delivery_2);
        mTomorrowDeliveries.deleteDelivery(test_Delivery_1);
        assertEquals(mTomorrowDeliveries.getDeliveries().size(),1);
        mTomorrowDeliveries.deleteDelivery(test_Delivery_2);
        assertEquals(mTomorrowDeliveries.getDeliveries().size(),0);
    }

    @Test
    public void addRoadMap() throws Exception {
        TomorrowDeliveries mTomorrowDeliveries = new  TomorrowDeliveries();
        RoadMap test_RoadMap_1 = new RoadMap();
        RoadMap test_RoadMap_2 = new RoadMap();
        mTomorrowDeliveries.addRoadMap(test_RoadMap_1);
        assertEquals(mTomorrowDeliveries.getRoadMaps().get(0),test_RoadMap_1);
        mTomorrowDeliveries.addRoadMap(test_RoadMap_1);
        assertEquals(mTomorrowDeliveries.getRoadMaps().get(0),test_RoadMap_1);
        assertEquals(mTomorrowDeliveries.getRoadMaps().get(1),test_RoadMap_1);
        mTomorrowDeliveries.addRoadMap(test_RoadMap_2);
        assertEquals(mTomorrowDeliveries.getRoadMaps().get(2),test_RoadMap_2);
    }

    @Test
    public void deleteRoadMap() throws Exception {
        TomorrowDeliveries mTomorrowDeliveries = new  TomorrowDeliveries();
        RoadMap test_RoadMap_1 = new RoadMap();
        RoadMap test_RoadMap_2 = new RoadMap();

        mTomorrowDeliveries.addRoadMap(test_RoadMap_1);
        mTomorrowDeliveries.addRoadMap(test_RoadMap_2);
        mTomorrowDeliveries.deleteRoadMap(test_RoadMap_1);
        assertEquals(mTomorrowDeliveries.getRoadMaps().size(),1);
        mTomorrowDeliveries.deleteRoadMap(test_RoadMap_2);
        assertEquals(mTomorrowDeliveries.getRoadMaps().size(),0);
    }
}

