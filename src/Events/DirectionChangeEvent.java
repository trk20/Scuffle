package Events;

import Controllers.SController;
import Controllers.TurnActionController;
import Model.Board;

/**
 * Base event class for any event originating from an SController.
 * Sent to SControllerListeners.
 *
 * @version NOV-11
 * @author Alex
 */
public class DirectionChangeEvent extends ControllerEvent{
    /**
     * Constructs a ControllerEvent object with the specified controller as the source.
     *
     * @param controller the controller where the event originated
     */
    public DirectionChangeEvent(SController controller) {
        super(controller);
    }

    /**
     * Get new direction from source
     * @return source controller
     */
    public Board.Direction getDir(){
        if(getController() instanceof TurnActionController actionController)
            return actionController.getDir();
        return null; // Should not happen. No error catching yet
    }
}
