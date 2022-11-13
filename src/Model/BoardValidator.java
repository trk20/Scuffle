package Model;

import Events.BoardPlaceEvent;

import java.awt.*;

import static Model.ScrabbleModel.BOARD_SIZE;

/**
 * Handles tile placement validation for Board models,
 * Does not indicate what caused an invalid placement, could be added in future versions.
 * Delegates some tasks to board, could possibly loosen coupling in the future.
 *
 * @author Alex
 * @version NOV-12
 */
// TODO: (M3) Valid placement may throw exceptions to indicate failure type
//  (subclasses of InvalidPlacementException?),
//  alternatively could set flags in the validator to get later?
public class BoardValidator {
    private final Board boardToValidate;
    private final DictionaryHandler dictionary;

    /**
     * Constructor for BoardValidator, initializes fields.
     *
     * @param board reference to the board to validate.
     */
    BoardValidator(Board board){
        this.boardToValidate = board;
        this.dictionary = new DictionaryHandler();
    }

    /**
     * Checks if a word can be placed with the desired placement.
     * Checks include: Are any tiles placed outside the board?
     *                 For the first word, is it on the start tile?
     *                 For subsequent words, are they connected to other tiles?
     *                 Are any new words created invalid?
     *
     * @param placementEvent The event representing a placement attempt
     * @return True if the word passes all the checks, false otherwise.
     */
    public boolean isValidPlacement(BoardPlaceEvent placementEvent) {
        System.out.println("Checking validatity");

        // Attempt to go from the least intensive checks, to most intensive
        if(!newTilesAreInBoard(placementEvent)) return false;
        System.out.println("In board");

        if(isBoardEmpty()){
            if(!isPlacedOnStart(placementEvent)) return false;
        } else if (!isPlacedNextToWord(placementEvent)) return false;

        System.out.println("Passed adjacency test");

        if(!newWordsAreValid(placementEvent)) return false;

        System.out.println("All valid words");

        return true; // All tests passed
    }

    /**
     * Checks if a tile would be adjacent to the placed word
     * @param placementEvent The event representing a placement attempt
     * @return True if a tile would be adjacent to the placed word
     */
    private boolean isPlacedNextToWord(BoardPlaceEvent placementEvent) {
        // Unpack relevant event info
        Point wordOrigin = placementEvent.getWordOrigin();
        Board.Direction placementDirection = placementEvent.getDirection();
        int numTiles = placementEvent.getPlacedTiles().size();

        // Check if any tile is on adjacent to another tile, using given info
        for(int i = 0; i < numTiles; i++){
            if(placementDirection == Board.Direction.RIGHT){ // Increment Col (Right from origin)
                if(isAdjacentToTile(new Point((int) (wordOrigin.getX()+i), (int) wordOrigin.getY()))) return true;
            } else { // Decrement row (y)
                if(isAdjacentToTile(new Point((int) wordOrigin.getX(), (int) (wordOrigin.getY()+i)))) return true;
            }
        }

        // No tile was adjacent to the placed word
        return false;
    }

    /**
     * Checks if a given point is adjacent to an existing tile in the board to validate.
     * If the point falls on an existing tile, it will end up being adjacent to it,
     * The tile placed will be placed after it, or another adjacent tile.
     *
     * @param point The point coordinates of a possible new tile in the board.
     * @return True if the point is adjacent to an existing tile
     */
    private boolean isAdjacentToTile(Point point) {
        if(isTaken(point)) return true;  // If overlapping, then adjacent (transitive relation)
        // Translate in the four cardinal directions to check for a tile adjacent to the given point:
        if(isTaken(new Point(point.x+1, point.y))) return true;
        if(isTaken(new Point(point.x-1, point.y))) return true;
        if(isTaken(new Point(point.x, point.y+1))) return true;
        if(isTaken(new Point(point.x, point.y-1))) return true;
        // No tests passed, must not be adjacent.
        return false;
    }


