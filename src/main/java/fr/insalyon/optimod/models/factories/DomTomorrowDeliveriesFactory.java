package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.TomorrowDeliveries;

/**
 * Create tomorrow deliveries from XML's DOM
 */
public class DomTomorrowDeliveriesFactory extends DomFactory implements TomorrowDeliveriesFactory{

    /**
     * Loads DOM document from a path
     * @param xmlPath The path to the xml to load
     */
    public DomTomorrowDeliveriesFactory(String xmlPath) {
        super(xmlPath);
    }

    @Override
    public TomorrowDeliveries create() {
        return null; // TODO - implement dom deflation, get Document from getDocument()
    }
}
