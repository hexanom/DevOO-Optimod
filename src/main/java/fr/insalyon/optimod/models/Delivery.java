package fr.insalyon.optimod.models;

import org.w3c.dom.Element;

/**
 * Represents a package to be delivered
 */


public class Delivery extends Location{


    private String mPayload;
    private TomorrowDeliveries mTomorrowDeliveries;
    private Customer mCustomer;
    private TimeWindow mTimeWindow;



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
     * Payload and address constructor
     * @param payload
     * @param address
     */
    public Delivery(String payload, String address) {
        super(address, 0,0);
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
     * @param node A dom node
     * @return A delivery
     */
    public static Delivery deserialize(Element node) throws DeserializationException {

        //attributes
        // id ??
        String payload = node.getAttribute("id");
        String address = node.getAttribute("adresse");
        String customerName = node.getAttribute("client");

        Delivery delivery = new Delivery(payload, address);
        delivery.setCustomer(new Customer(customerName));

        return delivery;
    }

}
