package Model;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.ModelEvent;
import ScrabbleEvents.ModelEvents.UndoHandlerEvent;


/**
 * Class to handle managing the undo functionality of scrabble
 *
 *
 * @Author: Kieran
 */
public class UndoHandler extends ActionHandler implements ModelListener{

    public UndoHandler() {
        super();
    }

    @Override
    public void handleModelEvent(ModelEvent e) {
         if (e instanceof UndoHandlerEvent event) {
             addToStack(event.players(), event.board());
         }
    }
}
