package ScrabbleEvents.ModelEvents;

import Model.Board;
import Model.Player;

import java.util.List;

/**
 * Event for adding to the redo stack
 * @param players
 * @param board
 */

public record RedoHandlerEvent(List<Player> players, Board board) implements ModelEvent {}

