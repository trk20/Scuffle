package Events;

import Model.Hand;
import Model.ScrabbleModel;

/**
 * HandChangeEvent is an Event class for any event
 * relating to a model change in a Hand.
 *
 * @version NOV-11
 * @author Alex
 */
public class HandChangeEvent extends ModelEvent{
    /**
     * Constructs a HandEvent object with the model as a source object.
     *
     * @param model the model where the event originated
     */
    public HandChangeEvent(ScrabbleModel model) {
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
