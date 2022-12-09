package Model;

import ScrabbleEvents.ModelEvents.ModelEvent;
import ScrabbleEvents.ModelEvents.RedoHandlerEvent;

import java.util.ArrayList;

public class RedoHandler extends ActionHandler {
    public RedoHandler(){
        super();
    }

    @Override
    public void handleModelEvent(ModelEvent e) {
        if (e instanceof RedoHandlerEvent event){
            addToStack(event.players(), event.board());
        }
    }
}
