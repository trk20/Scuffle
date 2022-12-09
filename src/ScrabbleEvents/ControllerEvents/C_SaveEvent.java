package ScrabbleEvents.ControllerEvents;

import java.io.File;

public record C_SaveEvent(File fileLocation) implements ControllerEvent { }
