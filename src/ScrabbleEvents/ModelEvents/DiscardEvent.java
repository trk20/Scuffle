package ScrabbleEvents.ModelEvents;

import Model.Tile;

import java.util.List;

/**
 * BoardPlaceEvent is a ModelEvent record created when tiles are discarded.
 *
 * @param used the tiles to be discarded
 * @author Alex
 * @version NOV-22
 */
public record DiscardEvent (List<Tile> used) implements ModelEvent {}
