package Tests;

import Model.BoardTile;
import Model.Letter;
import Model.ScrabbleModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class BoardTileTest {
    private BoardTile boardTile;
    private BoardTile boardTile2;
    @BeforeEach
    void setUp() {
        // Identical, default board tiles
        boardTile = new BoardTile(BoardTile.Type.BLANK, 0, 0);
        boardTile2 = new BoardTile(BoardTile.Type.BLANK, 0, 0);
    }

    /**
     * Test initial tile's initial state to be sure it is as expected before tests.
     * Also tests if two tiles with same state default state are equal
     */
    @Test
    void testConstructorAndEqual() {
        assertEquals(0, boardTile.getX());
        assertEquals(0, boardTile.getY());
        assertEquals(BoardTile.Type.BLANK, boardTile.getType());
        assertEquals(null, boardTile.getLetter());
        assertEquals(false, boardTile.isTaken());

        assertEquals(boardTile, boardTile2);
    }

    /**
     * Test initial tile state, and effect of X,Y setters on the tile.
     */
    @Test
    void testXYSettersAndGetters() {
        for(int i = 0; i < ScrabbleModel.BOARD_SIZE; i++){
            boardTile.setX(i);
            boardTile.setY(i);
            assertEquals(i, boardTile.getX());
            assertEquals(i, boardTile.getY());
        }
    }

    /**
     * Test effect of BoardTile.Type setters/getters
     */
    @Test
    void testTypeSettersAndGetters() {
        for(BoardTile.Type type: BoardTile.Type.values()){
            boardTile.setType(type);
            assertEquals(type, boardTile.getType());
        }
    }

    /**
     * Test the effect of setting a letter in a tile.
     * Should not be able to set it twice for one tile.
     * Should mark the tile as taken.
     */
    @Test
    void testLetterSet() {
        // Try to set a letter, make tile taken
        boardTile.setLetter(Letter.A);
        assertEquals(true, boardTile.isTaken());
        assertEquals(Letter.A, boardTile.getLetter());

        // Try to set different letter, should not override!
        boardTile.setLetter(Letter.B);
        assertEquals(true, boardTile.isTaken());
        assertEquals(Letter.A, boardTile.getLetter());
    }


    /**
     * Test if a copy is equal to its original tile, the tile that is copied.
     */
    @Test
    void testCopyIsEqual() {
        assert(boardTile.equals(boardTile.copy()));
    }

    /**
     * Tests toString implementation of BoardTile.
     * Should be the string representation of its contained type (if it does not have a letter).
     * BLANK: --
     * START: ST
     * word,letter (x2/x3): 2W, 3W, 2L, 3L
     * If it has a letter, it shows the letter instead.
     */
    @Test
    void testToString() {
        // Map every type to a string representation (we should have a toString in type...)
        HashMap<BoardTile.Type, String> typeToString = new HashMap<>();
        typeToString.put(BoardTile.Type.BLANK, " -- ");
        typeToString.put(BoardTile.Type.START, "ST");
        typeToString.put(BoardTile.Type.X2WORD, "2W");
        typeToString.put(BoardTile.Type.X3WORD, "3W");
        typeToString.put(BoardTile.Type.X3LETTER, "3L");
        typeToString.put(BoardTile.Type.X2LETTER, "2L");

        for(BoardTile.Type type: BoardTile.Type.values()){
            boardTile.setType(type);
            assertEquals(typeToString.get(type), boardTile.toString());
         }

        // Set letter, should return that letter as a string
        boardTile.setLetter(Letter.L);
        assertEquals("L", boardTile.toString());
    }
}