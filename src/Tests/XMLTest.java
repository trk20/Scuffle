package Tests;

import Model.Board;
import Model.BoardTile;
import Model.ReadXMLFile;
import Model.ScrabbleModel;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit Tests for the ReadXMLFile class
 *
 * @author Vladimir Kovacina
 * @version 2022-12-08
 */

class XMLTest {

    private ScrabbleModel model;
    private List<String> playerNames;
    private ReadXMLFile rXML;


    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        playerNames = Arrays.asList("Vlad","Alex","Tim","Kieran");
        model = new ScrabbleModel(playerNames);
        rXML = new ReadXMLFile(model, "test.xml");


    }

    /**
     * Tests that the xml is read properly, and that the premium tiles that have valid coordinates on the board
     * are placed properly and with the correct premium tiles
     *
     * @author: Vladimir Kovacina
     */

    @org.junit.jupiter.api.Test
    public void validPlacementsTest() {
        rXML.read();
        Board board = model.getBoard();

        assertEquals(board.getBoardTile(new Point(0,0)).getType(), BoardTile.Type.X3WORD);
        assertEquals(board.getBoardTile(new Point(1,1)).getType(), BoardTile.Type.X2WORD);
        assertEquals(board.getBoardTile(new Point(2,2)).getType(), BoardTile.Type.X2LETTER);
        assertEquals(board.getBoardTile(new Point(3,3)).getType(), BoardTile.Type.X3LETTER);


    }

    /**
     * Tests that the xml is read properly, and that the premium tiles that have invalid coordinates are
     * not placed on the board.
     *
     * Invalid coordinates are either out of board bounds or on the start tile
     * Also if the tag is not defined as <tile> </tile> it will not be read and the tile will be blank
     *
     * @author: Vladimir Kovacina
     */

    @org.junit.jupiter.api.Test
    public void invalidPlacementsTest() {
        rXML.read();
        Board board = model.getBoard();

        assertEquals(board.getBoardTile(new Point(7,7)).getType(), BoardTile.Type.START);
        assertEquals(board.getBoardTile(new Point(10,10)).getType(), BoardTile.Type.BLANK);



    }

}
