package Events;

import Model.Hand;
import Model.ScrabbleModel;

/**
 * NewPlayerHandEvent is an Event class
 * raised when the hand changes to a different player's (new turn).
 *
 * @version NOV-9
 * @author Alex
 */
public class NewPlayerHandEvent extends HandChangeEvent{
    /**
     * Constructs a HandEvent object with the model as a source object.
     *
     * @param model the model where the event originated
     */
    public NewPlayerHandEvent(ScrabbleModel model) {
        super(model);
    }

    /**
     * Get the hand from the player
     * who is currently playing in the model.
     *
     * @return the hand of the current player.
     */
    public Hand getHand(){
        return getModel().getCurHand();
    }
}
