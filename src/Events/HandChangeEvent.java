package Events;

import Model.Hand;
import Model.ScrabbleModel;

/**
 * HandChangeEvent is a base Event class for any event
 * relating to a model change in a Hand.
 *
 * @version NOV-11
 * @author Alex
 */
public abstract class HandChangeEvent extends ModelEvent{
    /**
     * Constructs a HandEvent object with the model as a source object.
     *
     * @param model the model where the event originated
     */
    public HandChangeEvent(ScrabbleModel model) {
        super(model);
    }
}
