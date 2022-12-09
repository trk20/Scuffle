package Model;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.AIPlayingEvent;
import ScrabbleEvents.ModelEvents.ModelEvent;
import ScrabbleEvents.ModelEvents.UndoHandlerEvent;

import java.util.*;

/**
 * Abstract class to handle managing the undo/redo functionality of scrabble
 *
 * @Author: Kieran
 */
public abstract class ActionHandler implements ModelListener{
    private Stack<Board> boardState;
    private Stack<List<Player>> playerState;

    public ActionHandler() {
        this.boardState = new Stack<>();
        this.playerState = new Stack<>();
    }

    @Override
    public abstract void handleModelEvent(ModelEvent e);

    /**
     * Handles adding the player and board to stack once the event has been handled
     * @param players, Current Players
     * @param board, Current Board
     */
    protected void addToStack(List<Player> players, Board board){
        ArrayList<Player> playerCopy = new ArrayList<Player>();
        for(Player p : players){
            playerCopy.add((Player) p.clone());
        }
        playerState.push(playerCopy);
        boardState.push(board.clone());
    }

    /**
     * Gets the most recent board state from the stack
     * @return: Recent Board
     */
    public Board getPreviousBoard(){
        return boardState.pop();
    }

    /**
     * Gets the most recent player state from the stack
     * @return: Recent Players
     */
    public List<Player> getPreviousPlayerState(){
        return playerState.pop();
    }

    /**
     * Checks if the stack is empty
     * @return: Boolean
     */
    public boolean isStackEmpty(){
        return boardState.empty() || playerState.empty();
    }

    public void clearStack(){
        boardState.clear();
        playerState.clear();
    }
}
