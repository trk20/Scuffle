package ScrabbleEvents.ModelEvents;

import Model.Player;

import java.util.List;

/**
 * PlayerChangeEvent is a ModelEvent record created when the active player,
 * the one currently playing in the model, changes.
 *
 * @param players The list of players playing the game
 * @author Alex
 * @version NOV-18
 */
// TODO: Serves very similar purpose as NewPlayerEvent;
//   Need to refactor something else before removing this
public record PlayerChangeEvent(List<Player> players) implements ModelEvent {}
