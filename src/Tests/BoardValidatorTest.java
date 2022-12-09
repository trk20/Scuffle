package Tests;

import Model.*;
import ScrabbleEvents.ModelEvents.BoardPlaceEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests the Board Validator Class, more specifcally the isValidPlacement method
 *
 * @author Vladimir Kovacina
 * @version NOV-19 2022
 */

class BoardValidatorTest {

    private Board board;
    private List<Tile> validWord,validWord2,invalidWord, wordToPlace;

    private Point outside,onEdge,start,leftStart,upStart,acrossWord1, acrossWord2, otherPoint, newPoint;

    private BoardValidator validator;

    @BeforeEach
    void setUp() {

        //Initialized model and board
        List<String> playerNames = Arrays.asList("Vlad", "Alex", "Kieran", "Tim");
        ScrabbleModel model = new ScrabbleModel(playerNames);
        board = model.getBoard();

        //Initialize Words
        validWord = new ArrayList<>();
        validWord.add(new Tile(Letter.H));
        validWord.add(new Tile(Letter.E));
        validWord.add(new Tile(Letter.L));
        validWord.add(new Tile(Letter.L));
        validWord.add(new Tile(Letter.O));

        invalidWord = new ArrayList<>();
        invalidWord.add(new Tile(Letter.X));
        invalidWord.add(new Tile(Letter.Y));
        invalidWord.add(new Tile(Letter.Z));

        validWord2 = new ArrayList<>();
        validWord2.add(new Tile(Letter.V));
        validWord2.add(new Tile(Letter.E));
        validWord2.add(new Tile(Letter.X));

        wordToPlace = new ArrayList<>();
        wordToPlace.add(new Tile(Letter.H));
        wordToPlace.add(new Tile(Letter.L));
        wordToPlace.add(new Tile(Letter.L));
        wordToPlace.add(new Tile(Letter.O));

        //Create Points to use
        outside = new Point(20,20);
        onEdge = new Point(12,1);
        start = new Point(7,7);
        leftStart = new Point(5,7);
        upStart = new Point(7,4);
        acrossWord1 = new Point(8,6);
        acrossWord2 = new Point(6,5);
        otherPoint = new Point(5,5);
        newPoint = new Point(6,8);

        //validator = board.getValidator();
        validator = new BoardValidator();
    }

    /**
     * Tests placing words that go outside the board
     * @author Vladimir Kovacina
     */
    @Test
    void isValidPlacementOutsideBoardTest() {

        //Placed outside board vertically
        BoardPlaceEvent outsideBoardV = new BoardPlaceEvent(validWord, outside, Board.Direction.DOWN);
        assertEquals(BoardValidator.Status.OUT_OF_BOUNDS, validator.isValidLocation(board, outsideBoardV));
        //Placed outside board horizontally
        BoardPlaceEvent outsideBoardH = new BoardPlaceEvent(validWord, outside, Board.Direction.RIGHT);
        assertEquals(BoardValidator.Status.OUT_OF_BOUNDS, validator.isValidLocation(board, outsideBoardH));

        //Placed inside board but goes out horizontally
        BoardPlaceEvent edgeBoardH = new BoardPlaceEvent(validWord, onEdge, Board.Direction.RIGHT);
        assertEquals(BoardValidator.Status.OUT_OF_BOUNDS, validator.isValidLocation(board, edgeBoardH));
    }

