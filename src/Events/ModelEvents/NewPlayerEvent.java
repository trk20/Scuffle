package Events.ModelEvents;

import Events.ModelEvents.HandChangeEvent;
import Model.Player;

/**
 * NewPlayerHandEvent is an Event class
 * raised when the hand changes to a different player's (new turn).
 *
 * @version NOV-9
 * @author Alex
 */
public class NewPlayerEvent extends HandChangeEvent {
    private final Player player;

    /**
     * Constructs a HandEvent object with the model as a source object.
     */
    public NewPlayerEvent(Player player) {
        this.player = player;
    }

    /**
     * Get the hand from the player
     * who is currently playing in the model.
     *
     * @return the hand of the current player.
     */
    public Player getPlayer(){
        return player;
    }
}
