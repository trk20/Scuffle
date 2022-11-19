package ScrabbleEvents.Listeners;

import ScrabbleEvents.ModelEvents.ModelEvent;

/**
 * ModelListener interface, handles ModelEvent.
 *
 * @version NOV-11
 * @author Alex
 */
public interface ModelListener {
    void handleModelEvent(ModelEvent e);
}
