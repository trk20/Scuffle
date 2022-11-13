package Events;

import Controllers.SController;
import Controllers.TurnActionController;

/**
 * Concrete event class for controller events causing a discard.
 * Sent to SControllerListeners.
 *
 * @version NOV-13
 * @author Alex
 */
public class DiscardClickEvent extends TurnActionEvent {
    /**
     * Constructs a TurnActionEvent object with the specified controller as the source.
     *
     * @param controller the controller where the event originated
     */
    public DiscardClickEvent(SController controller) {
        super(controller);
    }
}
