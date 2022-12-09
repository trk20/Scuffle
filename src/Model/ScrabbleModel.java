package Model;

import Controllers.OptionPaneHandler;
import Controllers.SController;
import ScrabbleEvents.ControllerEvents.*;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.Listeners.SControllerListener;
import ScrabbleEvents.ModelEvents.*;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static Views.DebugView.DEBUG_VIEW;

/**
 * Class that controls/models the overall scrabble game.
 *
 * @author Kieran Rourke, Alex, Vladimir Kovacina
 * @version NOV-21
 */
public class ScrabbleModel implements SControllerListener, SModel, Serializable {
    public static final Color SIDE_BACKGROUND_COLOR = new Color(144, 42, 42);
    /** Max players limited by the four racks (see README setup rules) */
    public static final int MAX_PLAYERS = 4;
    /** Min players, should be 2 but 1 could work if we want to allow solo play */
    @SuppressWarnings("unused")
    public static final int MIN_PLAYERS = 2; // Note: unused atm but probably should be
    public static final int BOARD_SIZE = 15;

    // Model components
    private Board board;
    private ArrayList<Player> players;
    /** Model's shared DrawPile */
    private final DrawPile drawPile = new DrawPile();
    private static final OptionPaneHandler input = new OptionPaneHandler();
    // Model components
    /** Player whose turn it is to play */
    private int numPlayers;
    private int turn;
    /** False until something triggers a game to end (Model.Player out of Letters, or no more possible moves)*/
    boolean gameFinished; // FIXME: may become a controller signal in the future, with no need for a field

    /** Model listeners to notify on model change */
    private transient List<ModelListener> modelListeners;
    /** list of selected tiles (in order) to pass to the board when placing*/
    private List<Tile> selectedTiles;

    private final UndoHandler undoHandler;
    private final RedoHandler redoHandler;

    private final List<SController> debugControllers;



    /**
     * For ease of testing, returns a Hashmap of <player name, false>
     * @param playerNames names of the players
     * @return a Hashmap of <player name, false>
     */
    private static HashMap<String,Boolean> getPlayerInfos(List<String> playerNames){
        HashMap<String,Boolean> playerInfo = new HashMap<>();
        for (String playerName:playerNames) {
            while(playerInfo.containsKey(playerName))
                playerName+=" ";
            playerInfo.put(playerName,false);
        }
        return playerInfo;
    }

    public ScrabbleModel(List<String> realPlayerNames){
        this(getPlayerInfos(realPlayerNames));
    }

    public ScrabbleModel(HashMap<String,Boolean> playerInfos){
        this.board = new Board(true);
        this.gameFinished = false;
        this.modelListeners = new ArrayList<>();
        this.selectedTiles = new ArrayList<>();
        this.players = new ArrayList<>();
        this.debugControllers = new ArrayList<>();
        this.turn = 0;
        this.numPlayers = 0; // In case of null players
        this.undoHandler = new UndoHandler();
        this.redoHandler = new RedoHandler();

        addModelListener(redoHandler);
        addModelListener(undoHandler);

        if(playerInfos.size() != 0){
            this.numPlayers = playerInfos.size();
            initializePlayers(playerInfos);
        }
    }

    /**
     * Increments turn, rolling back to the first turn after passing the last player.
     */
    private void incrementTurn(){
        turn = turn == numPlayers -1 ? 0 : turn+1;
    }

    private void decrementTurn(){
        turn = turn == 0 ? numPlayers-1 : turn-1;
    }

    /**
     * Creates Player models from a list of player names.
     *
     * @param playerInfos The HashMap of playerInfo for each player in the model
     */
    private void initializePlayers(HashMap<String,Boolean> playerInfos){
        players = new ArrayList<>();
        for (String playerName: playerInfos.keySet()) {
            if(!playerInfos.get(playerName)) {
                players.add(new Player(playerName, drawPile));
            }else{
                players.add(new AIPlayer(playerName, this));
            }
        }
    }

    /**
     * Handles the player wanting to skip
     */
    private void handleSkip(){
        notifyModelListeners(new UndoHandlerEvent(players, board));
        nextTurn(false);
    }

    /**
     * Handles the user wanting to discard letters
     *  @author Kieran, Alexandre
     */
    private void handleDiscard(){
        notifyModelListeners(new UndoHandlerEvent(players, board));
        getCurPlayer().discardTiles(selectedTiles);
        nextTurn(false);
    }

