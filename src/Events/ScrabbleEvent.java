package Events;
import java.awt.AWTEvent;

/**
 * Base event class for any defined scrabble event.
 * Does not serve much purpose by itself except for type indication.
 *
 * @version NOV-11
 * @author Alex
 */
public abstract class ScrabbleEvent extends AWTEvent {

    /**
     * Constructs a ScrabbleEvent object with the specified source object.
     *
     * @param source the object where the event originated
     */
    public ScrabbleEvent(Object source) {
        super(source, 0);
        // Not 100% sure what the id is for, so we're not using it for now.
    }
}