    /**
     * Tests placing words on the start tile
     * @author Vladimir Kovacina
     */
    @Test
    void isValidPlacementStartTileBoardTest() {
        //Placing invalid word at start
        BoardPlaceEvent invalidStart = new BoardPlaceEvent(invalidWord, start, Board.Direction.RIGHT);
        // Valid location... but invalid words
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, invalidStart));

        //Placing valid word but not at start tile initially (horizontally)
        BoardPlaceEvent invalidStart2 = new BoardPlaceEvent(validWord, otherPoint, Board.Direction.RIGHT);
        assertEquals(BoardValidator.Status.NOT_ON_START, validator.isValidLocation(board, invalidStart2));

        //Placing valid word but not at start tile initially (vertically)
        BoardPlaceEvent invalidStart3 = new BoardPlaceEvent(validWord, otherPoint, Board.Direction.DOWN);
        assertEquals(BoardValidator.Status.NOT_ON_START, validator.isValidLocation(board, invalidStart3));

        //Placing valid word at start tile
        BoardPlaceEvent validStart1 = new BoardPlaceEvent(validWord, start, Board.Direction.RIGHT);
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, validStart1));

        //Placing valid word across (horizontal) start tile (so that is doesn't start on it but still hits it)
        BoardPlaceEvent validStart2 = new BoardPlaceEvent(validWord, leftStart, Board.Direction.RIGHT);
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, validStart2));

        //Placing valid word across (vertical) start tile (so that is doesn't start on it but still hits it)
        BoardPlaceEvent validStart3 = new BoardPlaceEvent(validWord, upStart, Board.Direction.DOWN);
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, validStart3));
    }

    /**
     * Tests 2 words that intersect, first word is placed horizontally
     * @author Vladimir Kovacina
     */

    @Test
    void isValidPlacement2WordsHorizThenVertTest() {
        //Place Hello (horizontally) at start and place Hello across it (vertically) to see if adjacent words work
        board.placeWord(new BoardPlaceEvent(validWord, start, Board.Direction.RIGHT));
        BoardPlaceEvent acrossVert = new BoardPlaceEvent(wordToPlace, acrossWord1, Board.Direction.DOWN);
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, acrossVert));
    }

    /**
     * Tests 2 words that intersect, first word is placed vertically
     * @author Vladimir Kovacina
     */
    @Test
    void isValidPlacement2WordsVertThenHorizTest() {
        //Place Hello (vertically) at start and place Hello across it (horizontally) to see if adjacent words work
        board.placeWord(new BoardPlaceEvent(validWord, upStart, Board.Direction.DOWN));
        BoardPlaceEvent acrossHoriz = new BoardPlaceEvent(wordToPlace, acrossWord2, Board.Direction.RIGHT);
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, acrossHoriz));
    }

    /**
     * Tests 2 words that are not intersecting, first word is on start tile
     * @author Vladimir Kovacina
     */

    @Test
    void isValidPlacement2WordsNotAdjacentHorizontallyTest() {
        //Place Hello (horizontally) at start and place Hello again but not adjacent to the first word
        board.placeWord(new BoardPlaceEvent(validWord, start, Board.Direction.RIGHT));//place validStart1
        BoardPlaceEvent invalidPlace = new BoardPlaceEvent(validWord, otherPoint, Board.Direction.RIGHT);
        assertEquals(BoardValidator.Status.NOT_NEXT_TO_WORD, validator.isValidLocation(board, invalidPlace));

    }
    @Test
    void isValidPlacement2WordsNotAdjacentVerticallyTest() {
        //Place Hello (vertically) at start and place Hello again but not adjacent to the first word
        board.placeWord(new BoardPlaceEvent(validWord, upStart, Board.Direction.DOWN)); //place validStart3
        BoardPlaceEvent invalidPlace = new BoardPlaceEvent(validWord, otherPoint, Board.Direction.DOWN);
        assertEquals(BoardValidator.Status.NOT_NEXT_TO_WORD, validator.isValidLocation(board, invalidPlace));
    }

    /**
     * Tests placing 2 words that completly overlap
     * @author Vladimir Kovacina
     */

    @Test
    void isValidPlacementOverlapTest() {
        //Place Hello twice in the same place (on top of each other)
        board.placeWord(new BoardPlaceEvent(validWord, upStart, Board.Direction.DOWN));
        BoardPlaceEvent invalidPlace = new BoardPlaceEvent(validWord, upStart, Board.Direction.DOWN);
        // Valid location... but invalid words
        List<BoardWord> curWords = board.getCurrentWords();
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, invalidPlace));
    }

    /**
     * Tests placing 3 and a new word is created from the placement, but it is an invalid word
     * @author Vladimir Kovacina
     */
    @Test
    void isValidPlacement3WordsInvalidComboTest() {
        //Place Hello twice (once horizontally and once vertically) then place it
        // across again but create invalid words this time

        board.placeWord(new BoardPlaceEvent(validWord, start, Board.Direction.RIGHT)); //place validStart1
        board.placeWord(new BoardPlaceEvent(wordToPlace, acrossWord1, Board.Direction.DOWN)); //place acrossVert
        List<Tile> wordToPlace2 = new ArrayList<>();
        wordToPlace2.add(new Tile(Letter.H));
        wordToPlace2.add(new Tile(Letter.E));
        wordToPlace2.add(new Tile(Letter.L));
        wordToPlace2.add(new Tile(Letter.O));
        Point newPoint = new Point(6,8);
        BoardPlaceEvent invalidPlace = new BoardPlaceEvent(wordToPlace2, newPoint, Board.Direction.RIGHT);

        // Valid location... but invalid words
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, invalidPlace));
    }

    /**
     * Tests placing 3 and a new word is created from the placement, but it is a valid word
     * @author Vladimir Kovacina
     */
    @Test
    void isValidPlacement3WordsValidComboTest() {
        //Place Hello twice (once horizontally and once vertically) then place it
        // across again but create valid words this time

        board.placeWord(new BoardPlaceEvent(validWord, start, Board.Direction.RIGHT)); //place validStart1 word
        List<Tile> wordToPlace2 = new ArrayList<>();
        wordToPlace2.add(new Tile(Letter.E));
        wordToPlace2.add(new Tile(Letter.L));
        wordToPlace2.add(new Tile(Letter.L));
        wordToPlace2.add(new Tile(Letter.O));
        board.placeWord(new BoardPlaceEvent(wordToPlace2, start, Board.Direction.DOWN));
        BoardPlaceEvent validPlace = new BoardPlaceEvent(validWord2, newPoint, Board.Direction.DOWN);
        assertEquals(BoardValidator.Status.SUCCESS, validator.isValidLocation(board, validPlace));

    }

    @Test
    void testInvalidWord(){
        assertEquals(BoardValidator.Status.INVALID_WORD, board.isValidPlacement(new BoardPlaceEvent(invalidWord, start, Board.Direction.RIGHT)));
    }

    @Test
    void testValidWord(){
        assertEquals(BoardValidator.Status.SUCCESS, board.isValidPlacement(new BoardPlaceEvent(validWord, start, Board.Direction.RIGHT)));
    }


}