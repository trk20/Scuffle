package ScrabbleEvents.ControllerEvents;

import java.io.File;

/**
 * C_LoadEvent indicates a command to load a game state.
 * @param fileLocation The file containing the saved game state (serialized)
 * @version DEC-09
 * @author Alex
 */
public record C_LoadEvent(File fileLocation) implements ControllerEvent{ }
