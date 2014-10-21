package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.Map;

/**
 * Create maps from XML's DOM
 */
public class DomMapFactory extends DomFactory implements MapFactory {

    /**
     * Loads DOM document from a path
     * @param xmlPath The path to the xml to load
     */
    public DomMapFactory(String xmlPath) {
        super(xmlPath);
    }

    @Override
    public Map create() {
        return null; // TODO - implement dom deflation, get Document from getDocument()
    }
}
