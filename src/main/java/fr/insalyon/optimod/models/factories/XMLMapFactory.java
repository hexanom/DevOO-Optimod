package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.Map;

import org.w3c.dom.Element;

import java.net.URI;
import java.net.URL;

/**
 * Constructs a map from a XML file
 */
public class XMLMapFactory extends XMLFactoryBase implements ModelFactory<Map> {
    /**
     * Path constructor
     * @param path The path to the map file
     */
    public XMLMapFactory(URI path) {
        super(path);
    }

    @Override
    public Map create() throws Exception {
        String xsdFile = "plan.xsd";
        Element node = loadXMLFile(xsdFile);
        if(node != null)
        {
            return Map.deserialize(node);
        }
        else
        {
            return null;
        }

    }
}
