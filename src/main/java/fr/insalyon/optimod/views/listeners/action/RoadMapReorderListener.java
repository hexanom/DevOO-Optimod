package fr.insalyon.optimod.views.listeners.action;

/**
 * Binds to the roadmap reordering events
 */
public interface RoadMapReorderListener {
    /**
     * When the roadmap is reordered
     *
     * @param elementIndex The original element at index
     * @param movedToIndex Moved to this index
     */
    public void onRoadMapReorder(int elementIndex, int movedToIndex);
}
