package ScrabbleEvents.ModelEvents;

import Model.Board.Direction;
import Model.Tile;

import java.awt.Point;
import java.util.List;

/**
 * BoardPlaceEvent is a ModelEvent record created when the model attempts to place on the board.
 *
 * @param placedTiles the tiles to be placed in the board
 * @param wordOrigin  the initial placement location in the board
 * @param direction   the direction of the tiles after the origin
 *
 * @author Alex
 * @version NOV-18
 */
public record BoardPlaceEvent(List<Tile> placedTiles, Point wordOrigin, Direction direction) implements ModelEvent {}
