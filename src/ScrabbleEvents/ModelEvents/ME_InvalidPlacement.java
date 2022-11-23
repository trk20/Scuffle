package ScrabbleEvents.ModelEvents;

import Model.BoardValidator;

/**
 * ME_InvalidPlacement is a ModelEvent record created when an invalid placement is attempted.
 *
 * @param status the validation status for the invalid placement
 * @author Alex
 * @version NOV-22
 */
public record ME_InvalidPlacement(BoardValidator.Status status) implements ModelEvent{}
