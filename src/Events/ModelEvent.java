package Events;

import Model.ScrabbleModel;

/**
 * Base event class for any event originating from the model.
 * Mostly sent to views, but possibly to controllers as well.
 *
 * @version NOV-11
 * @author Alex
 */
public abstract class ModelEvent extends ScrabbleEvent{
    /**
     * Constructs a ModelEvent object with the model as a source object.
     *
     * @param model the model where the event originated
     */
    public ModelEvent(ScrabbleModel model) {
        super(model);
    }

    /**
     * Get model reference for model Event children (encapsulation)
     * @return model where event originated.
     */
    protected ScrabbleModel getModel(){
        return (ScrabbleModel) getSource();
    }
}
