package ScrabbleEvents.ModelEvents;

import Model.ScrabbleModel;

public record ME_ModelChangeEvent(ScrabbleModel newModel) implements ModelEvent{}
