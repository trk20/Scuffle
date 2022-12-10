package Tests;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for DrawPile model class
 *
 * @author Vladimir Kovacina
 * @version Dec-09-2022
 */

class DrawPileTest {
    ScrabbleModel model;

    List<String> players;


    /**
     * Set up for each test case in the test suite,
     * initializes a Scrabble model and list of players
     *
     * @author Vladimir Kovacina
     *
     */
    @BeforeEach
    void setUp() {

        players = List.of("Vlad");
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
        //check if the DrawPile has the correct number of each type of letter (INCL blank)
        for(Letter expectedLetter: Letter.values()) {
            assertEquals(dp.getLetterPile().stream().filter(
                    tile -> tile.getLetter().equals(expectedLetter)).count(),
                    expectedLetter.getFrequency()
            );
        }
    }

    /**
     * Tests the initial drawPile to make sure it is the correct size
     *
     * @author Vladimir Kovacina
     */
    @Test
    void initialDrawPileSizeTest(){
        DrawPile dp = model.getDrawPile();
        List<Tile> tiles = dp.getLetterPile();
        assertEquals(100 - 7, tiles.size());
    }
    /**
     * Tests the addToPile() method, checks if it works properly when
     * multiple tiles are added and when no tiles are added
     *
     * @author Vladimir Kovacina
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
     * @author Vladimir Kovacina
     */

    @Test
    void drawSuccess() {
        DrawPile dp = model.getDrawPile();
        Tile drawn = dp.draw();
        assertNotNull(drawn);
        assertEquals(100-7-1, dp.getLetterPile().size());
    }

    @Test
    void drawFail() {
        DrawPile dp = model.getDrawPile();

        for(int i = 0; i < 100; i++){
            dp.draw();
        }

        assertNull(dp.draw());
    }
}