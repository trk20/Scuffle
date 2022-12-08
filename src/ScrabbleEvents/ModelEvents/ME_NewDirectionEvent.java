package ScrabbleEvents.ModelEvents;

import Model.Board;

public record ME_NewDirectionEvent(Board.Direction dir) implements ModelEvent { }
