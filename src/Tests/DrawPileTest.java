package Tests;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DrawPileTest {
    ScrabbleModel model;

    List<String> players;


    /**
     * Set up for each test case in the test suite,
     * initializes a Scrabble model and list of players
     *
     * @author: Vladimir Kovacina
     *
     */
    @BeforeEach
    void setUp() {

        players =Arrays.asList("Vlad");
        model = new ScrabbleModel(players);

    }

    /**
     * Tests that the DrawPile contains all letters (including blanks)
     *
     * @author Timothy Kennedy
     */
    @Test
    void containsAllLetters(){
        DrawPile dp = new DrawPile();
        for(Letter letter: Letter.values()) { //check if the DrawPile has the correct number of each type of letter (INCL blank)
            assertEquals(dp.getLetterPile().stream().filter(tile -> tile.letter().equals(letter)).count(), letter.getFrequency());
        }
    }

    /**
     * Tests the initial drawPile to make sure it is the correct size, takes into account the number of players
     * and their hand size, also checks that each tile is the correct type
     *
     * @author: Vladimir Kovacina
     */
    @Test
    void initialDrawPileSizeTest(){
        DrawPile dp = model.getDrawPile();
        List<Tile> tiles = dp.getLetterPile();
        assertEquals(98 - 7, tiles.size());
        for(int i = 0; i< tiles.size(); i++){
            assertTrue(tiles.get(i) instanceof Tile);
        }

    }
    /**
     * Tests the addToPile() method, checks if it works properly when
     * multiple tiles are added and when no tiles are added
     *
     * @author: Vladimir Kovacina
     */

    @Test
    void addToPile() {
        //Assume only one player
        DrawPile dp = model.getDrawPile();
        List<Tile> current = dp.getLetterPile();
        int currentSize = current.size();
        List<Tile> newTiles = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            //Add 5 A's
            newTiles.add(new Tile(Letter.A));
        }
        dp.addToPile(newTiles);
        assertEquals(100-7+5, dp.getLetterPile().size());
        //Try adding no tiles (empty list)
        newTiles = new ArrayList<>();
        dp.addToPile(newTiles);
        assertEquals(98, dp.getLetterPile().size());
    }

    /**
     * Tests the draw() method, checks to see if a tile type is drawn,
     * also checks if the new drawPile is the correct size, and tests
     * that is handles drawing from an empty drawPile
     *
     * @author: Vladimir Kovacina
     */

    @Test
    void draw() {
        DrawPile dp = model.getDrawPile();
        Tile drawn = dp.draw();
        assertTrue(drawn instanceof Tile);
        assertEquals(100-7-1, dp.getLetterPile().size());

        //Test Case where draw is drawing from empty pile
        List<Tile> empty = new ArrayList<>();
        dp.setLetterPile(empty);
        Tile drawn2 = dp.draw();
        assertEquals(drawn2, null);
    }
}