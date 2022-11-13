package Model;

import Events.BoardPlaceEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

import static Model.ScrabbleModel.BOARD_SIZE;

/**
 * Class that handles the Model.Board for the Scrabble game
 *
 * @author Vladimir Kovacina
 * @author Timothy Kennedy
 * @author Alex
 * @version NOV-12
 */
public class Board {
    public enum Direction{
        DOWN("↓"), RIGHT("→");
        private final String dirStr;
        Direction(String s){this.dirStr = s;}

        @Override
        public String toString() {
            return dirStr;
        }
    }
    private BoardTile2DTable boardTileTable; // COLUMN (x), ROW (y)
    private ArrayList<PlacedWord> currentWords;
    /** Takes care of validating the board */
    private BoardValidator validator;

    /**
     * Constructor for a Model.Board object
     * @author Vladimir Kovacina
     * @author Timothy Kennedy
     */
    public Board(boolean randomBoard){
        boardTileTable = new BoardTile2DTable();
        validator = new BoardValidator(this);
        currentWords = new ArrayList<>();

        if(randomBoard) boardTileTable.randomiseTable();

        //Initialize the start tile at the middle of the board
        boardTileTable.setType(new Point(BOARD_SIZE/2, BOARD_SIZE/2), BoardTile.Type.START);
    }

    /**
     * Places a word on the board
     * @author Timothy Kennedy
     * @author Alex
     *
     * @param placeEvent The event containing the placement details
     * @return -1 if it is an invalid placement, otherwise, the score resulting from the placement.
     */
    public int placeWord(BoardPlaceEvent placeEvent){
        // Ensure valid placement
        if(!validator.isValidPlacement(placeEvent)) return -1;

        setWordTiles(placeEvent);

        int turnScore = getTurnScore(placeEvent);

        // Add new words to current words
        currentWords = new ArrayList<>();
        currentWords.addAll(getNewWords(placeEvent));

        // Return score after placing
        return turnScore;
    }

    /**
     * Places tiles in the board, skipping over tiles that are already placed.
     * Precondition: assumes valid placement.
     *
     * @param placeEvent The event containing the placement details
     */
    private void setWordTiles(BoardPlaceEvent placeEvent) {
        // Unpack relevant event info
        Point wordOrigin = placeEvent.getWordOrigin();
        Direction placementDirection = placeEvent.getDirection();
        List<Tile> word = placeEvent.getPlacedTiles();
        System.out.println(word);
        int overlaps = 0; // Place tile one further if a tile already occupies its spot

        // Place tiles in the board, skipping tiles that are already placed.
        for(int i = 0; i < word.size(); i++) {
            boolean overlapping = true; // (until proven otherwise)
            Point placementLocation = new Point();
            if (placementDirection == Board.Direction.RIGHT) {
                // Increment Col (x), until no overlap
                while (overlapping) {
                    placementLocation.setLocation((wordOrigin.x + (i + overlaps)), wordOrigin.y);
                    if (isTaken(placementLocation)) overlaps += 1;
                    else overlapping = false;
                }
            } else {
                // Decrement row (y), until no overlap
                while (overlapping) {
                    placementLocation.setLocation(wordOrigin.x, wordOrigin.y + (i + overlaps));
                    if (isTaken(placementLocation)) overlaps += 1;
                    else overlapping = false;
                }
            }
            placeTile(placementLocation, word.get(i).letter());
        }
    }

    /**
     * Place a tile in the board.
     * Precondition: The tile is verified to be empty beforehand
     * @param p The point where the tile should be placed
     * @param letter the letter to place at the board location.
     */
    private void placeTile(Point p, Letter letter) {
        boardTileTable.setLetter(p, letter);
    }


    /**
     * Checks if a coordinate in the board is taken (x = col, y = row), starting top left.
     * @param p A point coordinate in the board to check
     * @return True if a tile is placed at that location, false otherwise.
     */
    boolean isTaken(Point p) {
        return boardTileTable.isTaken(p);
    }

