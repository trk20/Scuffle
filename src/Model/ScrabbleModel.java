package Model;

import Controllers.SController;
import ScrabbleEvents.ControllerEvents.*;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.Listeners.SControllerListener;
import ScrabbleEvents.ModelEvents.*;
import Views.OptionPaneHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Views.DebugView.DEBUG_VIEW;

/**
 * Class that controls/models the overall scrabble game.
 * For Milestone 1, also acts as a text "view".
 *
 * @author Kieran Rourke, Alex
 * @version NOV-21
 */
public class ScrabbleModel implements SControllerListener, SModel, ModelListener{
    /** Max players limited by the four racks (see README setup rules) */
    public static final int MAX_PLAYERS = 4;
    /** Min players, should be 2 but 1 could work if we want to allow solo play */
    @SuppressWarnings("unused")
    public static final int MIN_PLAYERS = 2; // Note: unused atm but probably should be
    public static final int BOARD_SIZE = 15;

    // Model components
    final private Board board;
    private ArrayList<Player> players;
    /** Model's shared DrawPile */
    private final DrawPile drawPile;

    /** Player whose turn it is to play */
    private int numPlayers;
    private int turn;
    /** False until something triggers a game to end (Model.Player out of Letters, or no more possible moves)*/
    boolean gameFinished; // FIXME: may become a controller signal in the future, with no need for a field
    /** Model listeners to notify on model change */
    List<ModelListener> modelListeners;
    /** list of selected tiles (in order) to pass to the board when placing*/
    List<Tile> selectedTiles;


    private final List<SController> debugControllers;

    public static final Color SIDE_BACKGROUND_COLOR = new Color(144, 42, 42);

    public ScrabbleModel(List<?> playerInfo) {
        this.board = new Board(false);
        this.drawPile = new DrawPile();
        this.gameFinished = false;
        this.modelListeners = new ArrayList<>();
        this.selectedTiles = new ArrayList<>();
        this.players = new ArrayList<>();
        this.debugControllers = new ArrayList<>();
        this.turn = 0;
        this.numPlayers = 0; // In case of null players
        // Guard against null human players
        if(playerInfo != null){
            this.numPlayers = playerInfo.size();
            initializePlayers(playerInfo);
        }
    }

    /**
     * Increments turn, rolling back to the first turn after passing the last player.
     */
    private void incrementTurn(){
        turn = turn == numPlayers -1 ? 0 : turn+1;
    }

    /**
     * Creates Player models from a list of player names.
     *
     * @param playerInfos The list of playerInfo for each player in the model
     */
    private void initializePlayers(List<?> playerInfos){
        players = new ArrayList<>();
        for (Object playerInfo: playerInfos) {
            if(!(boolean)((List<?>)playerInfo).get(1)) {
                players.add(new Player((String)((List<?>)playerInfo).get(0), this));
            }else{
                players.add(new AIPlayer((String)((List<?>)playerInfo).get(0), this));
            }
        }
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

        // Otherwise, not a valid placement (ignore placement)
        if (placementScore >= 0) {
            // Notify listeners about new board state
            notifyModelListeners(new BoardChangeEvent(board));
            notifyModelListeners(new PlayerChangeEvent(players));

            // Letters have been placed, get rid of them and bank the score.
            getCurPlayer().addPoints(placementScore);
            try{
                getCurPlayer().placeTiles(selectedTiles);

            } catch (NullPointerException e){
                endGame();
            }

            notifyModelListeners(new BoardChangeEvent(board));
            nextTurn();
        }
    }


    /**
     * Used to end the game
     */
    public void newGame() {
        // TODO
    }

    /**
     * Gets player with highest score
     * @return Player with highest score
     */
    private Player getTopPlayer(){
        Player topPlayer = players.get(0);
        for (Player player : players){
            if (player.getScore() > topPlayer.getScore()){
                topPlayer = player;
            }
        }
        return topPlayer;
    }

    /**
     * Handles ending the game
     */
    private void endGame(){
        Player winner = getTopPlayer();
        OptionPaneHandler popUpHandler = new OptionPaneHandler();

        gameFinished = true;
        popUpHandler.displayMessage("Draw Pile is Empty Game is Over!\nThe winner is "+winner.getName()+"! Congrats!!!");
    }

    /**
     * Handles starting the game
     */
    // Creating a model should be synonymous to creating a game, we should move towards removing this.
    // I'm not convinced "synonymous to creating a game" is a good idea anymore (M3)
    public void startGame(){
        //Need to notify Score View here
        notifyModelListeners(new BoardChangeEvent(board));
        notifyModelListeners(new PlayerChangeEvent(players));
        notifyModelListeners(new NewPlayerEvent(getCurPlayer()));
        if(getCurPlayer() instanceof AIPlayer){
            notifyModelListeners(new AIPlayingEvent(true));
            ((AIPlayer) getCurPlayer()).play();
            notifyModelListeners(new AIPlayingEvent(false));
        }
    }

    /**
     * Handles running a turn, will be called in a loop until the game is over
     */
    private void nextTurn(){
        if(gameFinished) return;

        selectedTiles = new ArrayList<>(); // Clear selection
        // Update views to show current player
        incrementTurn();

        notifyModelListeners(new PlayerChangeEvent(players));
        notifyModelListeners(new NewPlayerEvent(getCurPlayer()));
        if(getCurPlayer() instanceof AIPlayer){
            notifyModelListeners(new AIPlayingEvent(true));
            ((AIPlayer) getCurPlayer()).play();
            notifyModelListeners(new AIPlayingEvent(false));
        }
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
        if (gameFinished) return;
        // TODO: make switch, show dropped events
        if(e instanceof PlaceClickEvent pce) handlePlace(pce);
        if(e instanceof DiscardClickEvent) handleDiscard();
        if(e instanceof C_SkipEvent) nextTurn();
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
        // (Concurrent modification exception otherwise)
        // noinspection ForLoopReplaceableByForEach
        for (int l = 0; l< modelListeners.size(); l++) {
            modelListeners.get(l).handleModelEvent(e);
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

    /**
     * If debug view is activated, will keep track of controllers in the program
     * @param c new controller talking to the model
     */
    public void addDebugController(SController c) {
        if(DEBUG_VIEW)
            this.debugControllers.add(c);
    }

    /**
     * If debug view is activated, will return the controllers in the program
     * @return The list of controllers talking to the model
     */
    public List<SController> getDebugControllers() {
        if(DEBUG_VIEW)
            return this.debugControllers;
        else
            return null;
    }
}
