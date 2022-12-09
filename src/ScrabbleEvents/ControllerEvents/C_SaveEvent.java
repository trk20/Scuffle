package ScrabbleEvents.ControllerEvents;

import java.io.File;

/**
 * C_SaveEvent indicates a command to save a game state.
 * @param fileLocation The file that will contain the saved game state (serialized)
 * @version DEC-09
 * @author Alex
 */
public record C_SaveEvent(File fileLocation) implements ControllerEvent { }
