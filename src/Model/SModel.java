package Model;

import Events.Listeners.ModelListener;
import Events.ModelEvents.ModelEvent;

/**
 * Interface for Scrabble Models (SModel).
 * Allows Model listeners (ModelListener) to listen for model events.
 *
 * @version NOV-09
 * @author Alex
 */
public interface SModel {
    /**
     * Add a listener to notify when an event is raised.
     * @param l the listener to add to this SModel.
     */
    void addModelListener(ModelListener l);
    /**
     * Notify listeners by sending them a model event.
     * @param e A model event to notify the listeners about
     */
    void notifyModelListeners(ModelEvent e);
}