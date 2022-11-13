package Events;

import Controllers.HandTileController;
import Controllers.SController;
import Model.Letter;
import Model.Tile;

/**
 * TileClickEvent is an Event class raised when
 * a HandTileController is clicked on.
 *
 * @version NOV-11
 * @author Alex
 */
public class TileClickEvent extends ControllerEvent{

    /**
     * Constructs a TileClickEvent object with the specified controller as the source,
     * and the tile associated to the controller.
     *
     * @param controller the controller where the event originated
     */
    public TileClickEvent(SController controller) {
        super(controller);
    }

    /**
     * Returns the letter associated to the tile.
     * Note: representation type might change due to Letter limitations later.
     * @return returns the letter associated to the tile
     */
    public Tile getTile() {
        HandTileController c = (HandTileController) getController();
        return c.getTile();
    }

}
