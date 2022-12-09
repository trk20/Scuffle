package ScrabbleEvents.ModelEvents;

import Model.Board;
import Model.Player;

import java.util.List;

/**
 * Event for adding to the undo stack
 * @param players
 * @param board
 */
public record UndoHandlerEvent(List<Player> players, Board board) implements ModelEvent {}
