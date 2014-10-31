package fr.insalyon.optimod.views.utils;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates list models from lists
 */
public class GenericListModel<T> implements ListModel<T> {
    private final List<T> mList;
    private final List<ListDataListener> mListeners;

    /**
     * Custructs a generic list model from a simple list
     *
     * @param list The list to create from
     */
    public GenericListModel(List<T> list) {
        mList = list;
        mListeners = new ArrayList<ListDataListener>();
    }

    @Override
    public int getSize() {
        return mList.size();
    }

    @Override
    public T getElementAt(int index) {
        return mList.get(index);
    }

    @Override
    public void addListDataListener(ListDataListener l) {
        mListeners.add(l);
    }

    @Override
    public void removeListDataListener(ListDataListener l) {
        mListeners.remove(l);
    }
}
