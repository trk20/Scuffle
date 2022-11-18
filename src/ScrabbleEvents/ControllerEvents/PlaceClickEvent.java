package ScrabbleEvents.ControllerEvents;

import Model.Board;

import java.awt.Point;

/**
 * PlaceClickEvent is a ScrabbleEvent record indicating a placement attempt in the board by the controllers.
 *
 * @param dir    the direction to place the word in
 * @param origin the point where the word starts in the board
 * @author Alex
 * @version NOV-18
 */
public record PlaceClickEvent(Board.Direction dir, Point origin) implements TurnActionEvent {}
