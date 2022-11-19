package Model;

import ScrabbleEvents.ModelEvents.BoardPlaceEvent;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Model.ScrabbleModel.BOARD_SIZE;

/**
 * Class that handles the Model.Board for the Scrabble game
 *
 * @author Vladimir Kovacina
 * @author Timothy Kennedy
 * @author Alex
 * @version NOV-18
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

    public static int MIN_WORD_SIZE = 2;
    private Grid2DArray<BoardTile> boardGrid; // COLUMN (x), ROW (y)
    private List<BoardWord> lastPlacedWords;
    /** Takes care of validating the board */
    private final BoardValidator validator;

    /**
     * Constructor for a Model.Board object
     */
    public Board(boolean randomBoard){
        validator = new BoardValidator(this);
        lastPlacedWords = new ArrayList<>();

        // TODO: add randomisation back in
//        if(randomBoard) boardTileTable.randomiseTable();

        // Initialize the start tile in the middle of the board
        initializeGrid();
        Point middleOfBoard = new Point(BOARD_SIZE/2, BOARD_SIZE/2);
        boardGrid.get(middleOfBoard).setType(BoardTile.Type.START);
    }

    /**
     * Initialize the board grid with blank board tiles.
     */
    private void initializeGrid(){
        boardGrid = new Grid2DArray<>(BOARD_SIZE);
        // Fill tile array
        for (int x = 0; x< BOARD_SIZE; x ++){
            for(int y = 0; y<BOARD_SIZE; y ++){
                Point p = new Point(x, y);
                boardGrid.set(p, new BoardTile(p));
            }
        }
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

        // Place word on board
        Grid2DArray<BoardTile> savedBoardGrid = boardGrid.copy();
        setWordTiles(placeEvent);
        List<BoardWord> curWords = getCurrentWords();
        // If placement results in invalid words, abort placement, reload previous state
        if(validator.isInvalidWordInBoard(curWords)){
            boardGrid = savedBoardGrid;
            return -1;
        }

        // Calculate score
        int score = getTurnScore(getNewWords(curWords));

        // Store words from this turn (for new word difference)
        lastPlacedWords = curWords;
        return score;
    }

    /**
     * Places tiles in the board, skipping over tiles that are already placed.
     * Precondition: assumes valid placement.
     *
     * @param placeEvent The event containing the placement details
     */
    private void setWordTiles(BoardPlaceEvent placeEvent) throws BoardValidator.InvalidPlacementException {
        // Unpack relevant event info
        Point wordOrigin = placeEvent.wordOrigin();
        Direction placementDirection = placeEvent.direction();
        List<Tile> word = placeEvent.placedTiles();
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
        boardGrid.get(p).setLetter(letter);
    }


    /**
     * Checks if a coordinate in the board is taken (x = col, y = row), starting top left.
     * @param p A point coordinate in the board to check
     * @return True if a tile is placed at that location, false otherwise.
     */
    boolean isTaken(Point p) {
        return boardGrid.get(p).isTaken();
    }

    /**
     * Checks if no words have been placed on the board yet.
     * @return True if the board is empty, false otherwise.
     */
    // Package private: only accessible by model classes (for the validator)
    boolean isBoardEmpty(){
        return getCurrentWords().size() == 0;
    }

    /**
     * Checks if the given board tile is the board's start tile
     *
     * @param p the coordinates of the tile to check for the "start" type
     * @return True if the given board tile is the board's start tile, false otherwise.
     */
    boolean isStartTile(Point p) {
        return boardGrid.get(p).getType() == BoardTile.Type.START;
    }

    /**
     * Finds all words currently in the board
     *
     * @return the list of words in the board
     */
    // TODO: could use more cohesion probably
     private List<BoardWord> getCurrentWords(){
         List<BoardWord> curWords = new ArrayList<>();

         boolean readingColumns = true; // Choose to read columns first (for condition clarity)
         List<BoardTile> takenTiles = new ArrayList<>();

         // Iterate through two ways of reading words in the board
         for(int readDir = 0; readDir<2; readDir++){
             // Iterate through entire board
             for(int i = 0; i < BOARD_SIZE; i++){
                 // Iterate through entire col/row (depending on direction)
                 for(int j = 0; j<BOARD_SIZE; j++){
                     // Check vertical words on first pass, horizontal on second
                     Point p = readingColumns ? new Point(i,j) : new Point(j, i);

                     if(isTaken(p)) {
                         takenTiles.add(getBoardTile(p));
                     } else {
                         if(takenTiles.size() >= MIN_WORD_SIZE){
                             // Creates new BoardWord with a copied (important!) list
                             curWords.add(new BoardWord(new ArrayList<>(takenTiles)));
                         }
                         // Clear taken tiles to check for another word in col/row
                         takenTiles.clear();
                     }
                 }
             }
             // Second loop, read rows instead
             readingColumns = false;
         }

         return curWords;
    }

    /**
     * Calculates the score of a given word placement, based off the value of the letters and tile multipliers
     * Precondition: Word has been placed for this turn
     *
     * @author Vladimir Kovacina
     * @author Timothy Kennedy
     * @author Alex
     *
     * @return int The score of the word
     */
    private int getTurnScore(List<BoardWord> newWords){
        int turnScore = 0;

        // TODO: Parameter should probably be new words, not concise atm
        for (BoardWord newWord:newWords) {
            int wordScore = 0;
            int multiplier = 1;

            for (BoardTile tile:newWord.tiles()) {
//                System.out.println(tile);
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
//            System.out.println("wordScore: "+wordScore+", multi: "+multiplier);
            turnScore += wordScore*multiplier;
        }

        // Set new word tiles to blank type (to disable bonus types on subsequent turns)
        for(BoardWord newWord: newWords){
            for (BoardTile tile:newWord.tiles()) {
                tile.setType(BoardTile.Type.BLANK);
            }
        }

        return turnScore;
    }

    /**
     * Gets a list of all new words created by a word placement
     *
     * @return a list of all new words
     */
    List<BoardWord> getNewWords(List<BoardWord> currentWords){
        // All words in the board - words that were in the board last turn.
        List<BoardWord> newWords = new ArrayList<>(currentWords);
        for (BoardWord word:lastPlacedWords) {
            newWords.remove(word);
        }

        return newWords;
    }

    public BoardTile getBoardTile(Point p){
        return boardGrid.get(p);
    }

    /**
     * Returns the string representation of its internal BoardTile2DTable
     *
     * @return a string representation of the board
     */
    @Override
    public String toString() {
        return boardGrid.toString();
    }
}
