package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.models.TomorrowDeliveries;

/**
 * Binds to a tomorrowdeliveriss update
 */
public interface TomorrowDeliveriesListener {
    /**
     * When the TDs changes
     *
     * @param tomorrowDeliveries The TDs
     */
    public void onTomorrowDeliveryChanged(TomorrowDeliveries tomorrowDeliveries);
}
