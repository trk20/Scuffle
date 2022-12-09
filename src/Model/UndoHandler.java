package Model;
import ScrabbleEvents.ControllerEvents.UndoHandlerEvent;
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
    public void handleModelEvent(ModelEvent e) {
         if (e instanceof UndoHandlerEvent event){
             ArrayList<Player> playerCopy = new ArrayList<Player>();
             for(Player p : event.players()){
                 playerCopy.add((Player) p.clone());
             }
             playerState.push(playerCopy);
             boardState.push(event.board().clone());
//             System.out.println(playerState.peek().get(0));
             System.out.println(event.players().get(0));
        }
    }

    public Board getPreviousBoard(){
        return boardState.pop();
    }


    public List<Player> getPreviousPlayerState(){
        return playerState.pop();
    }

    public boolean isStackEmpty(){
        System.out.println("test " + boardState.size() + playerState.size());
        return boardState.size() == 0 || playerState.size() == 0;
    }
}
