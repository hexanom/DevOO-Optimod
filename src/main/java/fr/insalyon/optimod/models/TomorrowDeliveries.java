package fr.insalyon.optimod.models;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the deliveries for a day
 */
public class TomorrowDeliveries {
	private Area mArea;
	private Warehouse mWareHouse;
	private List<Delivery> mDeliveries = new ArrayList<Delivery>();
	private List<RoadMap> mRoadMaps = new ArrayList<RoadMap>();

	/**
	 * Gets the Area
	 * @return An Area
	 */
	public Area getArea() {
		return mArea;
	}

	// NOTE: nothing = package visibility
	void setArea(Area a) {
		mArea = a;
	}

	/**
	 * Gets the warehouse
	 * @return A Warehouse
	 */
	public Warehouse getWareHouse() {
		return mWareHouse;
	}

	public void setWareHouse(Warehouse mWareHouse) {
		this.mWareHouse = mWareHouse;
	}
	/**
	 * Add a new delivery
	 * @param delivery A delivery
	 */
	public void addDelivery(Delivery delivery) {
		delivery.setTomorrowDeliveries(this);
		mDeliveries.add(delivery);
	}

	/**
	 * Delete a delivery
	 * @param delivery A delivery
	 */
	public void deleteDelivery(Delivery delivery) {
		delivery.setTomorrowDeliveries(null);
		mDeliveries.remove(delivery);
	}

	/**
	 * Returns a read-only list
	 * @return A list of Deliveries
	 */
	public final List<Delivery> getDeliveries() {
		return mDeliveries;
	}

	/**
	 * Add a new road map
	 * @param rm A road map
	 */
	public void addRoadMap(RoadMap rm) {
		rm.setTomorrowDeliveries(this);
		mRoadMaps.add(rm);
	}

	/**
	 * Delete a road map
	 * @param rm A road map
	 */
	public void deleteRoadMap(RoadMap rm) {
		rm.setTomorrowDeliveries(null);
		mRoadMaps.remove(rm);
	}

	/**
	 * Returns a read-only list
	 * @return A list of RoadMap
	 */
	public final List<RoadMap> getRoadMaps() {
		return mRoadMaps;
	}

	/**
	 * Deserializes a tomorrow deliveries from a dom node
	 * @param node A dom node
	 * @return A tomorrow deliveries
	 */
	public static TomorrowDeliveries deserialize(Element node) throws DeserializationException {

		TomorrowDeliveries tomorrowDeliveries = new TomorrowDeliveries();
		// calls sub deserializations depending on the subnodes names
		String tag = "";
		// Warehouse
		tag = "Entrepot";
		NodeList listChildNodes = node.getElementsByTagName(tag);
		if (listChildNodes.getLength() != 1) {
			return null;
		}

		Element warehouseElement = (Element) listChildNodes.item(0);
		// set warehouse
		tomorrowDeliveries.setWareHouse(Warehouse.deserialize(warehouseElement));

		// TimeWindows and deliveries
		tag = "PlagesHoraires";
		listChildNodes = node.getElementsByTagName(tag);
		if (listChildNodes.getLength() != 1) {
			return null;
		}

		Element timeWindowElement = (Element) listChildNodes.item(0);
		NodeList listTimeWindows = timeWindowElement.getElementsByTagName("Plage");
		if (listTimeWindows.getLength() < 1) {
			return null;
		}

		for (int j = 0; j < listTimeWindows.getLength(); j++) {
			Element windowElement = (Element) listTimeWindows.item(j);
			TimeWindow timeWindow = TimeWindow.deserialize(windowElement);

			//TODO timeWindow ??
			if(timeWindow != null){
				for(Delivery d : timeWindow.getDeliveries())
				{
					tomorrowDeliveries.addDelivery(d);
				}
			}
		}

		return tomorrowDeliveries;
	}
}
