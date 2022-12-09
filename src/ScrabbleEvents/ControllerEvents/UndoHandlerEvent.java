package ScrabbleEvents.ControllerEvents;

import Model.Board;
import Model.Player;
import ScrabbleEvents.ModelEvents.ModelEvent;

import java.util.List;

public record UndoHandlerEvent(List<Player> players, Board board) implements ModelEvent {}

