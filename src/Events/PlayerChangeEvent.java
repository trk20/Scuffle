package Events;

import Model.Hand;
import Model.Player;
import Model.ScrabbleModel;

import java.util.ArrayList;

public class PlayerChangeEvent extends ModelEvent{
    /**
     * Constructs a ModelEvent object with the model as a source object.
     * @author Vladimir Kovacina
     * @param model the model where the event originated
     */
    public PlayerChangeEvent(ScrabbleModel model) {
        super(model);
    }

    /**
     * Get the new players information
     * @author Vladimir Kovacina
     * @return ArrayList of players
     */
    public ArrayList<Player> getPlayers(){
        return getModel().getPlayers();
    }
}
