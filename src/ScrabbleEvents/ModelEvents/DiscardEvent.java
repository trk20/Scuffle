package ScrabbleEvents.ModelEvents;

import Model.Tile;

import java.util.List;

public record DiscardEvent (List<Tile> used) implements ModelEvent {}
