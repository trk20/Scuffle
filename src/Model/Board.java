package Model;

import ScrabbleEvents.ModelEvents.BoardPlaceEvent;

import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static Model.ScrabbleModel.BOARD_SIZE;

/**
 * Class that handles the Model.Board for the Scrabble game
 *
 * @author Vladimir Kovacina
 * @author Timothy Kennedy
 * @author Alex
 * @version NOV-22
 */
 
public class Board implements Serializable, Cloneable {

    /** Enum for board placement possibilities */
    public enum Direction{
        DOWN("↓"), RIGHT("→");

        private final String dirStr;

        Direction(String s){this.dirStr = s;}

        @Override
        public String toString() {return dirStr;}

    }
    public static final int MIN_WORD_SIZE = 2;
    public static final Point START_TILE_POINT = new Point(BOARD_SIZE/2, BOARD_SIZE/2);

    /** Takes care of validating the board */
    private static final BoardValidator validator = new BoardValidator();
    private Grid2DArray<BoardTile> boardGrid;
    private List<BoardWord> lastPlacedWords;



    /**
     * Constructor for a Board object

     *
     * @param isPremiumBoard If true, will add premium tiles to the board (point multipliers)
     */
    public Board(boolean isPremiumBoard){
        lastPlacedWords = new ArrayList<>();
        initializeBlankGrid();  // Initialize tiles in boardGrid


        if(isPremiumBoard) setPremiumTiles();
        boardGrid.get(START_TILE_POINT).setType(BoardTile.Type.START); // Set start tile
    }

    /**
     * Get a board tile at the given coordinates
     *
     * @param p The point coordinates of the tile in the board grid
     * @return The tile at coordinate p
     */
    public BoardTile getBoardTile(Point p){
        return boardGrid.get(p);
    }

    /**
     * Places a word on the board, and calculates the resulting score from the placement.
     * For invalid placements, will cancel the operation and indicate an exception.
     *
     * @param placeEvent The event containing the placement details
     * @return The score resulting from the placement (Post-Condition: score >= 0).
     */
    public int placeWord(BoardPlaceEvent placeEvent){
        // Pre-condition: placeEvent is a valid placement
        //noinspection AssertWithSideEffects
        assert(isValidPlacement(placeEvent) == BoardValidator.Status.SUCCESS);
        // Place word on board, save resulting state
        setWordTiles(placeEvent);
        List<BoardWord> curWords = getCurrentWords();
        List<BoardWord> turnWords = getPlacedWords(curWords);
        lastPlacedWords = curWords; // Store words from this turn (for next placement)

        return getPlacedScore(turnWords);
    }

    /**
     * Checks if a placement is valid, then indicates the result of the validation.
     * Board state before and after this method should be identical!
     *
     * @param placeEvent the placement event to evaluate
     * @return The status of the validation: Success if it passed, otherwise the cause of failure.
     *
     * @author Timothy Kennedy
     * @author Alex
     */
    public BoardValidator.Status isValidPlacement(BoardPlaceEvent placeEvent){
        BoardValidator.Status currentStatus;
        currentStatus = validator.isValidLocation(this, placeEvent); // Pass board copy instead?

        // Location is valid, check if words are valid
        if(currentStatus == BoardValidator.Status.SUCCESS){
            // Save board state
            Grid2DArray<BoardTile> savedBoardGrid = copySelfGrid();
            // Place word on board, check if it creates invalid words
            setWordTiles(placeEvent);
            List<BoardWord> curWords = getCurrentWords();
            currentStatus = validator.isInvalidWordInBoard(curWords);
            // Load board state (Prevent mutation due to validation!)
            boardGrid = savedBoardGrid;
        }

        return currentStatus;
    }

    /**
     * Copy its own boardGrid, and return it.
     * Returns a deep copy, to be able to save it without mutation.
     * @return A deep copy of the current boardGrid state.
     */
    private Grid2DArray<BoardTile> copySelfGrid() {
        int boardSize = boardGrid.getSize();
        Grid2DArray<BoardTile> gridCopy = new Grid2DArray<>(boardSize);

        for (int x =0; x<boardSize; x++) {
            for(int y =0; y<boardSize; y++){
                Point p = new Point(x,y);
                // Deep copy constructor for board tile
                BoardTile tileCopy = new BoardTile(boardGrid.get(p));
                // Set copied tile in new grid
                gridCopy.set(p, tileCopy);
            }
        }

        return gridCopy;
    }

    /**
     * Returns the string representation of the board's internal grid
     *
     * @return a string representation of the board
     */
    @Override
    public String toString() {
        return boardGrid.toString();
    }

    /**
     * Checks if a coordinate in the board is taken (x = col, y = row), starting top left.
     * @param p A point coordinate in the board to check
     * @return True if a tile is placed at that location, false otherwise.
     */
    boolean isTaken(Point p){
        return boardGrid.get(p).isTaken();
    }

