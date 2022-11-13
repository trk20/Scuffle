package Events;

import Model.Board;
import Model.SModel;
import Model.ScrabbleModel;

public class BoardChangeEvent extends ModelEvent {

    /**
     * Constructs a BoardChangeEvent object with the board as a source object.
     *
     * @param model the model where the event originated
     */
    public BoardChangeEvent(ScrabbleModel model) {
        super(model);
    }

    /**
     * Get the model's board to see its changes.
     * @return board held by the model
     */
    public Board getBoard(){
        return getModel().getBoard();
    }
}