    /**
     * Handles the user wanting to place letters
     */
    private void handlePlace(PlaceClickEvent pce){
        //Check for Blank tile in selected tiles:
        for(int i =0; i< selectedTiles.size(); i++){
            if(selectedTiles.get(i).getLetter() == Letter.BLANK){
                selectedTiles.get(i).setLetter(input.getChosenLetter());
            }
        }
    
        // Check if the placement event is valid
        BoardPlaceEvent placeEvent = new BoardPlaceEvent(selectedTiles, pce.origin(), pce.dir());
        BoardValidator.Status validStatus = board.isValidPlacement(placeEvent);
        
        if (validStatus == BoardValidator.Status.SUCCESS){
            notifyModelListeners(new UndoHandlerEvent(players, board));
            // Place on board, save points in player
            getCurPlayer().addPoints(board.placeWord(placeEvent));
            try{
                getCurPlayer().placeTiles(selectedTiles); // Get rid of tiles used
            } catch (NullPointerException e){
                endGame();
            }
            // Update turn state
            nextTurn(false);
        } else { //Reset Blank tile (could not place)
            notifyModelListeners(new ME_InvalidPlacement(validStatus));
            for(int i=0; i< selectedTiles.size(); i++){
                if(selectedTiles.get(i).getScore() == 0){
                    selectedTiles.get(i).setLetter(Letter.BLANK);
                }
            }
        }
    }


    /**
     * Used to start a new game
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
    private void nextTurn(boolean isSpecialAction){
        if(gameFinished) return;

        selectedTiles = new ArrayList<>(); // Clear selection


        if(!isSpecialAction){
            incrementTurn();
            notifyModelListeners(new RedoHandlerClearEvent());
        } else{
            notifyModelListeners(new ME_ModelChangeEvent(this));
        }
        // Notify listeners about new board state
        // Update views to show current player
        notifyModelListeners(new BoardChangeEvent(board));
        notifyModelListeners(new PlayerChangeEvent(players));
        notifyModelListeners(new NewPlayerEvent(getCurPlayer()));
        // Otherwise, wait for GUI controllers to handle turn
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
        if(e instanceof C_SkipEvent) handleSkip();
        if(e instanceof TileClickEvent tce) flipTileSelect(tce);
        if(e instanceof C_SaveEvent se) serializeModel(se.fileLocation());
        if(e instanceof C_LoadEvent se) deSerializeModel(se.fileLocation());
        if(e instanceof C_DirectionChangeEvent bce) notifyModelListeners(new ME_NewDirectionEvent(bce.dir()));
        if(e instanceof C_UndoEvent) handleUndo();
        if(e instanceof C_RedoEvent) handleRedo();
    }


    private void serializeModel(File fileLocation) {
        ObjectOutputStream objOut = null;
        try {
            objOut = new ObjectOutputStream(new FileOutputStream(fileLocation));
            objOut.writeObject(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void deSerializeModel(File fileLocation) {
        ObjectInputStream objIn = null;
        ScrabbleModel newModel = null;
        try {
            objIn = new ObjectInputStream(new FileInputStream(fileLocation));
            newModel = (ScrabbleModel) objIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //
        newModel.modelListeners = new ArrayList<>(); // Transient, have to re-initialize here
        // Update views to look at the new model only
        for (int l = 0; l< modelListeners.size(); l++) {
            newModel.addModelListener(modelListeners.get(l));
        }
        // Make views stop listening to old model
        modelListeners.clear();
        // Update views for new model
        newModel.notifyModelListeners(new ME_ModelChangeEvent(newModel));
        newModel.notifyModelListeners(new BoardChangeEvent(newModel.board));
        System.out.println(newModel.board);
        newModel.notifyModelListeners(new PlayerChangeEvent(newModel.players));
        newModel.notifyModelListeners(new NewPlayerEvent(newModel.getCurPlayer()));
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
    public Board getBoard() {
        return board;
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

    /**
     * Handles the player wanting to undo
     */
    private void handleUndo(){
        if(undoHandler.isStackEmpty()){
            return;
        }
        notifyModelListeners(new RedoHandlerEvent(players, board));
        board = undoHandler.getPreviousBoard();
        players = (ArrayList<Player>) undoHandler.getPreviousPlayerState();
        decrementTurn();


        nextTurn(true);
    }

    /**
     * Handles the player wamting to redo
     */
    private void handleRedo() {
        if(redoHandler.isStackEmpty()){
            return;
        }
        notifyModelListeners(new UndoHandlerEvent(players, board));
        board = redoHandler.getPreviousBoard();
        players = (ArrayList<Player>) redoHandler.getPreviousPlayerState();
        incrementTurn();

        nextTurn(true);
    }

    public UndoHandler getUndoHandler(){
        return undoHandler;
    }

    public RedoHandler getRedoHandler(){
        return redoHandler;
    }
}