    /**
     * Checks if no words have been placed on the board yet.
     * @return True if the board is empty, false otherwise.
     */
    boolean isBoardEmpty(){
        return ! boardGrid.get(START_TILE_POINT).isTaken();
    }

    /**
     * Gets a list of all new words created by a word placement
     *
     * @return a list of all new words
     */
    List<BoardWord> getPlacedWords(List<BoardWord> currentWords){
        // All words in the board - words that were in the board last turn.
        List<BoardWord> newWords = new ArrayList<>(currentWords);
        for (BoardWord word:lastPlacedWords) {
            newWords.remove(word);
        }

        return newWords;
    }

    /**
     * Keeps translating point in the placement direction until it is not overlapping with a tile in the board.
     *
     * @param placementDirection The direction in which the point's word is placed
     * @param placeAttempt The initial point where the placement is attempted
     * @return The first point with no overlap
     */
    Point getFirstNonTakenPoint(Direction placementDirection, Point placeAttempt) {
        Point placementLocation = new Point(placeAttempt);

        try {
            if (placementDirection == Direction.RIGHT) {
                // Increment Col (x), until no overlap
                while (isTaken(placementLocation)) {
                    placementLocation.translate(1, 0);
                }
            } else {
                // Increment row (y), until no overlap
                while (isTaken(placementLocation)) {
                    placementLocation.translate(0, 1);
                }
            }
        } catch (IndexOutOfBoundsException e){
            return new Point(-1, -1); // Out of bounds point
        }

        return placementLocation;
    }

    /**
     * Places tiles in the board, skipping over tiles that are already placed.
     * Precondition: assumes valid placement.
     *
     * @param placeEvent The event containing the placement details
     */
    private void setWordTiles(BoardPlaceEvent placeEvent) {
        // Unpack event info
        List<Tile> word = placeEvent.placedTiles();
        Direction placementDirection = placeEvent.direction();

        // First tile, attempt to place at origin
        Point placeLocation = placeEvent.wordOrigin();

        // Place tiles in the board, skipping tiles that are already placed.
        for (Tile tile : word) {
            // Place tile at first available location
            placeLocation = getFirstNonTakenPoint(placementDirection, placeLocation);
            placeTile(placeLocation, tile.getLetter());
        }
    }

