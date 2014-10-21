package fr.insalyon.optimod.models;

/**
 * Represents a package to be delivered
 */
public class Delivery {
    private String mPayload;
    private TomorrowDeliveries mTomorrowDeliveries;

    /**
     * Default Constructor
     */
    public Delivery() {
        mPayload = "Payload0";
    }

    /**
     * Payload constructor
     * @param payload A payload
     */
    public Delivery(String payload) {
        mPayload = payload;
    }

    void setTomorrowDeliveries(TomorrowDeliveries tomorrowDeliveries) {
        mTomorrowDeliveries = tomorrowDeliveries;
    }
}
