package Model;

import ScrabbleEvents.ControllerEvents.DiscardClickEvent;
import ScrabbleEvents.ControllerEvents.PlaceClickEvent;
import ScrabbleEvents.ControllerEvents.TileClickEvent;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.BoardPlaceEvent;

import java.awt.*;
import java.util.List;
import java.util.*;

/**
 * AIPlayer is an extension of the player class to handle
 * the choice and placement of words by an AI player.
 *
 * @author Timothy Kennedy
 * @version NOV-29
 */
public class AIPlayer extends Player {

    private static final boolean delay = false;
    private static final int MAX_ATTEMPTS = 40;
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
        HashMap<BoardPlaceEvent,Integer> placeEvents = new HashMap<>(); //make a HashMap to store placement information and corresponding score
        if(model.getBoard().isBoardEmpty()){ //The board is empty, play from the center
            try {
                placeEvents = getPlacementEventOn(null, true); // the board is empty, and no BoardTile is included in the word
            }catch (Exception e){
                //System.out.println(e.getMessage());
            }
            if(placeEvents.size() > 0){ //if there were possible placements found, play the one with the highest score
                doPlacementEvent(Collections.max(placeEvents.entrySet(), Map.Entry.comparingByValue()).getKey());
                return;
            }
        }else {
            ArrayList<BoardTile> boardTiles = new ArrayList<>(model.getBoard().getBoardTiles().stream().filter(BoardTile::isTaken).toList());
            for (BoardTile tile : boardTiles) {
                try {
                    placeEvents.putAll(getPlacementEventOn(tile, false)); // The board has words on it, find placements including this tile
                } catch (
                        Exception e) {
                    //System.out.println(e.getMessage());
                }
            }
            if(placeEvents.size() > 0) { // if any placement events were found
                doPlacementEvent(Collections.max(placeEvents.entrySet(), Map.Entry.comparingByValue()).getKey()); //play the highest scoring placement
                return;
            }
        }

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
            if(delay) {
                try {
                    Thread.sleep(70);
                } catch (InterruptedException e) {
                }
            }
        }
        model.handleControllerEvent(new PlaceClickEvent(placeEvent.direction(),placeEvent.wordOrigin()));
    }

    /**
     * Handles the AI discarding their hand
     */
    private void doHandDiscard(){
        for(Tile tile:super.getHand().getHeldTiles()){
            model.handleControllerEvent(new TileClickEvent(tile));
            if(delay) {
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                }
            }
        }
        model.handleControllerEvent(new DiscardClickEvent());
    }

    /**
     * Gets placement events including a boardTile if applicable, and returns a HashSet of valid placements and their scores if found.
     *
     * @param boardTileUsed the boardTile to include in the placement (if applicable)
     * @param startOfGame whether the board is blank
     * @return a valid placement, or null if none are found
     * @throws Exception if there is no passed boardTile but the board isn't empty
     */
    private HashMap<BoardPlaceEvent,Integer> getPlacementEventOn(BoardTile boardTileUsed, boolean startOfGame) throws Exception {
        if(!startOfGame && boardTileUsed == null){
            throw new Exception("boardTileUsed is null but tiles have already been played");
        }
        Board board = model.getBoard();
        Hand hand = super.getHand();

        HashMap<BoardPlaceEvent,Integer> placeEvents = new HashMap<>();
        ArrayList<Tile> tilesToPlace; //Tiles with which to attempt a placement
        ArrayList<Tile> notPlaced; //intermediate list to avoid modifying hand
        BoardPlaceEvent event;

        for(ArrayList<String> word: (startOfGame) ? getValidWordsNew(hand) : getValidWordsNew(boardTileUsed,hand)){
            tilesToPlace = new ArrayList<>(); //clear list each attempt (otherwise will grow)
            notPlaced = new ArrayList<>(hand.getHeldTiles());
            if(startOfGame){
                // Start play from the center
                transferTilesMatchingWord(notPlaced, tilesToPlace, word);
                for(int offset = 0; offset < word.size()-1; offset++) { //try all placements of the first word

                    event = new BoardPlaceEvent(tilesToPlace, new Point(7, 7-offset), Board.Direction.DOWN);
                    placeEvents.put(event, board.getPlacementScore(event));
                    event = new BoardPlaceEvent(tilesToPlace, new Point(7-offset, 7), Board.Direction.RIGHT);
                    placeEvents.put(event, board.getPlacementScore(event));
                }
            }else {
                //get the position where the boardTile intersects the word, to know what position to play the word later
                int boardTileIndex = word.indexOf(boardTileUsed.getLetter().name());

                word.remove(boardTileUsed.getLetter().name());
                transferTilesMatchingWord(notPlaced,tilesToPlace,word); //Get the tiles needed for the placement
                //check if placing the word going down is a valid placement
                if(board.isValidPlacement(
                        new BoardPlaceEvent(
                                tilesToPlace, new Point(boardTileUsed.getX(), (boardTileIndex > 0) ? (boardTileUsed.getY()-boardTileIndex): boardTileUsed.getY()+1), Board.Direction.DOWN
                        )) ==
                        BoardValidator.Status.SUCCESS)
                {
                    //add corresponding placement event
                    event = new BoardPlaceEvent(tilesToPlace, new Point(boardTileUsed.getX(), boardTileUsed.getY()-boardTileIndex) , Board.Direction.DOWN);
                    placeEvents.put(event,board.getPlacementScore(event));
                }

                //check if placing the word going right is a valid placement
                if (board.isValidPlacement(
                        new BoardPlaceEvent(
                                tilesToPlace, new Point((boardTileIndex > 0) ? (boardTileUsed.getX()-boardTileIndex) : boardTileUsed.getX()+1, boardTileUsed.getY()), Board.Direction.RIGHT
                        )) ==
                        BoardValidator.Status.SUCCESS)
                {
                    //add corresponding placement event
                    event = new BoardPlaceEvent(tilesToPlace, new Point(boardTileUsed.getX()-boardTileIndex, boardTileUsed.getY()), Board.Direction.RIGHT);
                    placeEvents.put(event,board.getPlacementScore(event));
                }
            }
        }
        return placeEvents;
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
    private void transferTilesMatchingWord(ArrayList<Tile> removeFrom, ArrayList<Tile> insertInto, ArrayList<String> word){
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
     * @deprecated
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
     * @deprecated
     */
    public ArrayList<ArrayList<String>> getValidWords(BoardTile tile, Hand hand){
        // The letters in the hand of the AI player: what it has to "work with"
        StringBuilder letters = new StringBuilder();
        Random r = new Random();

        ArrayList<ArrayList<String>> placementCandidates = new ArrayList<>();
        for(Tile heldTile:hand.getHeldTiles()){
            letters.append(heldTile.getLetter().name());
        }
        //Find valid words in a large amount of random strings that contain the right letters (including that of the BoardTile
        for(ArrayList<String> wordCandidate:getNRandomPermutations(letters.toString())){
            wordCandidate.add(r.nextInt(0,wordCandidate.size()+1),tile.getLetter().toString());
            if(dict.isValidWord(String.join("",wordCandidate))){
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
     * @deprecated
     */
    private HashSet<ArrayList<String>> getNRandomPermutations(String letters) {
        Random r = new Random();
        HashSet<ArrayList<String>> perms = new HashSet<>(); // Set of permutations generated-don't want to have duplicates
        int count = 0;

        //list of letters to shuffle to get a random permutation
        ArrayList<String> shuffle = new ArrayList<>(Arrays.asList(letters.split("")));


        while(perms.size() < MAX_ATTEMPTS && count < 150) {
            Collections.shuffle(shuffle, r);
            count++; // Stop it running forever
            // add a permutation of variable length
            perms.add(new ArrayList<>(shuffle.subList(0,r.nextInt(1,shuffle.size()))));
        }
        return perms;
    }


    /**
     * Gets a set of valid words given the AI's hand using new algorithm
     *
     * @param hand the hand of the AI player
     * @return a list of valid words to place
     *
     * @author Timothy Kennedy
     */
    public HashSet<ArrayList<String>> getValidWordsNew(Hand hand){
        // The letters in the hand of the AI player: what it has to "work with"
        StringBuilder letters = new StringBuilder();

        HashSet<ArrayList<String>> placementCandidates = new HashSet<>();

        for(Tile heldTile:hand.getHeldTiles()){
            if(!heldTile.getLetter().name().equals("BLANK")) {
                letters.append(heldTile.getLetter().name());
            }
        }

        //Find valid words
        for(String permutation:getNRandomPerms(Arrays.asList(letters.toString().split("")))){
            placementCandidates.addAll(dict.optimizedGetValidWords(getLetterCount(permutation),permutation.length()));
        }
        return placementCandidates;
    }


    /**
     * Gets a set of valid words given a tile to [extend from/extend to/cross] and the AI's hand using new algorithm
     *
     * @param tile the tile being considered for placement
     * @param hand the hand of the AI player
     * @return a list of valid words to place
     *
     * @author Timothy Kennedy
     */
    public HashSet<ArrayList<String>> getValidWordsNew(BoardTile tile, Hand hand){
        StringBuilder letters = new StringBuilder();

        HashSet<ArrayList<String>> placementCandidates = new HashSet<>();

        for(Tile heldTile:hand.getHeldTiles()){
            if(!heldTile.getLetter().name().equals("BLANK")) {
                letters.append(heldTile.getLetter().name());
            }
        }
        letters.append(tile.getLetter().name());

        //Find valid words
        for(String permutation:getNRandomPerms(Arrays.asList(letters.toString().split("")))){
            placementCandidates.addAll(dict.optimizedGetValidWords(getLetterCount(permutation),permutation.length()).stream().filter(word->word.contains(tile.getLetter().name())).toList());
        }
        return placementCandidates;
    }

    /**
     * Gets a Hashmap with unique letters as keys with their frequencies as values
     *
     * @param letters the string to count from
     * @return a HashMap mapping letters to their frequency
     */
    public static HashMap<String,Integer> getLetterCount(String letters){
        HashMap<String,Integer> letterCount = new HashMap<>();

        letters.chars().forEach(
                letter-> letterCount.put(Character.toString(letter), (int) letters.chars().filter(e -> e == letter).count())
        );

        return letterCount;
    }

    /**
     * Utility function to get random permutations of a list of letters
     *
     * @param a the letters to get the permutations of
     * @return a list of random permutations
     * @author Timothy Kennedy
     */
    private static List<String> getNRandomPerms(List<String> a) {
        HashSet<String> perms = new HashSet<>();
        Random r = new Random();
        for(int i = 0; i < MAX_ATTEMPTS; i++){
            Collections.shuffle(a);
            perms.add(String.join("",a.subList(0,r.nextInt(1,a.size()+1))));
        }
        return perms.stream().sorted(Comparator.comparingInt(String::length).reversed()).toList();
    }

}