    /**
     * Initialize the board grid with blank board tiles.
     */
    private void initializeBlankGrid(){
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
     *Set the board up with no premium tiles, this is used for xml configurations
     * @author Vladimir Kovacina
     */

    public void setDefaultBoard(){
        initializeBlankGrid();
        boardGrid.get(START_TILE_POINT).setType(BoardTile.Type.START); // Set start tile
    }

    /**
     * Set premium score tiles in the board.
     * Currently, randomly places them on the board.
     */
    // TODO: M4 will be using XML loading for the configuration
    private void setPremiumTiles() {
        Random r = new Random();
        for (int x = 0; x< BOARD_SIZE; x ++){
            for(int y = 0; y<BOARD_SIZE; y ++){
                Point p = new Point(x, y);
                // 1/14 chance to set a premium tile
                int randomInt = r.nextInt(14);
                if(randomInt == 1){
                    //Set multiplier tiles at random
                    boardGrid.get(p).setType(BoardTile.Type.values()[2+r.nextInt(4)]);
                }
            }
        }
    }

    /**
     * Method used to set the XML premium tiles that are read from the configuration file
     *
     * @author Vladimir Kovacina
     * @param x the col of the tile
     * @param y the row of the tile
     * @param type which premium tile type
     */

    public void setXMLPremiumTiles(int x, int y, String type){
        Point p = new Point(x, y);
        if(type.equals("2W")){
            boardGrid.get(p).setType(BoardTile.Type.values()[2]);
        }else if(type.equals("3W")){
            boardGrid.get(p).setType(BoardTile.Type.values()[3]);
        }else if(type.equals("2L")){
            boardGrid.get(p).setType(BoardTile.Type.values()[4]);
        }else if(type.equals("3L")){
            boardGrid.get(p).setType(BoardTile.Type.values()[5]);
        }else{
            boardGrid.get(p).setType(BoardTile.Type.values()[1]);
        }

    }

    /**
     * Place a tile in the board.
     * Precondition: The tile is not taken
     * @param p The point where the tile should be placed
     * @param letter the letter to place at the board location.
     */
    private void placeTile(Point p, Letter letter) {
        assert(!boardGrid.get(p).isTaken()); // Precondition: Tile is empty
        boardGrid.get(p).setLetter(letter);
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
    private int getPlacedScore(List<BoardWord> newWords){
        int turnScore = 0;

        // Sum the scores of each new word
        for (BoardWord newWord:newWords) {
            int wordSum = 0;
            int wordMulti = 1;

            // Check the scoring values of each tile
            for (BoardTile tile:newWord.getTiles()) {
                wordSum += getTileScore(tile);
                wordMulti *= getTileMulti(tile);
            }

            turnScore += wordSum*wordMulti;
        }

        // Set new word tiles to blank type (to disable bonus types on subsequent turns)
        for(BoardWord newWord: newWords){
            for (BoardTile tile:newWord.getTiles()) {
                tile.setType(BoardTile.Type.BLANK);
            }
        }

        return turnScore;
    }

    /**
     * Calculates the word multiplier of a given tile placement,
     * based on premium word multipliers at that location.
     *
     * @param tile The board tile to evaluate the score of
     * @return The score value of that tile
     */
    private int getTileMulti(BoardTile tile) {
        return switch (tile.getType()) {
            case START, X2WORD -> 2;
            case X3WORD -> 3;
            default -> 1;
        };
    }

    /**
     * Calculates the score of a given tile placement, based on
     * base letter score and premium letter score.
     *
     * @param tile The board tile to evaluate the score of
     * @return The score value of that tile
     */
    private int getTileScore(BoardTile tile) {
        return switch (tile.getType()) {
            case X2LETTER -> tile.getLetter().getScore() * 2;
            case X3LETTER -> tile.getLetter().getScore() * 3;
            default -> tile.getLetter().getScore();
        };
    }

    public ArrayList<BoardTile> getBoardTiles(){
        ArrayList<BoardTile> tiles = new ArrayList<>();
        for (int x = 0; x < 15; x++){
            for (int y = 0; y < 15; y++) {
                tiles.add(new BoardTile(boardGrid.get(new Point(x, y))));
            }
        }
        return tiles;
    }

    /**
     * Finds all words currently in the board
     *
     * @return the list of words in the board
     */
    public List<BoardWord> getCurrentWords(){
        List<BoardWord> curWords = new ArrayList<>();

        boolean readingColumns = true; // Choose to read columns first (arbitrary)
        List<BoardTile> sequentialTakenTiles = new ArrayList<>();

        /* Loop levels:
         * Iterate through two ways of reading words in the board;
         *  Iterate through entire board
         *   Iterate through entire col/row (cols first past)
         */
        for(int readDir = 0; readDir<2; readDir++){
            for(int i = 0; i < BOARD_SIZE; i++){
                for(int j = 0; j<BOARD_SIZE; j++){
                    // Increment coords based on reading strategy
                    Point p = readingColumns ? new Point(i,j) : new Point(j, i);

                    if(isTaken(p)) {
                        sequentialTakenTiles.add(getBoardTile(p));
                    } else {
                        // Sequence > min word size, then it represents a word in the board (possibly invalid)
                        if(sequentialTakenTiles.size() >= MIN_WORD_SIZE){
                            // Creates new BoardWord with a copied (important!) list
                            curWords.add(new BoardWord(new ArrayList<>(sequentialTakenTiles)));
                        }
                        // Sequence broken, clear list
                        sequentialTakenTiles.clear();
                    }
                }
            }
            // Second loop, read rows instead
            readingColumns = false;
        }

        return curWords;
    }

    public BoardTile getTile(Point p){
        return boardGrid.get(p);
    }

    private void setTile(Point p, BoardTile t){
        boardGrid.set(p, t);
    }

    public void setBoard(Board newBoard){
        for (int x = 0; x < 15; x++){
            for (int y = 0; y < 15; y++) {
                Point p = new Point(x,y);
                setTile(p, newBoard.getTile(p));

            }
        }
    }

    /**
     * Returns the score of a theoretical placement event without changing the board
     * For use by AI
     *
     * @param placeEvent the placement event to evaluate
     * @return the score of the placement event
     *
     * @author Timothy Kennedy
     */
    public int getPlacementScore(BoardPlaceEvent placeEvent) {
        // Store board state
        Grid2DArray<BoardTile> savedBoardGrid = copySelfGrid();
        // Place word on board, check if it creates invalid words
        setWordTiles(placeEvent);
        int score = getPlacedScore(getPlacedWords(getCurrentWords()));
        // Load board state (Prevent mutation)
        boardGrid = savedBoardGrid;
        return score;
    }

    private void setBoardGrid(Grid2DArray<BoardTile> boardGrid) {
        this.boardGrid = boardGrid;
    }

    private void setLastPlacedWords(List<BoardWord> lastPlacedWords) {
        this.lastPlacedWords = lastPlacedWords;
    }

    @Override
    public Board clone() {
        try {
            Board clone = (Board) super.clone();
            List<BoardWord> lastPlacedWordsClone = new ArrayList<>();

            for(BoardWord bw : lastPlacedWords){
                lastPlacedWordsClone.add(bw.clone());
            }

            clone.setBoardGrid(this.copySelfGrid());
            clone.setLastPlacedWords(lastPlacedWordsClone);


            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
