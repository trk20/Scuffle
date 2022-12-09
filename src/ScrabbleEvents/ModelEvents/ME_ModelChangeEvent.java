package ScrabbleEvents.ModelEvents;

import Model.ScrabbleModel;

/**
 * ME_ModelChangeEvent indicates a change in the model object reference.
 * @param newModel The current model
 * @version DEC-09
 * @author Alex
 */
public record ME_ModelChangeEvent(ScrabbleModel newModel) implements ModelEvent{}
