package Model;
import ScrabbleEvents.Listeners.BoardChangeListener;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.BoardChangeEvent;
import ScrabbleEvents.ModelEvents.ModelEvent;
import ScrabbleEvents.ModelEvents.PlayerChangeEvent;

import java.util.*;

/**
 * Class to handle managing the undo functionality of scrabble
 */
public class UndoHandler implements ModelListener{
    private Stack<Board> boardState;
    private Stack<List<Player>> playerState;

    public UndoHandler() {
        boardState = new Stack<>();
        playerState = new Stack<>();
    }

    @Override
    public void handleModelEvent(ModelEvent e){
        if (e instanceof PlayerChangeEvent newPlayers){
            System.out.println("Model event");
            playerState.push(newPlayers.players());
        }else if (e instanceof  BoardChangeEvent newBoard){
            System.out.println("Board event");
            boardState.push(newBoard.board());
        }
    }

    public Board getPreviousBoard(){
        return boardState.pop();
    }


    public List<Player> getPreviousPlayerState(){
        return playerState.pop();
    }
}
