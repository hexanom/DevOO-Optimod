package fr.insalyon.optimod.models;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the graph
 */
public class Map {
	private List<Location> mLocations = new ArrayList<Location>();

	/**
	 * Add a new location
	 * @param location A location
	 */
	public void addLocation(Location location) {
		location.setMap(this);
		mLocations.add(location);
	}

	/**
	 * Delete a location
	 * @param location A location
	 */
	public void deleteLocation(Location location) {
		location.setMap(null);
		mLocations.remove(location);
	}

	/**
	 * Returns a read-only list
	 * @return A list of Locations
	 */
	public final List<Location> getLocations() {
		return mLocations;
	}

	/**
	 * Get a Location which has the given address
	 * @param address
	 * @return A location with the given address
	 */
	public Location getLocationByAddress(String address)
	{
		Location location = null;
		for(Location l : mLocations)
		{
			if(l.getAddress().equals((address))){

				location = l;
				break;
			}
		}

		return location;
	}

	/**
	 * Deserializes a map from a dom node
	 * @param node A dom node
	 * @return A map
	 */
	public static Map deserialize(Element node) throws DeserializationException {

		Map myMap = new Map();

		NodeList listChildNodes = node.getChildNodes();
		for (int i = 0; i< listChildNodes.getLength(); i++) {
			if(listChildNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element locationNode = (Element)listChildNodes.item(i);
				Location location  = Location.deserialize(locationNode);

				location.setMap(myMap);
				myMap.addLocation(location);
			}
		}
		setMapSections(node, myMap);
		return myMap;
	}

	private static void setMapSections(Element node, Map map) throws DeserializationException {

		NodeList listChildNodes = node.getChildNodes();
		for (int i = 0; i< listChildNodes.getLength(); i++) {
			if(listChildNodes.item(i).getNodeType() == Node.ELEMENT_NODE) {
				Element locationNode = (Element)listChildNodes.item(i);
				String idLocation = locationNode.getAttribute("id");

				Location currentLocation = map.getLocationByAddress(idLocation);
				if(currentLocation != null) // 
				{
					currentLocation.deserializeSections(locationNode);
				}
			}
		}
	}
}
