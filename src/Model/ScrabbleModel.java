package Model;

import ScrabbleEvents.ControllerEvents.*;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.Listeners.SControllerListener;
import ScrabbleEvents.ModelEvents.*;
import Views.ScrabbleFrame;

import java.awt.*;
import java.util.*;
import java.util.List;

/**
 * Class that controls/models the overall scrabble game.
 * For Milestone 1, also acts as a text "view".
 *
 * @author Kieran Rourke, Alex
 * @version NOV-12
 */
public class ScrabbleModel implements SControllerListener, SModel, ModelListener{
    /** Max players limited by the four racks (see README setup rules) */
    public static final int MAX_PLAYERS = 4;
    /** Min players, should be 2 but 1 could work if we want to allow solo play */
    public static final int MIN_PLAYERS = 1;
    //    public static final Boolean DISCARD = false; FIXME: outdated, should not be using bool to indicate function.
//    public static final Boolean PLACE = true;
    public static final int BOARD_SIZE = 15;

    // Model components
    final private Board board;
    private ArrayList<Player> players;
    /** Model's shared DrawPile */
    private final DrawPile drawPile;

    /** Player whose turn it is to play */
    private int numPlayers;
    private int turn = 0;
    /** False until something triggers a game to end (Model.Player out of Letters, or no more possible moves)*/
    boolean gameFinished; // FIXME: may become a controller signal in the future, with no need for a field
    /** Model listeners to notify on model change */
    List<ModelListener> modelListeners;
    /** list of selected tiles (in order) to pass to the board when placing*/
    List<Tile> selectedTiles;


    private ScrabbleFrame mainFrame;

    public ScrabbleModel(List<String> playerNames) {
        this.board = new Board(false);
        this.drawPile = new DrawPile();
        this.gameFinished = false;
        this.modelListeners = new ArrayList<>();
        this.selectedTiles = new ArrayList<>();
        this.players = new ArrayList<>();
        this.turn = 0;
        this.numPlayers = 0; // In case of null players
        // Guard against null human players
        if(playerNames != null){
            this.numPlayers = playerNames.size();
            initializePlayers(playerNames);
        }
    }

    public ScrabbleModel(List<String> playerNames, int numAI){
        this(playerNames);
        initializeAI(numAI);
    }

    private void initializeAI(int numAI) {
        // TODO: implement
        return;
    }

    /**
     * Increments turn, rolling back to the first turn after passing the last player.
     */
    private void incrementTurn(){
        turn = turn == numPlayers -1 ? 0 : turn+1;
    }

    /**
     * Prints Model.Board
     */
    @Deprecated // View duty
    private void printBoard(){
        System.out.println(board);
    }

    /**
     * Creates Player models from a list of player names.
     *
     * @param names The list of names for each player in the model
     */
    private void initializePlayers(List<String> names){
        players = new ArrayList<>();
        for (String name: names) {
            players.add(new Player(name, this));
        }
    }


    public String getBoardTileText(Point p){
        return board.getBoardTile(p).toString();
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
     *  @author Kieran, Alexandre
     */
    private void handleDiscard(){
        getCurPlayer().discardTiles(selectedTiles);
        nextTurn();
    }

    /**
     * Handles the user wanting to place letters
     */
    private void handlePlace(PlaceClickEvent pce){
        BoardPlaceEvent placeEvent = new BoardPlaceEvent(selectedTiles, pce.origin(), pce.dir());
        int placementScore = board.placeWord(placeEvent);

        if(placementScore<0){
            // Display error, do nothing.
        } else {
            // Letters have been placed, get rid of them and bank the score.
            getCurPlayer().placeTiles(selectedTiles);
            getCurPlayer().addPoints(placementScore);

            // Notify listeners about new board state
            notifyModelListeners(new BoardChangeEvent(board));
            notifyModelListeners(new PlayerChangeEvent(players));
            nextTurn();
        }
    }


    /**
     * Used to end the game
     */
    public void setGameFinished() {
        this.gameFinished = true;
    }


    /**
     * Handles starting the game
     */
    // Creating a model should be synonymous to creating a game, we should move towards removing this.
    @Deprecated
    public void startGame(){
        // Do not touch the views in the model!
        // The main decides when to create views, or models. Possibly through controllers.
//        mainFrame = new ScrabbleFrame(this);


//        while(!gameFinished){
//            nextTurn();
//        }

        //Need to notify Score View here

        notifyModelListeners(new PlayerChangeEvent(players));
        notifyModelListeners(new NewPlayerEvent(getCurPlayer()));
        //nextTurn();
//        System.out.println("Game ended, END SCREEN UNIMPLEMENTED");
    }

    /**
     * Handles running a turn, will be called in a loop until the game is over
     */
    private void nextTurn(){
//        Player currentPlayer = players.get(turn);
        selectedTiles = new ArrayList<>(); // Clear selection
        // Update views to show current player
        incrementTurn();
        notifyModelListeners(new NewPlayerEvent(getCurPlayer()));
        notifyModelListeners(new PlayerChangeEvent(players));
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
        Tile t = tc.tile();
        // If tile is not in selection list yet, it becomes selected
        final boolean selectedAfterClick = !(selectedTiles.contains(t));
        // Add or remove from selection list depending on new desired state
        if(selectedAfterClick){
            selectedTiles.add(t);
        } else {
            selectedTiles.remove(t); // This uses equals!!
        }
        // Model changed, notify listeners of new state:
        notifyModelListeners(new TileSelectEvent(t, selectedAfterClick));
    }

    /**
     * Returns all the players in the game
     * @return players ArrayList
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Process Controller events when one is raised.
     *
     * @param e the event to process
     */
    @Override
    public void handleControllerEvent(ControllerEvent e) {
        // TODO: make switch, show dropped events
        if(e instanceof PlaceClickEvent pce) handlePlace(pce);
        if(e instanceof DiscardClickEvent) handleDiscard();
        if(e instanceof C_SkipEvent skip) nextTurn();
        if(e instanceof TileClickEvent tce) flipTileSelect(tce);
    }



    /**
     * Add a listener to notify when an event is raised.
     *
     * @param l the listener to add to this SModel.
     */
    @Override
    public void addModelListener(ModelListener l) {
        this.modelListeners.add(l);
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

    /**
     * Getter for board related events
     * @return Model's board
     */
    // FIXME: in the future, should inherit SModel in ModelEvents
    //  and then pass only the relevant parts in model events
    public Board getBoard() {
        return board;
    }

    @Override
    public void handleModelEvent(ModelEvent e) {

    }
}
