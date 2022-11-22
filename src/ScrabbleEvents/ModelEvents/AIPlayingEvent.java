package ScrabbleEvents.ModelEvents;

/**
 * AIPlayingEvent is an event indicating if an AIPlayer is currently playing or not.
 * Serves as a sort of Mutex for the GUI controllers (humans should not be able to mess with AI turns)
 *
 * @param isPlaying true if the AIPlayer is playing, false otherwise
 *
 * @author Alex
 * @version NOV-21
 */
public record AIPlayingEvent(boolean isPlaying) implements ModelEvent { }
