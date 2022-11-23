package ScrabbleEvents.Listeners;

import ScrabbleEvents.ControllerEvents.C_BoardClickEvent;
import ScrabbleEvents.ControllerEvents.ControllerEvent;

/**
 * Interface to label board click listeners, handles C_BoardClickEvents
 *
 * @author Alex
 * @version NOV-18
 */
public interface BoardClickListener extends SControllerListener{

    default void handleControllerEvent(ControllerEvent e) {
        if(e instanceof C_BoardClickEvent){
            handleBoardClickEvent((C_BoardClickEvent) e);
        }
    }

    void handleBoardClickEvent(C_BoardClickEvent e);

}