    /**
     * Checks if no words have been placed on the board yet.
     * @return True if the board is empty, false otherwise.
     */
    // Package private: only accessible by model classes (for the validator)
    boolean isBoardEmpty(){
        return currentWords.size() == 0;
    }

    /**
     * Checks if the given board tile is the board's start tile
     *
     * @param p the coordinates of the tile to check for the "start" type
     * @return True if the given board tile is the board's start tile, false otherwise.
     */
    boolean isStartTile(Point p) {
        return boardTileTable.getType(p) == BoardTile.Type.START;
    }

    /**
     * Creates a word starting from given coordinates
     * @author Timothy Kennedy
     *
     * @param aBoard the state of the board to check
     * @param p the point where the word starts from
     * @param direction whether the direction of the word is right-to-left
     * @return the word starting at the given coordinates
     */
    // TODO: wouldn't it be easier to just scan each row/column instead? Making sets of 2+ letter words?
    private PlacedWord getWordStartingFrom(Board aBoard, Point p, Direction direction){
        //initialize a string with the first character of the word
        ArrayList<BoardTile> wordTiles = new ArrayList<>();
        wordTiles.add(aBoard.boardTileTable.getTile(p));
        int index = 1;

        // Going in the direction of the word, add each character to the word
        if(direction == Direction.RIGHT){
            while (p.x+index < BOARD_SIZE && aBoard.boardTileTable.isTaken(new Point(p.x + index, p.y))){
                wordTiles.add(aBoard.boardTileTable.getTile(new Point(p.x + index, p.y)));
                index+=1;
            }
        }else{
            while (p.y+index < BOARD_SIZE && aBoard.boardTileTable.isTaken(new Point(p.x , p.y + index))){
                wordTiles.add(aBoard.boardTileTable.getTile(new Point(p.x, p.y + index)));
                index+=1;
            }
        }

        return new PlacedWord(wordTiles);
    }

    /**
     * Finds all words after a word placement
     * @author Timothy Kennedy
     *
     * @param placeEvent The event containing the placement details
     * @return the list of words originating from the word placement
     */
    // FIXME: Is this function placing words down? it shouldn't. it seems to be lacking cohesion
    private List<PlacedWord> allBoardWords(BoardPlaceEvent placeEvent){
        // FIXME: Big function, cohesion could be improved (using helper methods)
        Direction direction = placeEvent.getDirection();
        List<Tile> word = placeEvent.getPlacedTiles();
        int row = placeEvent.getWordOrigin().y;
        int col = placeEvent.getWordOrigin().x;

        //make a copy of the board
        Board boardCopy = this.copy();
        Set<BoardTile> newTakenTiles = new HashSet<>();
        ArrayList<PlacedWord> words = new ArrayList<>();

        // Place word on the copy of the board
        for(int index = 0; index < word.size(); index++){
            Point location = new Point(col+(direction==Direction.RIGHT ? index : 0),
                    row+(direction==Direction.DOWN ? index : 0));
            boardCopy.boardTileTable.setLetter(location, word.get(index).letter());

//            BoardTile takenTile = new BoardTile(location);
//            takenTile.setLetter(boardTileTable.getTile(location).getLetter());
//            newTakenTiles.add(takenTile);
        }

        // FIXME: this is doing the letter duplication
//         Get all taken tiles FIXME: could probably only add new taken tiles, by making hashset persistent?
        for(int x = 0; x < BOARD_SIZE; x++){
            for(int y = 0; y < BOARD_SIZE; y++){
                Point coords = new Point(x,y);
                if(boardTileTable.isTaken(coords)){ // Add a copy of the tile at this location to the hashset
                    BoardTile takenTile = new BoardTile(coords);
                    takenTile.setLetter(boardTileTable.getTile(coords).getLetter());
                    newTakenTiles.add(takenTile);
                }
            }
        }

        // TODO: not convinced this works yet would need to test rigorously some other time
        // Find words
        for (BoardTile tile:newTakenTiles){
            // If tile is the start of a word, IE: no letters before it, and 1+ letters after it, add the word starting from that position to the list of words
            if ((!boardCopy.isTaken(new Point(tile.getX() - 1,tile.getY())) || tile.getX() == 0)
                    && tile.getX() < 14
                    && boardCopy.isTaken(new Point(tile.getX() + 1,tile.getY()))) {
                words.add(getWordStartingFrom(boardCopy,
                        new Point(tile.getX(), tile.getY()),
                        Direction.RIGHT));
            }
            if ((!boardCopy.isTaken(new Point(tile.getX(),tile.getY()-1))
                    || tile.getY() == 0) && tile.getY() < 14
                    && boardCopy.isTaken(new Point(tile.getX(),tile.getY()+1))) {
                words.add(getWordStartingFrom(boardCopy,
                        new Point(tile.getX(), tile.getY()),
                        Direction.DOWN));
            }
        }
        return words;
    }

