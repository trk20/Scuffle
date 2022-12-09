package ScrabbleEvents.ModelEvents;

import Model.Board;
import Model.Player;

import java.util.List;

/**
 * Event for clearing the redo stack
 */

public record RedoHandlerClearEvent() implements ModelEvent {}
