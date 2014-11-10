package fr.insalyon.optimod.models;

import org.w3c.dom.Element;

/**
 * Represents a package to be delivered
 */


public class Delivery {

    private String mPayload;
    private TomorrowDeliveries mTomorrowDeliveries;
    private Customer mCustomer;
    private TimeWindow mTimeWindow;
    private Location mLocation;


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
     * Payload and location constructor
     * @param location
     * @param payload
     */
    public Delivery(Location location, String payload) {
        mLocation = location;
        mPayload = payload;
    }

    public Location getLocation() {
        return mLocation;
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

    /**
     * Gets the associated time window
     * @return A Time Window
     */
    public TimeWindow getTimeWindow() {
        return mTimeWindow;
    }

    void setTimeWindow(TimeWindow tw) {
        mTimeWindow = tw;
    }

    /**
     * Deserializes a delivery from a dom node
     *
     * @param location Location of the delivery
     * @param node A dom node
     * @return A delivery
     */
    public static Delivery deserialize(Location location, Element node) throws DeserializationException {

        String payload = node.getAttribute("id");
        String customerName = node.getAttribute("client");

        Delivery delivery = new Delivery(location, payload);
        delivery.setCustomer(new Customer(customerName));

        return delivery;
    }

}
