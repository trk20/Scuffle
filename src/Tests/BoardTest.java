package Tests;

import Events.BoardPlaceEvent;
import Model.Board;
import Model.Letter;
import Model.ScrabbleModel;
import Model.Tile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BoardTest {
    ArrayList<Tile> placedTiles;
    ScrabbleModel model;
    Board board;
    Point point;
    BoardPlaceEvent event;

    @BeforeEach
    void setup(){
        placedTiles = new ArrayList<Tile>();
        placedTiles.add(new Tile(Letter.C));
        placedTiles.add(new Tile(Letter.A));
        placedTiles.add(new Tile(Letter.T));

        model = new ScrabbleModel(new ArrayList<String>(1));
        board = new Board(false);
        point = new Point(7,7);
        event = new BoardPlaceEvent(model, placedTiles, point, Board.Direction.DOWN);
    }

    //Currently all valid placement tests will fail due to scoring being incomplete

    @Test
    void checkInvalidPlacement(){
        event = new BoardPlaceEvent(model, placedTiles, new Point(0,0), Board.Direction.DOWN);
        int result = board.placeWord(event);
        assertEquals(-1, result);
    }

    @Test
    void checkInvalidWord(){
        placedTiles.add(new Tile(Letter.C));
        int result = board.placeWord(event);
        assertEquals(-1, result);

    }


    @Test
    void checkValidPlaceOnStartTile(){
        int result = board.placeWord(event);
        assertEquals(10, result);
    }

    @Test
    void checkValidOverlapWordPlacement(){
        board.placeWord(event);
        placedTiles.remove(Letter.A);
        event = new BoardPlaceEvent(model, placedTiles, new Point(6, 8), Board.Direction.RIGHT);
        int result = board.placeWord(event);
        /*
         * Should pass as this should look like
         *   C
         *  CAT
         *   T
         *
         */
        assertEquals(5, result);
    }

    @Test
    void checkValidAppendingWordPlacement(){
        board.placeWord(event);
        placedTiles = new ArrayList<Tile>();
        placedTiles.add(new Tile(Letter.S));

        event = new BoardPlaceEvent(model, placedTiles, new Point(7, 10), Board.Direction.DOWN);
        int result = board.placeWord(event);
        /*
         * Should pass as this should look like
         *   C
         *   A
         *   T
         *   S
         */
        assertEquals(6, result);
    }

    @Test
    void checkInvalidAppendingWordPlacement(){
        board.placeWord(event);
        placedTiles = new ArrayList<Tile>();
        placedTiles.add(new Tile(Letter.K));

        event = new BoardPlaceEvent(model, placedTiles, new Point(7, 10), Board.Direction.DOWN);
        int result = board.placeWord(event);
        /*
         * Should fail as this should look like
         *   C
         *   A
         *   T
         *   K
         */
        assertEquals(-1, result);
    }

    @Test
    void checkValidConnectingWordPlacement(){
        board.placeWord(event);
        placedTiles = new ArrayList<Tile>();
        placedTiles.add(new Tile(Letter.S));
        placedTiles.add(new Tile(Letter.O));

        event = new BoardPlaceEvent(model, placedTiles, new Point(8, 8), Board.Direction.DOWN);
        int result = board.placeWord(event);
        /*
         * Should pass as this should look like
         *   C
         *   AS
         *   TO
         */
        assertEquals(4, result);
    }

    @Test
    void checkInvalidConnectingWordPlacement(){
        board.placeWord(event);
        placedTiles = new ArrayList<Tile>();
        placedTiles.add(new Tile(Letter.S));
        placedTiles.add(new Tile(Letter.Q));

        event = new BoardPlaceEvent(model, placedTiles, new Point(8, 8), Board.Direction.DOWN);
        int result = board.placeWord(event);
        /*
         * Should pass as this should look like
         *   C
         *   AS
         *   TQ (invalid word)
         */
        assertEquals(-1, result);
    }


}