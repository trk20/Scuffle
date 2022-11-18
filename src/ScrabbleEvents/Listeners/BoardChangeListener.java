package ScrabbleEvents.Listeners;

import ScrabbleEvents.ModelEvents.BoardChangeEvent;
import ScrabbleEvents.ModelEvents.ModelEvent;

/**
 * ModelListener interface, handles BoardChangeEvents.
 *
 * @version NOV-13
 * @author Alex
 */
public interface BoardChangeListener extends ModelListener{
    // Delegate to BoardChangeEvent handler
    default void handleModelEvent(ModelEvent e){
        // Only handle event if it's a hand change event
        if (e instanceof BoardChangeEvent ch)
            handleBoardChangeEvent(ch);
    }

    void handleBoardChangeEvent(BoardChangeEvent e);
}
