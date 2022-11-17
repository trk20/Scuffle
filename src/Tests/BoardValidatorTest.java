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
    private List<Tile> validWord;
    private List<Tile> invalidWord;



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


    }

    @Test
    void isValidPlacementTest() {
        BoardValidator validator = board.getValidator();
        //Create BoardPlaceEvent
        //Outside opf Board
        Point outside = new Point(20,20);
        Point onEdge = new Point(12,1);
        Point start = new Point(7,7);
        Point leftStart = new Point(5,7);
        Point upStart = new Point(7,4);
        //Placed outside board vertically
        BoardPlaceEvent outsideBoardV = new BoardPlaceEvent(model,validWord, outside, Board.Direction.DOWN);
        assertFalse(validator.isValidPlacement(outsideBoardV));
        //Placed outside board horizontally
        BoardPlaceEvent outsideBoardH = new BoardPlaceEvent(model,validWord, outside, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(outsideBoardH));
        //Placed inside board but goes out horizontally
        BoardPlaceEvent edgeBoardH = new BoardPlaceEvent(model,validWord, onEdge, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(edgeBoardH));

        //Placing invalid word at start
        BoardPlaceEvent invalidStart = new BoardPlaceEvent(model,invalidWord, start, Board.Direction.RIGHT);
        assertFalse(validator.isValidPlacement(invalidStart));

        //Placing valid word at start tile
        BoardPlaceEvent validStart1 = new BoardPlaceEvent(model,validWord, start, Board.Direction.RIGHT);
        assertTrue(validator.isValidPlacement(validStart1));

        //Placing valid word across (horizontal) start tile (so that is doesn't start on it but still hits it)
        BoardPlaceEvent validStart2 = new BoardPlaceEvent(model,validWord, leftStart, Board.Direction.RIGHT);
        assertTrue(validator.isValidPlacement(validStart2));

        //Placing valid word across (vertical) start tile (so that is doesn't start on it but still hits it)
        BoardPlaceEvent validStart3 = new BoardPlaceEvent(model,validWord, upStart, Board.Direction.DOWN);
        assertTrue(validator.isValidPlacement(validStart3));


    }
}