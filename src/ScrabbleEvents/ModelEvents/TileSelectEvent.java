package ScrabbleEvents.ModelEvents;

import Model.Tile;

/**
 * TileSelectEvent is created when a tile has been selected in the model.
 *
 * @param tile      The tile reference in the model where the event occur
 * @param selected  true if the tile got selected, false if it got unselected.
 *
 * @version NOV-18
 * @author Alex
 */
public record TileSelectEvent(Tile tile, boolean selected) implements HandChangeEvent {}