package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.TomorrowDeliveries;
import org.w3c.dom.Element;

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
    	Element node = loadXMLFile();
        return TomorrowDeliveries.deserialize(node);
    }
}
