package fr.insalyon.optimod.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a customer waiting for deliveries
 */
public class Customer {
    private String mName;
    private List<Delivery> mDeliveries = new ArrayList<Delivery>();

    /**
     * Default constructor
     */
    public Customer() {
        mName = "Customer0";
    }

    /**
     * Name constructor
     * @param name The name
     */
    public Customer(String name) {
        mName = name;
    }

    /**
     * Add a new delivery
     * @param delivery A Delivery
     */
    public void addDelivery(Delivery delivery) {
        delivery.setCustomer(this);
        mDeliveries.add(delivery);
    }

    /**
     * Delete a delivery
     * @param delivery A Delivery
     */
    public void deleteDelivery(Delivery delivery) {
        delivery.setCustomer(null);
        mDeliveries.remove(delivery);
    }

    /**
     * Returns a read-only list
     * @return A list of deliveries
     */
    public final List<Delivery> getDeliveries() {
        return mDeliveries;
    }

    /**
     * Gets the customer's name
     * @return A name
     */
    public String getName() {
        return mName;
    }
}
