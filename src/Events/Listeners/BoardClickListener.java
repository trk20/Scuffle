package Events.Listeners;

import Events.ControllerEvents.BoardClickEvent;
import Events.ControllerEvents.ControllerEvent;

public interface BoardClickListener extends SControllerListener{

    default void handleControllerEvent(ControllerEvent e) {
        if(e instanceof BoardClickEvent){
            handleBoardClickEvent((BoardClickEvent) e);
        }
    }

    void handleBoardClickEvent(BoardClickEvent e);

}
