package ScrabbleEvents.ControllerEvents;

import Model.Tile;

/**
 * TileClickEvent is a ScrabbleEvent record created when a HandTileController is clicked on.
 *
 * @param tile The tile that was clicked on
 * @author Alex
 * @version NOV-18
 */
public record TileClickEvent(Tile tile) implements ControllerEvent{}
