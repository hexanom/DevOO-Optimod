package fr.insalyon.optimod.models.factories;

import org.w3c.dom.Element;

import fr.insalyon.optimod.models.TomorrowDeliveries;

/**
 * Creates the tomorrow deliveries from the xml file
 */
public class XMLTomorrowDeliveriesFactory extends XMLFactoryBase implements ModelFactory<TomorrowDeliveries> {
    /**
     * Path constructor
     * @param path The path to the deliveries file
     */
    public XMLTomorrowDeliveriesFactory(String path) {
        super(path);
    }

    @Override
    public TomorrowDeliveries create() throws Exception {
    	String xsdFile = "resources/xml/livraison.xsd";
    	Element node = loadXMLFile(xsdFile);
    	if(node != null)
		{
    		return TomorrowDeliveries.deserialize(node);
		}
		else 
		{
			return null;
		}
    	
        
    }
}
