package Model;

import ScrabbleEvents.ControllerEvents.DiscardClickEvent;
import ScrabbleEvents.ControllerEvents.PlaceClickEvent;
import ScrabbleEvents.ControllerEvents.TileClickEvent;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.BoardPlaceEvent;

import java.awt.*;
import java.util.List;
import java.util.*;

public class AIPlayer extends Player {
    private static final int MAX_ATTEMPTS = 100;
    private final List<ModelListener> modelListeners;
    private final DictionaryHandler dict;
    private final ScrabbleModel model;

    /**
     * AIPlayer constructor
     *
     * @param name the AI player's name
     * @param model the Scrabble game's model
     */
    public AIPlayer(String name, ScrabbleModel model) {
        super(name, model.getDrawPile());
        this.modelListeners = new ArrayList<>();
        this.model = model;
        dict = new DictionaryHandler();
    }

    /**
     * Makes the AI player find a placement or discard their hand if none are found
     *
     * @author Timothy Kennedy
     */
    public void play(){
        BoardPlaceEvent placeEvent = null; //make a new PlaceEvent to store placement information

        if(model.getBoard().isBoardEmpty()){ //The board is empty, play the first possible valid word

            System.out.println("Board is empty, playing word");
            try {
                placeEvent = getPlacementEventOn(null, true); // the board is empty, no BoardTile included in the word
                System.out.println(placeEvent);
            }catch (Exception e){
                //System.out.println(e.getMessage());
            }
            if(placeEvent!= null){ //if there was a possible placement found, play it
                doPlacementEvent(placeEvent);
                return;
            }
        }else {
            ArrayList<BoardTile> boardTiles = new ArrayList<BoardTile>(model.getBoard().getBoardTiles().stream().filter(BoardTile::isTaken).toList());
            Collections.shuffle(boardTiles);
            for (BoardTile tile : boardTiles) {
                try {
                    placeEvent = getPlacementEventOn(tile, false); // The board has words on it, find a word including this tile
                } catch (
                        Exception e) {
                    //System.out.println(e.getMessage());
                }
                if (placeEvent != null) {
                    doPlacementEvent(placeEvent);
                    return;
                }
            }
        }

        //System.out.println("Couldn't find a placement for " + super.getHand()+", discarding");

        //if the AI can't find anywhere to play, it discards its entire hand
        doHandDiscard();
    }

    /**
     * Handles a placement on the board by the AI player
     *
     * @param placeEvent the placement the AI is playing
     */
    private void doPlacementEvent(BoardPlaceEvent placeEvent){
        for(Tile tile:placeEvent.placedTiles()){
            model.handleControllerEvent(new TileClickEvent(tile));
        }
        model.handleControllerEvent(new PlaceClickEvent(placeEvent.direction(),placeEvent.wordOrigin()));
    }

    /**
     * Handles the AI discarding their hand
     */
    private void doHandDiscard(){
        for(Tile tile:super.getHand().getHeldTiles()){
            model.handleControllerEvent(new TileClickEvent(tile));
        }
        model.handleControllerEvent(new DiscardClickEvent());
    }

    /**
     * Gets a placement event including a boardTile if applicable, and returns a valid placement if found.
     * If it finds no placements, it returns null.
     * TODO: optimize/rework/change word-finding algorithm (currently random guessing, inefficient)
     *
     * @param boardTileUsed the boardTile to include in the placement (if applicable)
     * @param startOfGame whether the board is blank
     * @return a valid placement, or null if none are found
     * @throws Exception if there is no passed boardTile but the board isn't empty
     */
    private BoardPlaceEvent getPlacementEventOn(BoardTile boardTileUsed, boolean startOfGame) throws Exception {
        if(!startOfGame && boardTileUsed == null){
            throw new Exception("boardTileUsed is null but tiles have already been played");
        }
        Board board = model.getBoard();
        Hand hand = super.getHand();

        ArrayList<Tile> tilesToPlace; //Tiles with which to attempt a placement

        for(ArrayList<String> word: (startOfGame) ? getValidWords(hand) : getValidWords(boardTileUsed,hand)){
            tilesToPlace = new ArrayList<>(); //clear list each attempt (otherwise will grow)
            ArrayList<Tile> notPlaced = new ArrayList<>(hand.getHeldTiles()); //intermediate list to avoid modifying hand
            if(!startOfGame) { //The boardTile being placed on isn't a tile the AI needs to play, so remove it from the word before playing the tiles
                word.remove(boardTileUsed.getLetter().name());
            }
            transferTilesMatchingWord(notPlaced,tilesToPlace,word); //Get the tiles needed for the placement
            if(startOfGame){
                // place the first valid word, starting at the center going down
                return new BoardPlaceEvent(tilesToPlace, new Point(7,7), Board.Direction.DOWN);
            }else {
                //get the position where the boardTile intersects the word, to know what position to play the word later
                int boardTileIndex =
                        tilesToPlace.indexOf(
                                tilesToPlace.stream()
                                                .filter(currentTile->currentTile.getLetter().name().equals(boardTileUsed.getLetter().name()))
                                        .findFirst().get());

                //debug
                /*System.out.println("Trying placement\n" +
                        new BoardPlaceEvent(tilesToPlace, new Point(boardTileUsed.getX(), boardTileUsed.getY()-boardTileIndex), Board.Direction.DOWN)+
                        " and placement\n" + new BoardPlaceEvent(tilesToPlace, new Point(boardTileUsed.getX()-boardTileIndex, boardTileUsed.getY()), Board.Direction.RIGHT));
                */

                //check if placing the word going down is a valid placement
                if(board.isValidPlacement(
                        new BoardPlaceEvent(
                                tilesToPlace, new Point(boardTileUsed.getX(), boardTileUsed.getY()-boardTileIndex), Board.Direction.DOWN
                        )) ==
                        BoardValidator.Status.SUCCESS)
                {
                    //return corresponding placement event
                    return new BoardPlaceEvent(tilesToPlace, new Point(boardTileUsed.getX(), boardTileUsed.getY()-boardTileIndex), Board.Direction.DOWN);
                }

                //check if placing the word going right is a valid placement
                else if (board.isValidPlacement(
                        new BoardPlaceEvent(
                                tilesToPlace, new Point(boardTileUsed.getX()-boardTileIndex, boardTileUsed.getY()), Board.Direction.RIGHT
                        )) ==
                        BoardValidator.Status.SUCCESS)
                {
                    //return corresponding placement event
                    return new BoardPlaceEvent(tilesToPlace, new Point(boardTileUsed.getX()-boardTileIndex, boardTileUsed.getY()), Board.Direction.RIGHT);
                }
            }
        }

        //no placements were found :(
        return null;
    }

