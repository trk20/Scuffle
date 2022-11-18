package Events.Listeners;

import Events.ControllerEvents.ControllerEvent;
import Events.ControllerEvents.C_DirectionChangeEvent;

/**
 * Interface to label placement direction change listeners(SControllerListener).
 *
 *
 * @version 11-NOV
 * @author Alex
 */
public interface DirectionChangeListener extends SControllerListener {
    /**
     * Process Controller events when one is raised.
     * Only acts on directionChangeEvent (forwards call to other handler)
     * @param e the event to process
     */
    default void handleControllerEvent(ControllerEvent e) {
        if(e instanceof C_DirectionChangeEvent dce)
            handleDirectionChangeEvent(dce);
    }

    /**
     * Process direction change event when one is raised.
     * @param e the event to process
     */
    void handleDirectionChangeEvent(C_DirectionChangeEvent e);
}
