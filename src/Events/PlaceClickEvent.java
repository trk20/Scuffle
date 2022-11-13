package Events;

import Controllers.SController;
import Model.Board;

import java.awt.*;

/**
 * Concrete class indicating a placement attempt in the board by the controllers.
 * Has info on board initial location, and placement direction.
 */
public class PlaceClickEvent extends TurnActionEvent {
    private final Board.Direction dir;
    private final Point origin;

    /**
     * Constructs a PlaceClickEvent object with the specified controller as the source.
     *
     * @param controller the controller where the event originated
     */
    public PlaceClickEvent(SController controller, Board.Direction dir, Point origin) {
        super(controller);
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
