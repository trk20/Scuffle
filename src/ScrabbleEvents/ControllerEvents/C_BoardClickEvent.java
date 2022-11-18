package ScrabbleEvents.ControllerEvents;

import java.awt.Point;

/**
 * C_BoardClickEvent indicates the selection (click) of a point in a board controller.
 *
 * @param origin The point on the board that was selected (clicked)
 * @version NOV-18
 * @author Alex
 */
public record C_BoardClickEvent(Point origin) implements ControllerEvent {}
