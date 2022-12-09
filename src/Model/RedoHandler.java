package Model;

import ScrabbleEvents.ModelEvents.ModelEvent;
import ScrabbleEvents.ModelEvents.RedoHandlerClearEvent;
import ScrabbleEvents.ModelEvents.RedoHandlerEvent;

/**
 * Class for handling Redo action
 *
 * @Author: Kieran
 */
public class RedoHandler extends ActionHandler {
    public RedoHandler(){
        super();
    }

    @Override
    public void handleModelEvent(ModelEvent e) {
        if (e instanceof RedoHandlerEvent event) {
            addToStack(event.players(), event.board());
        }else if (e instanceof RedoHandlerClearEvent){
            clearStack();
        }
    }
}
