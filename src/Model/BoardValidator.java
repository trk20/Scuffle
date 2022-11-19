package Model;

import ScrabbleEvents.ModelEvents.BoardPlaceEvent;

import java.awt.*;
import java.util.List;

import static Model.Board.START_TILE_POINT;

/**
 * Handles tile placement validation for Board models,
 * Does not indicate what caused an invalid placement, could be added in future versions.
 * Should not be able to modify the board!
 *
 * @author Alex
 * @version NOV-18
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
    public BoardValidator(Board board){
        this.boardToValidate = board;
        this.dictionary = new DictionaryHandler();
    }

    /**
     * Checks if a word can be placed with the desired placement.
     * Checks include: Are any tiles placed outside the board?
     *                 Then,
     *                 For the first word, is it on the start tile?
     *                 For any subsequent word, is it connected to other tiles?
     *
     * @param placementEvent The event representing a placement attempt
     * @return True if the word passes all the checks, false otherwise.
     */
    public boolean isValidLocation(BoardPlaceEvent placementEvent) {
        if(!areNewTilesInBoard(placementEvent)) return false;

        if(isBoardEmpty()){
            return isPlacedOnStart(placementEvent);
        } else return isPlacedNextToWord(placementEvent);
    }

    /**
     * Checks if a tile would be adjacent to the placed word
     * @param placementEvent The event representing a placement attempt
     * @return True if a tile would be adjacent to the placed word
     */
    private boolean isPlacedNextToWord(BoardPlaceEvent placementEvent) {
        // Unpack relevant event info
        Point wordOrigin = placementEvent.wordOrigin();
        Board.Direction placementDirection = placementEvent.direction();
        int numTiles = placementEvent.placedTiles().size();

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
    private boolean isAdjacentToTile(Point point){
        if(isTaken(point)) return true;  // If overlapping, then adjacent (transitive relation)

        // Translate in the four cardinal directions to check for a tile adjacent to the given point:
        // Ignoring exceptions because placements outside the board simply indicate a non-adjacent tile
        try{ if(isTaken(new Point(point.x+1, point.y))) return true; }
        catch (IndexOutOfBoundsException ignored) {}

        try{ if(isTaken(new Point(point.x-1, point.y))) return true; }
        catch (IndexOutOfBoundsException ignored) {}

        try{ if(isTaken(new Point(point.x, point.y+1))) return true; }
        catch (IndexOutOfBoundsException ignored) {}

        try{ if(isTaken(new Point(point.x, point.y-1))) return true; }
        catch (IndexOutOfBoundsException ignored) {}
        // No tests passed, must not be adjacent.
        return false;
    }


    /**
     * Checks if all placed tiles are within the board's limits.
     * @param placementEvent  The event representing a placement attempt
     * @return True if all placed tiles are within the board's limit, false if any are not.
     */
    private boolean areNewTilesInBoard(BoardPlaceEvent placementEvent) {
        // Unpack relevant event info
        Point wordOrigin = placementEvent.wordOrigin();
        Board.Direction placementDirection = placementEvent.direction();
        int numTiles = placementEvent.placedTiles().size();

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
        catch(IndexOutOfBoundsException e){
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
        Point wordOrigin = placementEvent.wordOrigin();
        Board.Direction placementDirection = placementEvent.direction();
        int numTiles = placementEvent.placedTiles().size();

        // Check if any tile is on start tile, using given info
        for(int i = 0; i < numTiles; i++){
            if(placementDirection == Board.Direction.RIGHT){ // Increment Col (Right from origin)
                if(START_TILE_POINT.equals(new Point(wordOrigin.x+i, wordOrigin.y))) return true;
            } else { // Decrement row (y)
                if(START_TILE_POINT.equals(new Point(wordOrigin.x, wordOrigin.y+i))) return true;
            }
        }
        // No tile was on the start tile
        return false;
    }

    /**
     * Asks the validator's board if
     * a coordinate in the validator's board is taken (x = col, y = row), starting top left.
     * @param p A point coordinate in the board to check
     * @return True if a tile is placed at that location, false otherwise.
     */
    private boolean isTaken(Point p){
        return boardToValidate.isTaken(p);
    }

    /**
     * Asks the validator's board if
     * no words have been placed on the board yet.
     * @return True if the board is empty, false otherwise.
     */
    private boolean isBoardEmpty(){
        return boardToValidate.isBoardEmpty();
    }

    public boolean isInvalidWordInBoard(List<BoardWord> currentWords) {
        for (BoardWord curWord: currentWords)
            if (!dictionary.isValidWord(curWord.toString())){
                return true;
            }
        return false; // No invalid words detected
    }
}
