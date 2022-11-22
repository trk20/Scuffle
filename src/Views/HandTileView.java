package Views;

import Controllers.HandTileController;
import Model.Letter;
import Model.SModel;
import Model.Tile;

import java.awt.*;

/**
 * HandView is responsible for displaying information about individual Tile objects.
 * Displays Tile's letters. Also implements a mouse based controller to allow selecting the tiles.
 *
 * @author Alex
 * @version NOV-21
 */
public class HandTileView extends TileView{
    public static final Color TILE_BEIGE = new Color(245, 245, 220);
    private final HandTileController controller;

    /**
     * HandTileView constructor, initializes fields.
     *
     * @param tile The tile to view
     */
    public HandTileView(Tile tile, SModel model){
        styleHandTile(tile.getLetter());
        controller = new HandTileController(tile, model);
        this.addMouseListener(controller);
    }

    /**
     * Set visuals for the tile. This includes a border, and a background color,
     * There is also text indicating the letter it represents.
     */
    private void styleHandTile(Letter letter) {
        // Empirically found good size.
        setPreferredSize(new Dimension(80,80));
        // Sets the tile labels
        styleLetterTile(letter);
    }

    public HandTileController getController(){
        return this.controller;
    }
}
