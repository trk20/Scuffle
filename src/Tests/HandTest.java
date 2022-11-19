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
    void useSelected() {
        ArrayList<Tile> moreTiles = new ArrayList<>();
        moreTiles.addAll(tiles);
        moreTiles.add(new Tile(Letter.X));
        moreTiles.add(new Tile(Letter.Z));
        hand.setTiles(moreTiles); // set hand's tiles to have a known set of tiles plus X and Z
        hand.useTiles(tiles); // use the known set of tiles, leaving X and Z as the first tiles in the hand
        assertSame(hand.getHeldTiles().get(0).letter(), Letter.X);
    }
}