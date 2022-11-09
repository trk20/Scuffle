package Controllers;

import Events.Listeners.SControllerListener;

/**
 * Interface for Scrabble Controllers (SController).
 * Allows controller listeners (SControllerListener) to listen for controller events.
 *
 * @version 11-NOV
 * @author Alex
 */
public interface SController {
    /**
     * Add a listener to notify when an event is raised.
     * @param l the listener to add to this SController.
     */
    void addControllerListener(SControllerListener l);
    /**
     * Notify listeners by sending them a controller event.
     */
    void notifyControllerListeners();
}
