package fr.insalyon.optimod.models.factories;

import org.w3c.dom.Node;

/**
 * XML Loader
 */
public abstract class XMLFactoryBase {
    private String mPath;

    /**
     * Path constructor
     * @param path The path to the file
     */
    public XMLFactoryBase(String path) {
        mPath = path;
    }

    /**
     * Loads the root node from an XML File
     * @return A dom node
     */
    protected Node loadXMLFile() {
        return null; // TODO
    }
}