    /**
     * Checks if all placed tiles are within the board's limits.
     * @param placementEvent  The event representing a placement attempt
     * @return True if all placed tiles are within the board's limit, false if any are not.
     */
    private boolean newTilesAreInBoard(BoardPlaceEvent placementEvent) {
        // Unpack relevant event info
        Point wordOrigin = placementEvent.getWordOrigin();
        Board.Direction placementDirection = placementEvent.getDirection();
        int numTiles = placementEvent.getPlacedTiles().size();

        int overlaps = 0; // Place tile one further if a tile already occupies its spot

        // Check if any tile is outside the board, using given info
        try {
            for (int i = 0; i < numTiles; i++) {
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
            }
        }
        catch(InvalidPlacementException e){
            return false;
        }
        // No tile was outside the board
        return true;
    }

    /**
     * Checks if the word to be placed will cover the start tile
     * @return True if one of the tiles is placed on the start tile, false otherwise.
     */
    private boolean isPlacedOnStart(BoardPlaceEvent placementEvent) {
        // TODO: this unpack/check loop (mostly) repeats itself across checks. Might be refactorable... lambdas maybe?

        // Unpack relevant event info
        Point wordOrigin = placementEvent.getWordOrigin();
        Board.Direction placementDirection = placementEvent.getDirection();
        int numTiles = placementEvent.getPlacedTiles().size();

        // Check if any tile is on start tile, using given info
        for(int i = 0; i < numTiles; i++){
            if(placementDirection == Board.Direction.RIGHT){ // Increment Col (Right from origin)
                if(isStartTile(new Point((int) (wordOrigin.getX()+i), (int) wordOrigin.getY()))) return true;
            } else { // Decrement row (y)
                if(isStartTile(new Point((int) wordOrigin.getX(), (int) (wordOrigin.getY()+i)))) return true;
            }
        }
        // No tile was on the start tile
        return false;
    }

    /**
     * Check if a given point is outside the board.
     *
     * @param p The point coordinates of a placement in the board.
     * @return True if the point is outside the bounds of the board, false otherwise.
     */
    private boolean isOutsideBoard(Point p) {
        return p.getX() >= BOARD_SIZE || p.getY() >= BOARD_SIZE;
    }

    /**
     * Asks the validator's board if
     * each word created as a result of the placement is a valid word in the model's dictionary
     * @return True if each new word is valid.
     */
    private boolean newWordsAreValid(BoardPlaceEvent placementEvent) {
        for (PlacedWord newWord: boardToValidate.getNewWords(placementEvent))
            if (!dictionary.isValidWord(newWord.toString())) return false; // invalid word
        return true; // All words valid
    }

    /**
     * Asks the validator's board if
     * a coordinate in the validator's board is taken (x = col, y = row), starting top left.
     * @param p A point coordinate in the board to check
     * @return True if a tile is placed at that location, false otherwise.
     * @throws InvalidPlacementException if the checked placement is outside the board.
     */
    private boolean isTaken(Point p) throws InvalidPlacementException {
        if (isOutsideBoard(p)) throw new InvalidPlacementException("Placement is outside board");
        return boardToValidate.isTaken(p);
    }

    /**
     * Asks the validator's board if
     * the given point on the board is a start tile
     *
     * @param p the coordinates of the tile to check for the "start" type
     * @return True if the given board tile is the board's start tile, false otherwise.
     */
    private boolean isStartTile(Point p) {
        return boardToValidate.isStartTile(p);
    }

    /**
     * Asks the validator's board if
     * no words have been placed on the board yet.
     * @return True if the board is empty, false otherwise.
     */
    private boolean isBoardEmpty(){
        return boardToValidate.isBoardEmpty();
    }

    // TODO: Make a hierarchy of these in seperate classes later

    /**
     * Exception thrown during invalid placements
     */
    public class InvalidPlacementException extends RuntimeException {
        public InvalidPlacementException(String placement_is_outside_board) {
        }
    }
}
