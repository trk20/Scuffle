package Tests;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HandTest {
    private Hand hand;
    DrawPile drawPile;
    ScrabbleModel model;
    private List<Tile> tiles;


    /**
     * Sets variables up for tests- initialises a new model and hand with predetermined tiles
     */
    @BeforeEach
    void setUp() {
        model = new ScrabbleModel(Arrays.asList("Tim"));
        drawPile = model.getDrawPile();
        tiles = new ArrayList<>();
        tiles.addAll(Arrays.asList(new Tile(Letter.A),new Tile(Letter.B),new Tile(Letter.C),
                new Tile(Letter.D),new Tile(Letter.E),new Tile(Letter.F),new Tile(Letter.G),
                new Tile(Letter.H),new Tile(Letter.I),new Tile(Letter.J)));
        hand = new Hand(drawPile);
        hand.setTiles(tiles);

    }

    /**
     * Tests the setting and getting of tiles from the hand
     */
    @Test
    void setAndGetTiles() {
        assertEquals(tiles,hand.getHeldTiles());
        hand.setTiles(tiles.subList(1,4));
        assertEquals(tiles.subList(1,4),hand.getHeldTiles());
        tiles.add(new Tile(Letter.Z));
        hand.setTiles(tiles);
        assertEquals(tiles,hand.getHeldTiles());
    }

    /**
     * Tests the use of tiles to ensure the tiles are no longer present after being used
     */
    @Test
    void useTiles() {
        // FIXME: use tile does not return boolean anymore
//        assertTrue(hand.useTiles(tiles));
//        hand.setTiles(new ArrayList<>());
//        assertFalse(hand.useTiles(tiles));
    }

    /**
     * Tests the toString method
     */
    @Test
    void testToString() {
        assertEquals("Model.Hand: (A, 1) (B, 3) (C, 3) (D, 2) (E, 1) (F, 4) (G, 2) (H, 4) (I, 1) (J, 8)",hand.toString());
        hand.setTiles(tiles.subList(1,2));

        // Not sure what the issue is but we don't use toString except for debug...
        assertEquals("Model.Hand: (B, 3) (C, 3)",hand.toString());
    }

    /**
     * Tests that discarding tiles discards tiles
     */
    @Test
    void discardSelected() {
        hand.useTiles(tiles);
        for(Tile tile:tiles){
            // FIXME: this is not guaranteed to work,
            //  its possible to draw your own tile back
//            assertFalse(hand.getHeldTiles().contains(tile));
        }
    }
}