package fr.insalyon.optimod.controllers;

import fr.insalyon.optimod.controllers.actions.Action;
import fr.insalyon.optimod.controllers.listeners.data.HistoryChangedListener;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Defines a controller that supports undo/redo actions
 */
public abstract class HistoryEnabledController implements Controller {
    Deque<Action> mHistory = new ArrayDeque<Action>();
    Deque<Action> mFuture = new ArrayDeque<Action>();
    private HistoryChangedListener mListener;

    /**
     * Does an action for the first time
     * It will <strong>empty the redo stack</strong>
     *
     * @param action The action to stack on top of the history
     */
    protected void doAction(Action action) {
        mFuture.clear();
        mHistory.addLast(action);
        action.doAction();
        if(mListener != null) {
            mListener.onHistoryChanged(canUndo(), canRedo());
        }
    }

    /**
     * Undoes the last action
     */
    protected void undo() {
        if(canUndo()) {
            Action action = mHistory.pollLast();
            mFuture.addLast(action);
            action.undoAction();
            if(mListener != null) {
                mListener.onHistoryChanged(canUndo(), canRedo());
            }
        }
    }

    /**
     * Does again the undid action
     */
    protected void redo() {
        if(canRedo()) {
            Action action = mFuture.pollLast();
            mHistory.addLast(action);
            action.doAction();
            if(mListener != null) {
                mListener.onHistoryChanged(canUndo(), canRedo());
            }
        }
    }

    protected void clearHistory() {
        mFuture.clear();
        mHistory.clear();
        if(mListener != null) {
            mListener.onHistoryChanged(false, false);
        }
    }

    protected void clearFuture() {
        mFuture.clear();
        if(mListener != null) {
            mListener.onHistoryChanged(canUndo(), false);
        }
    }

    /**
     * Defines if the history is not empty
     *
     * @return The possibility of an undo
     */
    private boolean canUndo() {
        return !mHistory.isEmpty();
    }

    /**
     * Defines if the redo stack is not empty
     *
     * @return The possibility of a redo
     */
    private boolean canRedo() {
        return !mFuture.isEmpty();
    }

    /**
     * Sets a listener on history changes
     *
     * @param listener A view usually
     */
    protected void setHistoryListener(HistoryChangedListener listener) {
        mListener = listener;
    }
}
