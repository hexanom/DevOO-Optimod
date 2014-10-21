package fr.insalyon.optimod.models.factories;

import fr.insalyon.optimod.models.TomorrowDeliveries;

/**
 * Trait that characterize objects able to generate maps
 */
public interface TomorrowDeliveriesFactory {
    /**
     * Creates the tomorrow deliveries
     * @return The tomorrow deliveries or null if it failed for some reason
     */
    public TomorrowDeliveries create();
}
