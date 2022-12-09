package ScrabbleEvents.ControllerEvents;

import java.io.File;

public record C_LoadEvent(File fileLocation) implements ControllerEvent{ }
