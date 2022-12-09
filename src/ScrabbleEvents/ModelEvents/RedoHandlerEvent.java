package ScrabbleEvents.ModelEvents;

import Model.Board;
import Model.Player;

import java.util.List;

/**
 * Event for adding to the redo stack
 * @param players
 * @param board
 *
 * @Author: Kieran
 */

public record RedoHandlerEvent(List<Player> players, Board board) implements ModelEvent {}

