package fr.insalyon.optimod.models.factories;

import org.w3c.dom.Document;

/**
 * Base for Dom Factories
 */
public abstract class DomFactory {
    private final Document mDocument;

    /**
     * Loads DOM document from a path
     * @param xmlPath The path to the xml to load
     */
    public DomFactory(String xmlPath) {
        mDocument = null; // TODO - load file and get the root DOM element
    }

    /**
     * Gets the DOM document
     * @return A Document Node
     */
    protected Document getDocument() {
        return mDocument;
    }
}
