package Events;

import Model.Board.Direction;
import Model.ScrabbleModel;
import Model.Tile;

import java.awt.*;
import java.util.List;

/**
 * Event sent when attempting to place a set of tiles on the board.
 * Encapsulates all information needed by board to handle a placement event.
 *
 * @version NOV-12
 * @author Alex
 */
public class BoardPlaceEvent extends ModelEvent{
    private List<Tile> placedTiles;
    private Point wordOrigin;
    private Direction direction;

    /**
     * Constructs a BoardPlaceEvent object with the model as a source object.
     *
     * @param model the model where the event originated
     *
     */
    public BoardPlaceEvent(ScrabbleModel model,
                           List<Tile> placedTiles,
                           Point wordOrigin,
                           Direction direction){
        super(model);
        this.placedTiles = placedTiles;
        this.wordOrigin = wordOrigin;
        this.direction = direction;
    }


    public List<Tile> getPlacedTiles() {
        return placedTiles;
    }

    public Point getWordOrigin() {
        return wordOrigin;
    }

    public Direction getDirection() {
        return direction;
    }
}
