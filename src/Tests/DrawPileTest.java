package Tests;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DrawPileTest {
    ScrabbleModel model;

    List<String> players;

    @BeforeEach
    void setUp() {

        players =Arrays.asList("Vlad");
        model = new ScrabbleModel(players);

    }

    @Test
    void initialDrawPileSizeTest(){
        DrawPile dp = model.getDrawPile();
        List<Tile> tiles = dp.getLetterPile();
        assertEquals(98 - 7, tiles.size());
        for(int i = 0; i< tiles.size(); i++){
            assertTrue(tiles.get(i) instanceof Tile);
        }

    }

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
        assertEquals(98-7+5, dp.getLetterPile().size());


    }

    @Test
    void draw() {
        DrawPile dp = model.getDrawPile();
        Tile drawn = dp.draw();
        assertTrue(drawn instanceof Tile);
        assertEquals(98-7-1, dp.getLetterPile().size());

        //Test Case where draw is drawing from empty pile
        List<Tile> empty = new ArrayList<>();
        dp.setLetterPile(empty);
        Tile drawn2 = dp.draw();
        assertEquals(drawn2, null);

    }
}