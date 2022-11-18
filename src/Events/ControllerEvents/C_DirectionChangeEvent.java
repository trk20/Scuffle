package Events.ControllerEvents;

import Model.Board;

/**
 * Base event class for any event originating from an SController.
 * Sent to SControllerListeners.
 *
 * @version NOV-11
 * @author Alex
 */
public class C_DirectionChangeEvent extends ControllerEvent {
    private final Board.Direction dir;

    /**
     * Constructs a ControllerEvent object with the specified controller as the source.
     *
     * @param newDir the direction after the change
     */
    public C_DirectionChangeEvent(Board.Direction newDir) {
        dir = newDir;
    }

    /**
     * Get new direction from event data
     * @return new direction after change
     */
    public Board.Direction getDir(){
        return dir;
    }
}
