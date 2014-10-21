package fr.insalyon.optimod.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a graph node
 */
public class Location {
    private List<Section> mIns = new ArrayList<Section>();
    private List<Section> mOuts = new ArrayList<Section>();

    /**
     * Connects the node to another one
     * @param location An other node
     */
    public void connectTo(Location location) {
        Section section = new Section();
        section.setOrigin(this);
        mOuts.add(section);
        location.connectedFrom(section);
    }

    void connectedFrom(Section section) {
        section.setDestination(this);
        mIns.add(section);
    }

    /**
     * Delete any of your IN/OUT arrows
     * @param section An arrow
     */
    public void deleteConnectedSection(Section section) {
        if(section.getOrigin().equals(this)) {
            section.setOrigin(null);
            mOuts.remove(section);
            if(section.getDestination() != null) {
                section.getDestination().deleteConnectedSection(section);
            }
        } else if(section.getDestination().equals(this)) {
            section.setDestination(null);
            mIns.remove(section);
            if(section.getOrigin() != null) {
                section.getOrigin().deleteConnectedSection(section);
            }
        }
    }

    /**
     * Get the incoming arrows
     * @return A list of arrows
     */
    public List<Section> getIns() {
        return mIns;
    }

    /**
     * Get the the outgoing arrows
     * @return A list of arrows
     */
    public List<Section> getOuts() {
        return mOuts;
    }

    /**
     * Gets all the connected arrows
     * @return A list of arrows
     */
    public List<Section> getConnectedSections() {
        List<Section> all = new ArrayList<Section>();
        all.addAll(mIns);
        all.addAll(mOuts);
        return all;
    }
}
