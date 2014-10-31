package fr.insalyon.optimod.models;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Represents a time window for a delivery
 */
public class TimeWindow {
    /**
     * The TimeWindow comparator for TreeSets
     */
    public static final Comparator<TimeWindow> COMPARATOR = new Comparator<TimeWindow>() {
        @Override
        public int compare(TimeWindow tw1, TimeWindow tw2) {
            return (int)(tw2.getStart().getTime() - tw1.getStart().getTime());
        }
    };

    private final Date mStart;
    private final Date mEnd;
    private RoadMap mRoadMap;
    private List<Delivery> mDeliveries = new ArrayList<Delivery>();

    /**
     * Default Constructor
     */
    public TimeWindow() {
        mStart = new Date(0);
        mEnd = new Date(1);
    }

    /**
     * Time Constructor
     * @param start The start time for the time window
     * @param end The end time for the time window
     */
    public TimeWindow(Date start, Date end) {
        mStart = start;
        mEnd = end;
    }

    /**
     * Gets the associated road map
     * @return A Road Map
     */
    public RoadMap getRoadMap() {
        return mRoadMap;
    }

    void setRoadMap(RoadMap roadMap) {
        mRoadMap = roadMap;
    }


    /**
     * Add a new delivery
     * @param delivery A delivery
     */
    public void addDelivery(Delivery delivery) {
        delivery.setTimeWindow(this);
        mDeliveries.add(delivery);
    }

    /**
     * Delete a delivery
     * @param delivery A Delivery
     */
    public void deleteDelivery(Delivery delivery) {
        delivery.setTimeWindow(null);
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
     * Gets the start of the time window
     * @return A start date
     */
    public Date getStart() {
        return mStart;
    }

    /**
     * Gets the end of the time window
     * @return Anend date
     */
    public Date getEnd() {
        return mEnd;
    }

    /**
     * Deserializes a time window from a dom node
     * @param node A dom node
     * @return A time window
     */
    public static TimeWindow deserialize(Element node) throws DeserializationException {
    	
    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    	
    	//attributes
    	String start = node.getAttribute("heureDebut");
        String end = node.getAttribute("heureFin");
        
        Date startDate;
		try {
			startDate = sdf.parse(start);
			Date endDate = sdf.parse(end);
			TimeWindow timeWindow = new TimeWindow(startDate, endDate);
			
			// deliveries 
			NodeList deliveriesNode = node.getElementsByTagName("Livraisons");
			if(deliveriesNode.getLength() != 1)
			{
				return null;
			}
			
			Element deliveriesElement = (Element) deliveriesNode.item(0);
			NodeList listDeliveries = deliveriesElement.getElementsByTagName("Livraison");
			
			for (int i = 0; i < listDeliveries.getLength(); i++) {
				Element deliveryElement = (Element) listDeliveries.item(i);
				timeWindow.addDelivery(Delivery.deserialize(deliveryElement));
			}
			
			return timeWindow;
	        
		} catch (ParseException e) {
			return null;
		}
    }
}
