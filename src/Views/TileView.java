package Views;

import Controllers.HandTileController;
import Model.Tile;

public class TileView extends HandTileController {
    /**
     * TileView constructor, initializes fields.
     *
     * @param tile The tile to view
     */
    TileView(Tile tile){
        super(tile);
    }

    /**
     * Flips the selection of the tile in the model.
     */
    @Override
    protected void flipSelection() {
        // TODO
    }

    /**
     * Highlights the tile in the view (lower prio)
     */
    @Override
    protected void highlight() {
        // TODO (low prio)
    }

    /**
     * Undo highlighting for the tile in the view (lower prio)
     */
    @Override
    protected void undoHighlight() {
        // TODO (low prio)
    }
}
