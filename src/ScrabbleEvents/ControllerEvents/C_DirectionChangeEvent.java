package ScrabbleEvents.ControllerEvents;

import Model.Board.Direction;

/**
 * C_DirectionChangeEvent indicates a command to change directions from a controller.
 * Sent to SControllerListeners.
 *
 * @param dir The new direction after the change
 * @version NOV-18
 * @author Alex
 */
public record C_DirectionChangeEvent(Direction dir) implements ControllerEvent {}
