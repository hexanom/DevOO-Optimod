package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.Map;

import org.w3c.dom.Element;

/**
 * Constructs a map from a XML file
 */
public class XMLMapFactory extends XMLFactoryBase implements ModelFactory<Map> {
    /**
     * Path constructor
     * @param path The path to the map file
     */
    public XMLMapFactory(String path) {
        super(path);
    }

    @Override
    public Map create() throws Exception {
        String xsdFile = "resources/xml/plan.xsd";
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
