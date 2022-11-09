package Events;

import Controllers.SController;

/**
 * Base event class for any event originating from an SController.
 * Sent to SControllerListeners.
 *
 * @version NOV-11
 * @author Alex
 */
public abstract class ControllerEvent extends ScrabbleEvent{
    /**
     * Constructs a ControllerEvent object with the specified controller as the source.
     *
     * @param controller the controller where the event originated
     */
    public ControllerEvent(SController controller) {
        super(controller);
    }

    /**
     * Get reference to source controller.
     * Specific subclasses make use of this to return specific controller info.
     * @return source controller
     */
    protected SController getController(){
        return (SController) getSource();
    }
}