    /**
     * Returns a copy of the board, copying over an equal (but different) internal boardTileTable.
     * @return A copy of this board.
     */
    private Board copy() {
        Board copiedBoard = new Board(false);
        copiedBoard.boardTileTable = this.boardTileTable.copy();
        return copiedBoard;
    }

    /**
     * Calculates the score of a given word placement, based off the value of the letters and tile multipliers
     *
     * @author Vladimir Kovacina
     * @author Timothy Kennedy
     * @author Alex
     *
     * @return int The score of the word
     */
    private int getTurnScore(BoardPlaceEvent placeEvent){
        int turnScore = 0;

        // TODO: Parameter should probably be new words, not concise atm
        for (PlacedWord newWord:getNewWords(placeEvent)) {
            int wordScore = 0;
            int multiplier = 1;

            for (BoardTile tile:newWord.getTiles()) {
                    switch (tile.getType()){
                        case X2LETTER:
                            wordScore+=tile.getLetter().getScore()*2;
                            break;
                        case X3LETTER:
                            wordScore+=tile.getLetter().getScore()*3;
                            break;
                        case X2WORD: case START:
                            wordScore+=tile.getLetter().getScore();
                            multiplier*=2;
                            break;
                        case X3WORD:
                            wordScore+=tile.getLetter().getScore();
                            multiplier*=3;
                            break;
                        default:
                            wordScore+=tile.getLetter().getScore();
                            break;
                    }
            }
            turnScore += wordScore*multiplier;
        }

        // Set placed tiles to blank type (to disable bonus types on subsequent turns)
        Direction dir = placeEvent.getDirection();
        int row = placeEvent.getWordOrigin().y;
        int col = placeEvent.getWordOrigin().x;
        for(int index = 0; index < placeEvent.getPlacedTiles().size(); index++){
            boardTileTable.setType(new Point(
                    col+(dir == Direction.RIGHT ? index : 0),
                    row+(dir == Direction.DOWN ? index : 0)),
                    BoardTile.Type.BLANK);
        }

        return turnScore;
    }

    /**
     * Gets a list of all new words created by a word placement
     *
     * @param placementEvent Event containing relevant information on placement
     * @return a list of all new words
     */
    List<PlacedWord> getNewWords(BoardPlaceEvent placementEvent){
        // All words in the board - words that were in the board last turn.
        List<PlacedWord> newWords = new ArrayList<>(allBoardWords(placementEvent));
        for (PlacedWord aWord:currentWords) {
            newWords.remove(aWord);
        }
        return newWords;
    }

    public BoardTile getBoardTile(Point p){
        return boardTileTable.getTile(p);
    }

    /**
     * Returns the string representation of its internal BoardTile2DTable
     *
     * @return a string representation of the board
     */
    @Override
    public String toString() {
        return boardTileTable.toString();
    }
}