    /**
     * A utility function to transfer a tile for each matching letter in a word from one ArrayList to another
     *
     * @param removeFrom the ArrayList to remove from
     * @param insertInto the ArrayList to insert into
     * @param word the word to match against
     *
     * @author Timothy Kennedy
     */
    public void transferTilesMatchingWord(ArrayList<Tile> removeFrom, ArrayList<Tile> insertInto, ArrayList<String> word){
        for(String letter:word) {
            for(int i = 0; i < removeFrom.size(); i ++) {
                Tile current = removeFrom.get(i);
                if(current.getLetter().name().equals(letter)) {
                    insertInto.add(removeFrom.remove(i));
                    break;
                }
            }
        }
    }

    /**
     * Gets a list of valid words given the AI's hand
     *
     * @param hand the hand of the AI player
     * @return a list of valid words to place
     *
     * @author Timothy Kennedy
     */
    public ArrayList<ArrayList<String>> getValidWords(Hand hand){
        // The letters in the hand of the AI player: what it has to "work with"
        StringBuilder letters = new StringBuilder();

        // List of words found
        ArrayList<ArrayList<String>> placementCandidates = new ArrayList<>();

        for(Tile heldTile:hand.getHeldTiles()){
            letters.append(heldTile.getLetter().name());
        }

        //Find valid words in a large amount of random strings that contain the right letters
        for(ArrayList<String> wordCandidate:getNRandomPermutations(letters.toString())){
            if(dict.isValidWord(String.join("",wordCandidate))){
                placementCandidates.add(wordCandidate);
            }
        }
        return placementCandidates;
    }
    /**
     * Gets a list of valid words given a tile to [extend from/extend to/cross] and the AI's hand
     *
     * @param tile the tile being considered for placement
     * @param hand the hand of the AI player
     * @return a list of valid words to place
     *
     * @author Timothy Kennedy
     */
    public ArrayList<ArrayList<String>> getValidWords(BoardTile tile, Hand hand){
        // The letters in the hand of the AI player: what it has to "work with"
        StringBuilder letters = new StringBuilder(tile.getLetter().toString());

        ArrayList<ArrayList<String>> placementCandidates = new ArrayList<>();
        for(Tile heldTile:hand.getHeldTiles()){
            letters.append(heldTile.getLetter().name());
        }
        //Find valid words in a large amount of random strings that contain the right letters (including that of the BoardTile
        for(ArrayList<String> wordCandidate:getNRandomPermutations(letters.toString())){
            if(wordCandidate.contains(tile.getLetter().name()) && dict.isValidWord(String.join("",wordCandidate))){
                placementCandidates.add(wordCandidate);
            }
        }
        //System.out.println(placementCandidates);
        return placementCandidates;
    }

    /**
     * Utility function to get n random permutations of a string
     *
     * @param letters the string to get the permutations of
     * @return a hashset of n random permutations of the string
     * @author Timothy Kennedy
     */
    private HashSet<ArrayList<String>> getNRandomPermutations(String letters) {
        Random r = new Random();
        HashSet<ArrayList<String>> perms = new HashSet<>(); // Set of permutations generated-don't want to have duplicates

        //list of letters to shuffle to get a random permutation
        ArrayList<String> shuffle = new ArrayList<>(Arrays.asList(letters.split("")));

        while(perms.size() < MAX_ATTEMPTS) {
            Collections.shuffle(shuffle, r);

            // add a permutation of variable length
            perms.add(new ArrayList<>(shuffle.subList(0,r.nextInt(1,shuffle.size()))));
        }
        return perms;
    }
}
