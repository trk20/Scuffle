package Tests;

import Model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the PLayer Model class
 *
 * @author Vladimir Kovacina
 * @version Dec-09-2022
 */

class PlayerTest {
    ScrabbleModel model;
    List<String> playerNames;
    ArrayList<Player> players;

    List<Tile> sampleHand;

    List<Tile> word1;

    List<Tile> word2;

    /**
     * Set up for each test case in the test suite,
     * initializes some words, a hand and list of players
     *
     * @author Vladimir Kovacina
     */
    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        playerNames = Arrays.asList("Vlad","Alex","Tim","Kieran");
        model = new ScrabbleModel(playerNames);
        players = model.getPlayers();

        sampleHand = new ArrayList<>();
        sampleHand.add(new Tile(Letter.L));
        sampleHand.add(new Tile(Letter.I));
        sampleHand.add(new Tile(Letter.R));
        sampleHand.add(new Tile(Letter.E));
        sampleHand.add(new Tile(Letter.A));
        sampleHand.add(new Tile(Letter.N));

        word1 = new ArrayList<>();
        word1.add(new Tile(Letter.L));
        word1.add(new Tile(Letter.I));
        word1.add(new Tile(Letter.E));

        word2 = new ArrayList<>();
        word2.add(new Tile(Letter.L));
        word2.add(new Tile(Letter.I));
        word2.add(new Tile(Letter.X));

    }

    /**
     * Tests the placeTiles method, checks if it works properly when
     * the word is and is not in the players hand
     *
     * @author Vladimir Kovacina
     */

    @org.junit.jupiter.api.Test
    public void placeTilesTest() {

        Hand newHand = new Hand(model.getDrawPile(), true);
        newHand.setTiles(sampleHand);
        players.get(1).setHand(newHand);
        players.get(1).placeTiles(newHand.getHeldTiles());
        assertNotEquals(sampleHand, players.get(1).getHand());
    }

    /**
     * Tests the discardTiles method, checks if it works properly when
     * the word is and is not in the players hand
     *
     * @author Vladimir Kovacina
     */
    @org.junit.jupiter.api.Test
    public void discardTilesTest() {

        Hand newHand = new Hand(model.getDrawPile(), true);
        newHand.setTiles(sampleHand);
        players.get(1).setHand(newHand);
        players.get(1).discardTiles(newHand.getHeldTiles());
        assertNotEquals(players.get(1).getHand(), sampleHand);
    }

    /**
     * Tests the outOfTiles method, checks if it works properly when
     * the hand is empty and when it is not empty
     *
     * @author Vladimir Kovacina
     */
    @org.junit.jupiter.api.Test
    public void outOfTilesTest() {
        //Make player hand empty
        List<Tile> empty = new ArrayList<>();
        Hand newHand = new Hand(model.getDrawPile(), true);
        // FIXME: use until out of tiles instead
        newHand.setTiles(empty);
        players.get(0).setHand(newHand);
        //Hand is Empty
        assertTrue(players.get(0).outOfTiles());
        newHand.setTiles(sampleHand);
        players.get(0).setHand(newHand);
        //Hand is not Empty
        assertFalse(players.get(0).outOfTiles());

        //Test Exception:
        for(int i = 0; i < 100; i++){
            try{
                players.get(0).discardTiles(players.get(0).getHand().getHeldTiles());
            }catch (NullPointerException e){
                assertTrue(e instanceof NullPointerException);
            }
        }
    }

    /**
     * Tests the getHand method, checks if it works properly and
     * if the returned hand is the correct type
     *
     * @author Vladimir Kovacina
     */
    @org.junit.jupiter.api.Test
    public void getHandTest() {
        Hand playerHand = players.get(0).getHand();
        assertNotNull(playerHand);
        assertEquals(playerHand.getHeldTiles().size(), 7);
    }

    /**
     * Tests the getScore method, checks if it works properly with
     * various different scores
     *
     * @author Vladimir Kovacina
     */
    @org.junit.jupiter.api.Test
    public void getScoreTest() {
        assertEquals(0, players.get(0).getScore());
        players.get(0).addPoints(5);
        assertEquals(5, players.get(0).getScore());
        players.get(0).addPoints(10);
        assertEquals(15, players.get(0).getScore());
    }

    /**
     * Tests the getName method, checks if it works properly with
     * various different Names
     *
     * @author Vladimir Kovacina
     */
    @org.junit.jupiter.api.Test
    void getNameTest() {
        assertEquals("Vlad", players.get(0).getName());
        assertEquals("Alex", players.get(1).getName());
        assertEquals("Tim", players.get(2).getName());
        assertEquals("Kieran", players.get(3).getName());
    }
}