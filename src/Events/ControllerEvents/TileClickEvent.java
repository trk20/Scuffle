package Events.ControllerEvents;

import Events.ControllerEvents.ControllerEvent;
import Model.Tile;

/**
 * TileClickEvent is an Event class raised when
 * a HandTileController is clicked on.
 *
 * @version NOV-17
 * @author Alex
 */
public class TileClickEvent extends ControllerEvent {

    private final Tile tile;

    /**
     * Constructs a TileClickEvent object with the specified controller as the source,
     * and the tile associated to the controller.
     *
     * @param tile the tile which was clicked
     */
    public TileClickEvent(Tile tile) {
        this.tile = tile;
    }

    /**
     * Returns the tile clicked.
     * @return the tile clicked (which triggered the event)
     */
    public Tile getTile() {
        return tile;
    }

}
