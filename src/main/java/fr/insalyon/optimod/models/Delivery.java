package fr.insalyon.optimod.models;

/**
 * Represents a package to be delivered
 */
public class Delivery extends Location{
    private String mPayload;
    private TomorrowDeliveries mTomorrowDeliveries;
    private Customer mCustomer;

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

    /**
     * Gets the payload
     * @return A string
     */
    public String getPayload() {
        return mPayload;
    }

    /**
     * Gets the TomorrowDelivery
     * @return A TomorrowDelivery
     */
    public TomorrowDeliveries getTomorrowDeliveries() {
        return mTomorrowDeliveries;
    }

    void setTomorrowDeliveries(TomorrowDeliveries tomorrowDeliveries) {
        mTomorrowDeliveries = tomorrowDeliveries;
    }

    /**
     * Gets the customer requesting the delivery
     * @return A Client
     */
    public Customer getCustomer() {
        return mCustomer;
    }

    void setCustomer(Customer mClient) {
        this.mCustomer = mClient;
    }
}
