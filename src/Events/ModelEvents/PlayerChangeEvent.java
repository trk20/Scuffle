package Events.ModelEvents;

import Events.ModelEvents.ModelEvent;
import Model.Player;

import java.util.List;

public class PlayerChangeEvent extends ModelEvent {
    private final List<Player> players;

    /**
     * Constructs a ModelEvent object with the model as a source object.
     *
     * @param players the model where the event originated
     */
    public PlayerChangeEvent(List<Player> players) {
        this.players = players;
    }

    /**
     * Get the new players information
     * @return ArrayList of players
     */
    public List<Player> getPlayers(){
        return players;
    }
}
