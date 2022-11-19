package ScrabbleEvents.Listeners;

import ScrabbleEvents.ControllerEvents.ControllerEvent;

/**
 * Interface to label Scrabble Controller Listeners (SControllerListener).
 *
 *
 * @version 11-NOV
 * @author Alex
 */
public interface SControllerListener {
    /**
     * Process Controller events when one is raised.
     * @param e the event to process
     */
    void handleControllerEvent(ControllerEvent e);
}
