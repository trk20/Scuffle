package ScrabbleEvents.ModelEvents;

import Model.Board;

/**
 * BoardChangeEvent is a ModelEvent record created when the model has changed the board.
 *
 * @param board a reference to the board that changed
 *
 * @author Alex
 * @version NOV-18
 */
public record BoardChangeEvent(Board board) implements ModelEvent {}
