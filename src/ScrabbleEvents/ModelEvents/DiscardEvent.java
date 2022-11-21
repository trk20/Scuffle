package ScrabbleEvents.ModelEvents;

import Model.Player;
import Model.Tile;

import java.util.List;

public record DiscardEvent (Player player, List<Tile> used) implements ModelEvent {}
