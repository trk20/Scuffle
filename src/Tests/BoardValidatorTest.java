package Tests;

import Events.BoardPlaceEvent;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BoardValidatorTest {

    private ScrabbleModel model;
    private List<String> playerNames;
    private Board board;
    private List<Tile> validWord,validWord2,invalidWord, wordToPlace;

    private Point outside,onEdge,start,leftStart,upStart,acrossWord1, acrossWord2, otherPoint, newPoint;

    private BoardValidator validator;

    @BeforeEach
    void setUp() {

        playerNames = Arrays.asList("Vlad","Alex","Kieran","Tim");
        model = new ScrabbleModel(playerNames);
        board = model.getBoard();

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

        validator = board.getValidator();

    }


    @Test
    void isValidPlacementOutsideBoardTest() {

        //Placed outside board vertically
        BoardPlaceEvent outsideBoardV = new BoardPlaceEvent(model,validWord, outside, Board.Direction.DOWN);
        assertFalse(validator.isValidPlacement(outsideBoardV));
        //Placed outside board horizontally
        BoardPlaceEvent outsideBoardH = new BoardPlaceEvent(model,validWord, outside, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(outsideBoardH));

        //Placed inside board but goes out horizontally
        BoardPlaceEvent edgeBoardH = new BoardPlaceEvent(model,validWord, onEdge, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(edgeBoardH));
    }

    @Test
    void isValidPlacementStartTileBoardTest() {
        //Placing invalid word at start
        BoardPlaceEvent invalidStart = new BoardPlaceEvent(model,invalidWord, start, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(invalidStart));

        //Placing valid word at start tile
        BoardPlaceEvent validStart1 = new BoardPlaceEvent(model,validWord, start, Board.Direction.RIGHT);
        assertTrue(validator.isValidPlacement(validStart1));

        //Placing valid word but not at start tile initially (horizontally)
        BoardPlaceEvent invalidStart2 = new BoardPlaceEvent(model,validWord, otherPoint, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(invalidStart2));

        //Placing valid word but not at start tile initially (vertically)
        BoardPlaceEvent invalidStart3 = new BoardPlaceEvent(model,validWord, otherPoint, Board.Direction.DOWN);
        assertFalse(validator.isValidPlacement(invalidStart3));


        //Placing valid word across (horizontal) start tile (so that is doesn't start on it but still hits it)
        BoardPlaceEvent validStart2 = new BoardPlaceEvent(model,validWord, leftStart, Board.Direction.RIGHT);
        assertTrue(validator.isValidPlacement(validStart2));

        //Placing valid word across (vertical) start tile (so that is doesn't start on it but still hits it)
        BoardPlaceEvent validStart3 = new BoardPlaceEvent(model,validWord, upStart, Board.Direction.DOWN);
        assertTrue(validator.isValidPlacement(validStart3));
    }

    @Test
    void isValidPlacement2WordsHorizThenVertTest() {
        //Place Hello (horizontally) at start and place Hello across it (vertically) to see if adjacent words work
        board.placeWord(new BoardPlaceEvent(model,validWord, start, Board.Direction.RIGHT));
        BoardPlaceEvent acrossVert = new BoardPlaceEvent(model,wordToPlace, acrossWord1, Board.Direction.DOWN);
        assertTrue(validator.isValidPlacement(acrossVert));

    }
    @Test
    void isValidPlacement2WordsVertThenHorizTest() {
        //Place Hello (vertically) at start and place Hello across it (horizontally) to see if adjacent words work
        board = new Board(false);
        model = new ScrabbleModel(playerNames);
        validator = board.getValidator();
        board.placeWord(new BoardPlaceEvent(model,validWord, upStart, Board.Direction.DOWN));
        BoardPlaceEvent acrossHoriz = new BoardPlaceEvent(model,wordToPlace, acrossWord2, Board.Direction.RIGHT);
        assertTrue(validator.isValidPlacement(acrossHoriz));
    }

    @Test
    void isValidPlacement2WordsNotAdjacentTest() {
        //Place Hello (horizontally) at start and place Hello again but not adjacent to the first word

        board.placeWord(new BoardPlaceEvent(model,validWord, start, Board.Direction.RIGHT));//place validStart1
        BoardPlaceEvent invalidPlace = new BoardPlaceEvent(model,validWord, otherPoint, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(invalidPlace));

        //Place Hello (vertically) at start and place Hello again but not adjacent to the first word
        board.placeWord(new BoardPlaceEvent(model,validWord, upStart, Board.Direction.DOWN)); //place validStart3
        invalidPlace = new BoardPlaceEvent(model,validWord, otherPoint, Board.Direction.DOWN);
        assertFalse(validator.isValidPlacement(invalidPlace));

    }

    @Test
    void isValidPlacementOverlapTest() {
        //Place Hello twice in the same place (on top of each other)
        BoardPlaceEvent invalidPlace = new BoardPlaceEvent(model,validWord, upStart, Board.Direction.DOWN);
        assertFalse(validator.isValidPlacement(invalidPlace));
    }

    @Test
    void isValidPlacement3WordsInvalidComboTest() {
        //Place Hello twice (once horizontally and once vertically) then place it
        // across again but create invalid words this time

        board.placeWord(new BoardPlaceEvent(model,validWord, start, Board.Direction.RIGHT)); //place validStart1
        board.placeWord(new BoardPlaceEvent(model,wordToPlace, acrossWord1, Board.Direction.DOWN)); //place acrossVert
        List<Tile> wordToPlace2 = new ArrayList<>();
        wordToPlace2.add(new Tile(Letter.H));
        wordToPlace2.add(new Tile(Letter.E));
        wordToPlace2.add(new Tile(Letter.L));
        wordToPlace2.add(new Tile(Letter.O));
        Point newPoint = new Point(6,8);
        BoardPlaceEvent invalidPlace = new BoardPlaceEvent(model, wordToPlace2, newPoint, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(invalidPlace));
    }

    @Test
    void isValidPlacement3WordsValidComboTest() {
        //Place Hello twice (once horizontally and once vertically) then place it
        // across again but create valid words this time
        board = new Board(false);
        model = new ScrabbleModel(playerNames);
        validator = board.getValidator();
        board.placeWord(new BoardPlaceEvent(model,validWord, start, Board.Direction.RIGHT)); //place validStart1 word
        List<Tile> wordToPlace2 = new ArrayList<>();
        wordToPlace2.add(new Tile(Letter.E));
        wordToPlace2.add(new Tile(Letter.L));
        wordToPlace2.add(new Tile(Letter.L));
        wordToPlace2.add(new Tile(Letter.O));
        board.placeWord(new BoardPlaceEvent(model,wordToPlace2, start, Board.Direction.DOWN));
        BoardPlaceEvent validPlace = new BoardPlaceEvent(model,validWord2, newPoint, Board.Direction.DOWN);
        assertTrue(validator.isValidPlacement(validPlace));

    }


}