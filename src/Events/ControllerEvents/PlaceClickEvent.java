package Events.ControllerEvents;

import Events.ControllerEvents.TurnActionEvent;
import Model.Board;

import java.awt.*;

/**
 * Concrete class indicating a placement attempt in the board by the controllers.
 * Has info on board initial location, and placement direction.
 *
 * @author Alex
 * @version NOV-17
 */
public class PlaceClickEvent extends TurnActionEvent {
    private final Board.Direction dir;
    private final Point origin;

    /**
     * Constructs a PlaceClickEvent object with the specified controller as the source.
     *
     * @param dir the direction to place the word in
     * @param origin the point where the word starts
     */
    public PlaceClickEvent(Board.Direction dir, Point origin) {
        this.dir = dir;
        this.origin = origin;
    }

    public Point getOrigin() {
        return origin;
    }

    public Board.Direction getDir() {
        return dir;
    }
}
