package Events.Listeners;

import Events.ModelEvents.HandChangeEvent;
import Events.ModelEvents.ModelEvent;

/**
 * HandChangeListener interface handles HandChangeEvents.
 *
 * @version NOV-11
 * @author Alex
 */
public interface HandChangeListener extends ModelListener{
    // Delegate to Hand change event
    default void handleModelEvent(ModelEvent e){
        // Only handle event if it's a hand change event
        if (e instanceof HandChangeEvent ch)
            handleHandChangeEvent(ch);
    }

    void handleHandChangeEvent(HandChangeEvent e);
}
