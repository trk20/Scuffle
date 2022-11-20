package Tests;

import Model.BoardTile;
import Model.BoardTile2DTable;
import Model.Letter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;
// This should translate somewhat well to Grid2DArray tests, keeping it until those exist
@Deprecated
class BoardTile2DTableTest {
    BoardTile2DTable board;


    @BeforeEach
    void setup(){
        board = new BoardTile2DTable();
    }

    @Test
    void randomiseTable() {
        //Not sure how to test this
    }

    @Test
    void setAndGetLetter() {
        Point point = new Point(0, 0);
        BoardTile expectedTile = new BoardTile(0,0);
        expectedTile.setLetter(Letter.A);

        board.setLetter( point, Letter.A);

        assertEquals(expectedTile, board.getTile(point));
    }

    @Test
    void setAndGetType() {
        Point point = new Point(0, 0);

        board.setType(point, BoardTile.Type.X2WORD);
        assertEquals(board.getType(point), BoardTile.Type.X2WORD);
    }

    @Test
    void isTaken() {
        Point point = new Point(0, 0);

        board.setLetter( point, Letter.A);
        assertTrue(board.isTaken(point));
    }

    @Test
    void isNotTaken() {
        Point point = new Point(0, 0);

        assertFalse(board.isTaken(point));
    }

    @Test
    void copyBlankTable() {
        assertEquals(board, board.copy());
    }

    @Test
    void copyNonBlankTable() {
        Point point = new Point(0, 0);

        board.setType(point, BoardTile.Type.X2WORD);
        board.setLetter( point, Letter.A);

        assertEquals(board, board.copy());
    }

    @Test
    void testToString() {
        // too big to test
    }
}
