package fr.insalyon.optimod.models;

import org.w3c.dom.Element;

/**
 * Represents a start/end location
 */
public class Warehouse extends Location {
    /**
     * Deserializes a warehouse from a dom node
     * @param node A dom node
     * @return A warehouse
     */
	
	public Warehouse(String address){
		super(address, 0, 0);
	}
    public static Warehouse deserialize(Element node) throws DeserializationException {
    	
    	String address = node.getAttribute("adresse");
    	return new Warehouse(address);
    }
}
