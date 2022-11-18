package ScrabbleEvents.ModelEvents;

import Model.Player;

/**
 * NewPlayerEvent is a ModelEvent record created when the active player,
 * the one currently playing in the model, changes.
 *
 * @param player The player now playing in the model
 * @author Alex
 * @version NOV-18
 */
public record NewPlayerEvent(Player player) implements HandChangeEvent {}
