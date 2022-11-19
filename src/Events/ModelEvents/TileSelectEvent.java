package Events.ModelEvents;

import Events.ModelEvents.HandChangeEvent;
import Model.ScrabbleModel;
import Model.Tile;

/**
 * TileSelectEvent is an Event class
 * raised when a tile gets selected (or un-selected).
 *
 * @version NOV-9
 * @author Alex
 */
public class TileSelectEvent extends HandChangeEvent {
    private final Tile tile;
    private final boolean selected;

    /**
     * Constructs a TileSelectEvent object, and initialize its fields.
     *
     * @param t         The tile reference in the model where the event occur
     * @param selected  true if the tile got selected, false if it got unselected.
     */
    public TileSelectEvent(ScrabbleModel model, Tile t, boolean selected) {
        this.tile = t;
        this.selected = selected;
    }

    /**
     * Get the tile that switched states in the model.
     *
     * @return the tile that changed selection state in the model.
     */
    public Tile getTile(){
        return tile;
    }

    /**
     * Get the new selection state of the tile.
     *
     * @return the new selection state of the tile.
     */
    public boolean getSelection(){
        return selected;
    }
}