package Events.ModelEvents;

import Model.Board;

public class BoardChangeEvent extends ModelEvent {
    private final Board board;

    /**
     * Constructs a BoardChangeEvent object with the board as a source object.
     *
     * @param board the board which changed
     */
    public BoardChangeEvent(Board board) {
        this.board = board;
    }

    /**
     * Get the model's board to see its changes.
     * @return board held by the model
     */
    public Board getBoard(){
        return board;
    }
}
