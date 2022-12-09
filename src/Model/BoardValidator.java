package Model;

import ScrabbleEvents.ModelEvents.BoardPlaceEvent;

import java.awt.*;
import java.util.List;

import static Model.Board.START_TILE_POINT;

/**
 * Handles tile placement validation for Board models,
 * Indicates results of a validation through its Status enum.
 * Should not be able to modify the board!
 *
 * @author Alex, Kieran
 * @version NOV-18
 */
public class BoardValidator {
    /** Enum for different possible validation outcomes, with associated error messages (if applicable) */
    public enum Status{
        SUCCESS (null),
        OUT_OF_BOUNDS("Error: PLACEMENT WAS OUT OF BOUNDS"),
        NOT_ON_START("Error: PLACEMENT DOES NOT INCLUDE START TILE"),
        NOT_NEXT_TO_WORD("Error: PLACEMENT WAS NOT NEXT TO ANOTHER WORD"),
        INVALID_WORD("Error: PLACEMENT DID NOT FORM VALID WORDS");

        private final String errorMessage;
        Status(String errorMessage){
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage(){
            return errorMessage;
        }
    }
//    private final Board boardToValidate;
    private final static DictionaryHandler dictionary = new DictionaryHandler();

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
    public Status isValidLocation(Board boardToValidate, BoardPlaceEvent placementEvent) {
        if(!areNewTilesInBoard(boardToValidate, placementEvent)) return Status.OUT_OF_BOUNDS;

        if(isBoardEmpty(boardToValidate)){
            return isPlacedOnStart(placementEvent) ? Status.SUCCESS : Status.NOT_ON_START;
        } else return isPlacedNextToWord(boardToValidate, placementEvent) ? Status.SUCCESS : Status.NOT_NEXT_TO_WORD;
    }

    /**
     * Checks if the words in the board are valid, according to the game's dictionary.
     * @param currentWords List of words currently in the board
     * @return True if an invalid word is in the board
     */
    public Status isInvalidWordInBoard(List<BoardWord> currentWords) {
        for (BoardWord curWord: currentWords)
            if (!dictionary.isValidWord(curWord.toString())){
                return Status.INVALID_WORD;
            }
        return Status.SUCCESS; // No invalid words detected
    }

    /**
     * Checks if a tile would be adjacent to the placed word
     * @param placementEvent The event representing a placement attempt
     * @return True if a tile would be adjacent to the placed word
     */
    private boolean isPlacedNextToWord(Board boardToValidate, BoardPlaceEvent placementEvent) {
        // Unpack relevant event info
        Point wordOrigin = placementEvent.wordOrigin();
        Board.Direction placementDirection = placementEvent.direction();
        int numTiles = placementEvent.placedTiles().size();

        // Check if any tile is on adjacent to another tile, using given info
        for(int i = 0; i < numTiles; i++){
            if(placementDirection == Board.Direction.RIGHT){ // Increment Col (Right from origin)
                if(isAdjacentToTile(boardToValidate, new Point((int) (wordOrigin.getX()+i), (int) wordOrigin.getY())))
                    return true;
            } else { // Decrement row (y)
                if(isAdjacentToTile(boardToValidate, new Point((int) wordOrigin.getX(), (int) (wordOrigin.getY()+i))))
                    return true;
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
    private boolean isAdjacentToTile(Board boardToValidate, Point point){
        if(isTaken(boardToValidate, point)) return true;  // If overlapping, then adjacent (transitive relation)

        // Translate in the four cardinal directions to check for a tile adjacent to the given point:
        // Ignoring exceptions because placements outside the board simply indicate a non-adjacent tile
        try{ if(isTaken(boardToValidate, new Point(point.x+1, point.y))) return true; }
        catch (IndexOutOfBoundsException ignored) {}

        try{ if(isTaken(boardToValidate, new Point(point.x-1, point.y))) return true; }
        catch (IndexOutOfBoundsException ignored) {}

        try{ if(isTaken(boardToValidate, new Point(point.x, point.y+1))) return true; }
        catch (IndexOutOfBoundsException ignored) {}

        try{ if(isTaken(boardToValidate, new Point(point.x, point.y-1))) return true; }
        catch (IndexOutOfBoundsException ignored) {}
        // No tests passed, must not be adjacent.
        return false;
    }

    /**
     * Checks if all placed tiles are within the board's limits.
     * @param placeEvent  The event representing a placement attempt
     * @return True if all placed tiles are within the board's limit, false if any are not.
     */
    private boolean areNewTilesInBoard(Board boardToValidate, BoardPlaceEvent placeEvent) {
        // Unpack event info
        List<Tile> word = placeEvent.placedTiles();
        Board.Direction placementDirection = placeEvent.direction();

        // First tile, no translation
        Point nextPoint = placeEvent.wordOrigin();

        // Check if any tile is outside the board, using given info
        try {
            for (int i = 0; i < word.size(); i++) {
                // Place tile at first available location
                Point firstFree = boardToValidate.getFirstNonTakenPoint(placementDirection, new Point(nextPoint));
                isTaken(boardToValidate, firstFree); // Will check to see if it causes an out-of-bounds exception

                // nextPoint is checked one after last place
                if (placementDirection == Board.Direction.RIGHT) firstFree.translate(1, 0);
                else firstFree.translate(0, 1);
                nextPoint = new Point(firstFree);
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
    private boolean isTaken(Board boardToValidate, Point p){
        return boardToValidate.isTaken(p);
    }

    /**
     * Asks the validator's board if
     * no words have been placed on the board yet.
     * @return True if the board is empty, false otherwise.
     */
    private boolean isBoardEmpty(Board boardToValidate){
        return boardToValidate.isBoardEmpty();
    }
}
