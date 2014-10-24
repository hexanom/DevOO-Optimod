package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.Map;
import org.w3c.dom.Node;

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
        Node node = loadXMLFile();
        return Map.deserialize(node);
    }
}
