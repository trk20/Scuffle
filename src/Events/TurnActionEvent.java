package Events;

import Controllers.SController;

/**
 * Base event class for any event originating relating to triggering a TurnAction.
 * Sent to SControllerListeners.
 *
 * @version NOV-13
 * @author Alex
 */
public abstract class TurnActionEvent extends ControllerEvent{
    /**
     * Constructs a TurnActionEvent object with the specified controller as the source.
     *
     * @param controller the controller where the event originated
     */
    public TurnActionEvent(SController controller) {
        super(controller);
    }
}
