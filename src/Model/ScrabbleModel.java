package Model;

import Events.Listeners.ModelListener;
import Events.Listeners.SControllerListener;
import Events.ControllerEvent;
import Events.ModelEvent;
import Events.TileClickEvent;

import java.util.*;

/**
 * Class that controls/models the overall scrabble game.
 * For Milestone 1, also acts as a text "view".
 *
 * @author Kieran Rourke, Alex
 * @version NOV-11
 */
public class ScrabbleModel implements SControllerListener {
    final private Board board;
    final private OptionPaneHandler inputHandler;
    private ArrayList<Player> players;
    /** Player whose turn it is to play */
    private Player currentPlayer;
    private int numPlayers;
    private final int SIZE = 15;
    private int turn = 0;
    public static final Boolean DISCARD = false;
    public static final Boolean PLACE = true;
    /** Model's shared Model.DrawPile */
    private final DrawPile drawPile;
    /** Max players limited by the four racks (see README setup rules) */
    public static final int MAX_PLAYERS = 4;
    /** Min players, should be 2 but 1 could work if we want to allow solo play */
    public static final int MIN_PLAYERS = 1;
    /** False until something triggers a game to end (Model.Player out of Letters, or no more possible moves)*/
    boolean gameFinished;
    /** Model listeners to notify on model change */
    List<ModelListener> modelListeners;

    public ScrabbleModel() {
        this.board = new Board(SIZE, SIZE);
        this.inputHandler = new OptionPaneHandler();
        this.drawPile = new DrawPile();
        this.gameFinished = false;
        this.modelListeners = new ArrayList<>();
    }

    /**
     * Increments turn
     * @param turn: current value
     * @return new value
     */
    private int incrementTurn(int turn){
        return turn == numPlayers ? 1 : ++turn;
    }

    /**
     * Prints Model.Board
     */
    private void printBoard(){
        System.out.println(board);
    }

    private void initializePlayers(){
        String name = "";

        numPlayers = inputHandler.askForNumPlayers();
        players = new ArrayList<>(numPlayers);

        for (int i = 0; i < numPlayers; i++){
            name = inputHandler.askForPlayerName(i);
            players.add(i, new Player(name, this));
        }
    }

    /**
     * Following Methods handle parsing the user coord input
     * @param coords String inputted by the user. Will be in the form 2f or f2.
     * @return The integer/boolean value from the input
     */
    private int getYCoord(String coords){
        if(coords.length() == 3){
             return Character.isLetter(coords.charAt(0)) ? Integer.parseInt(coords.substring(1, 2))
                     : Integer.parseInt(coords.substring(0, 1));
        }
        return Character.isLetter(coords.charAt(0)) ? Integer.parseInt(String.valueOf(coords.charAt(1)))
                : Integer.parseInt(String.valueOf(coords.charAt(0)));
    }

    private int getXCoord(String coords){
        if (coords.length() == 3){
             return Character.isLetter(coords.charAt(0)) ? (int) Character.toUpperCase(coords.charAt(0)) - 65
                     : (int) Character.toUpperCase(coords.charAt(2)) - 65;
        }
        return Character.isLetter(coords.charAt(0)) ? (int) Character.toUpperCase(coords.charAt(0)) - 65
                : (int) Character.toUpperCase(coords.charAt(1)) - 65;
    }

    private boolean getDirection(String coords){
        return Character.isLetter(coords.charAt(0));
    }


    /**
     * Validates User input to ensure that it can be placed on the board
     * @param coords: The coords for the placement
     * @param word: The word to be placed
     * @param p: The current player
     * @return: True/false depending on if it is a valid placement
     */
    private boolean validateInput(String coords, List<Letter> word, Player p){
        int x = getXCoord(coords);
        int y = getYCoord(coords);
        boolean direction = getDirection(coords);

        if (!p.containsLetters(word)){
            System.out.println("You do not contain the letters needed for the word");
            return false;
        }

        if(!board.boardWordsAreValid(word, x, y, direction)){
            System.out.println("Can not place word as it forms an invalid word");
            return false;
        }

        if(!board.wordInBoard(word, x, y, direction)){
            System.out.println("Coords are not valid based on current board arrangement");
            return false;
        }

        return true;
    }

    /**
     * Gets the user action either place or discard
     * @return boolean mapped to the action
     */
    private boolean getAction(){
        return true;
    }

    /**
     * Handles the user wanting to discard letters
     * @param currentPlayer The player whose turn it is
     * @author Kieran, Alexandre
     */
    private void handleDiscard(Player currentPlayer){
        // TODO to be implemented later
        ;
    }

    /**
     * Handles the user wanting to place letters
     * @param currentPlayer The player whose turn it is
     */
    private void handlePlace(Player currentPlayer){
//        try{
//            currentPlayer.placeLetters(word);
//        }catch(NullPointerException e){
//            System.out.println(e.getMessage());
//            // if player is out of letters, end the game
//            if(currentPlayer.outOfLetters()){
//                this.gameFinished = true;
//            }
//        }
//
//        board.placeWord(word, x, y, direction);
//        printBoard();
//        currentPlayer.addPoints(board.boardScore(word, x, y, direction));
        // TODO to be implemented later
        ;
    }

    /**
     * Handles starting the game
     */
    public void startGame(){
        initializePlayers();
        printBoard();
        while(!gameFinished){
            nextTurn();
        }
        System.out.println("Game ended, END SCREEN UNIMPLEMENTED");
    }

    /**
     * Handles running a turn, will be called in a loop until the game is over
     */
    private void nextTurn(){
        boolean action;

        turn = incrementTurn(turn);
        currentPlayer = players.get(turn-1);
        System.out.println("==========================");
        System.out.printf("It is Model.Player %d's turn\n", turn);
        // Print player state before turn
        System.out.println(currentPlayer);
        action = getAction();

        if(action == PLACE){
            handlePlace(currentPlayer);
        }else{
            handleDiscard(currentPlayer);
        }
        // Print player state after turn
        System.out.println(currentPlayer);
    }

    /**
     * Getter for model drawPile.
     * @author Alexandre marques - 101189743
     * @return model's shared Model.DrawPile
     */
    public DrawPile getDrawPile() {
        return drawPile;
    }
    public static void main(String[] args){
        ScrabbleModel s = new ScrabbleModel();
        s.startGame();
    }

    /**
     * Getter for player currently playing
     * @return player whose turn it is to play
     */
    public Player getCurPlayer() {
        return currentPlayer;
    }

    /**
     * Getter for hand of the player currently playing
     * @return hand of the player whose turn it is to play
     */
    public Hand getCurHand() {
        return getCurPlayer().getHand();
    }

    /**
     * Process Controller events when one is raised.
     *
     * @param e the event to process
     */
    @Override
    public void handleControllerEvent(ControllerEvent e) {
        if(e instanceof TileClickEvent tc){
            Tile tile = tc.getTile();
            // TODO: do an == to select the same memory ref... may lead to issues if we pass copies instead
        }
    }

    /**
     * Notify listeners about the model event
     *
     * @param e A model event to notify the listeners about
     */
    private void notifyModelListeners(ModelEvent e){
        for (ModelListener l: modelListeners) {
            l.handleModelEvent(e);
        }
    }
}
