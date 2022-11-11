package Model;

import Events.*;
import Events.Listeners.ModelListener;
import Events.Listeners.SControllerListener;
import Views.ScrabbleFrame;
import Views.TileView;
import Events.ControllerEvent;
import Events.ModelEvent;
import Events.NewPlayerHandEvent;
import Events.TileClickEvent;
import Views.OptionPaneHandler;
import java.util.*;

/**
 * Class that controls/models the overall scrabble game.
 * For Milestone 1, also acts as a text "view".
 *
 * @author Kieran Rourke, Alex
 * @version NOV-11
 */
public class ScrabbleModel implements SControllerListener, SModel{
    /** Max players limited by the four racks (see README setup rules) */
    public static final int MAX_PLAYERS = 4;
    /** Min players, should be 2 but 1 could work if we want to allow solo play */
    public static final int MIN_PLAYERS = 1;
    public static final Boolean DISCARD = false;
    public static final Boolean PLACE = true;
    private static final int BOARD_SIZE = 15;

    // Model components
    final private Board board;
    private ArrayList<Player> players;
    /** Model's shared DrawPile */
    private final DrawPile drawPile;

    final private OptionPaneHandler inputHandler; // FIXME: Should be refactored out of the model eventually

    /** Player whose turn it is to play */
    private int numPlayers;
    private int turn = 0;
    /** False until something triggers a game to end (Model.Player out of Letters, or no more possible moves)*/
    boolean gameFinished; // FIXME: may become a controller signal in the future, with no need for a field
    /** Model listeners to notify on model change */
    List<ModelListener> modelListeners;
    /** list of selected tiles (in order) to pass to the board when placing*/
    List<Tile> selectedTiles;
    Player currentPlayer;

    private ScrabbleFrame mainFrame;

    public ScrabbleModel() {
        this.board = new Board(BOARD_SIZE, BOARD_SIZE);
        this.inputHandler = new OptionPaneHandler();
        this.drawPile = new DrawPile();
        this.gameFinished = false;
        this.modelListeners = new ArrayList<>();
        this.selectedTiles = new ArrayList<>();
    }

    /**
     * Increments turn, rolling back to the first turn after passing the last player.
     */
    private void incrementTurn(){
        turn = turn == numPlayers-1 ? 0 : turn+1;
    }

    /**
     * Prints Model.Board
     */
    @Deprecated // View duty
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
    @Deprecated
    private int getYCoord(String coords){
        if(coords.length() == 3){
             return Character.isLetter(coords.charAt(0)) ? Integer.parseInt(coords.substring(1, 2))
                     : Integer.parseInt(coords.substring(0, 1));
        }
        return Character.isLetter(coords.charAt(0)) ? Integer.parseInt(String.valueOf(coords.charAt(1)))
                : Integer.parseInt(String.valueOf(coords.charAt(0)));
    }

    @Deprecated
    private int getXCoord(String coords){
        if (coords.length() == 3){
             return Character.isLetter(coords.charAt(0)) ? (int) Character.toUpperCase(coords.charAt(0)) - 65
                     : (int) Character.toUpperCase(coords.charAt(2)) - 65;
        }
        return Character.isLetter(coords.charAt(0)) ? (int) Character.toUpperCase(coords.charAt(0)) - 65
                : (int) Character.toUpperCase(coords.charAt(1)) - 65;
    }

    @Deprecated
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

        // FIXME: Have not gone through and refactored board to use tiles yet,
        //  so a conversion Letter->Tile is needed here for now
        List<Tile> tileList = new ArrayList<>();
        for (Letter l: word) {
            tileList.add(new Tile(l));
        }

        if (!p.containsTiles(tileList)){
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

    public void skipTurn(){
        nextTurn();
    }

    public void discardHand() {
        handleDiscard();
    }

    /**
     * Handles the user wanting to discard letters
     * @author Kieran, Alexandre
     */
    private void handleDiscard(){
        nextTurn();
        // TODO to be implemented later
        ;
    }

    public void placeHand(){
        handlePlace();
    }

    /**
     * Handles the user wanting to place letters
     */
    private void handlePlace(){
        nextTurn();
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
        mainFrame = new ScrabbleFrame(this);

        nextTurn();
//        System.out.println("Game ended, END SCREEN UNIMPLEMENTED");
    }

    /**
     * Handles running a turn, will be called in a loop until the game is over
     */
    private void nextTurn(){
        incrementTurn();
        currentPlayer = players.get(turn);
        // Update views to show current player
        notifyModelListeners(new NewPlayerHandEvent(this));

    }

    /**
     * Getter for model drawPile.
     * @author Alexandre marques - 101189743
     * @return model's shared Model.DrawPile
     */
    public DrawPile getDrawPile() {
        return drawPile;
    }

    /**
     * Getter for player currently playing
     * @return player whose turn it is to play
     */
    public Player getCurPlayer() {
        return players.get(turn);
    }

    /**
     * Getter for hand of the player currently playing
     * @return hand of the player whose turn it is to play
     */
    public Hand getCurHand() {
        return getCurPlayer().getHand();
    }

    /**
     * Flip selection of the tile relating to the tile click event.
     * @param tc Tile selection event
     */
    private void flipTileSelect(TileClickEvent tc) {
        Tile t = tc.getTile();
        // If tile is not in selection list yet, it becomes selected
        final boolean selectedAfterClick = !(selectedTiles.contains(t));
        // Add or remove from selection list depending on new desired state
        if(selectedAfterClick){
            selectedTiles.add(t);
        } else {
            selectedTiles.remove(t);
        }
        // Model changed, notify listeners of new state:
        notifyModelListeners(new TileSelectEvent(this, t, selectedAfterClick));
    }

    /**
     * Process Controller events when one is raised.
     *
     * @param e the event to process
     */
    @Override
    public void handleControllerEvent(ControllerEvent e) {
        if(e instanceof TileClickEvent tc) flipTileSelect(tc);
    }

    /**
     * Add a listener to notify when an event is raised.
     *
     * @param l the listener to add to this SModel.
     */
    @Override
    public void addModelListener(ModelListener l) {
        this.modelListeners.add(l);
        System.out.println("New listener: "+l);
    }

    /**
     * Notify listeners by sending them a model event.
     * @param e A model event to notify the listeners about
     */
    @Override
    public void notifyModelListeners(ModelEvent e) {
        for (ModelListener l: modelListeners) {
            l.handleModelEvent(e);
        }
    }

    public static void main(String[] args){
        ScrabbleModel s = new ScrabbleModel();
        s.startGame();
    }
}
