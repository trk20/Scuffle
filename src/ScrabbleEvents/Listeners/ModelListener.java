package ScrabbleEvents.Listeners;

import ScrabbleEvents.ModelEvents.ModelEvent;

import java.io.Serializable;

/**
 * ModelListener interface, handles ModelEvent.
 *
 * @version NOV-11
 * @author Alex
 */
public interface ModelListener extends Serializable {
    void handleModelEvent(ModelEvent e);
}
