package ScrabbleEvents.ModelEvents;

import Model.Board;

/**
 * ME_NewDirectionEvent indicates a change in placement direction.
 * @param dir The new direction in the model
 * @version DEC-09
 * @author Alex
 */
public record ME_NewDirectionEvent(Board.Direction dir) implements ModelEvent { }
