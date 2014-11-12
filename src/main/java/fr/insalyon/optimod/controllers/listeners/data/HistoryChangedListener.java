package fr.insalyon.optimod.controllers.listeners.data;

/**
 * Hooks on history stack events
 */
public interface HistoryChangedListener {
    /**
     * When the history stack changes
     *
     * @param hasHistory Has actions behind
     * @param hasFuture Has actions in the future
     */
    public void onHistoryChanged(boolean hasHistory, boolean hasFuture);
}
