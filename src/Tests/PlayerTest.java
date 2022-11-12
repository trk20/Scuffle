package Tests;

import Model.*;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

class PlayerTest {
    ScrabbleModel model;
    List<String> playerNames;
    ArrayList<Player> players;

    @org.junit.jupiter.api.BeforeEach
    public void setUp() {
        playerNames = Arrays.asList("Vlad","Alex","Kieran","Tim");
        model = new ScrabbleModel(playerNames);
        players = model.getPlayers();

    }



    @org.junit.jupiter.api.Test
    public void placeTilesTest() {
        List<Tile> sampleHand = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.E), new Tile(Letter.R),new Tile(Letter.A),new Tile(Letter.N));
        List<Tile> word1 = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.E));
        List<Tile> word2 = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.X));
        Hand newHand = new Hand(model.getDrawPile());
        newHand.setTiles(sampleHand);
        players.get(1).setHand(newHand);

        //Word is in hand
        assertTrue(players.get(1).placeTiles(word1));
        //Word is not in hand
        assertFalse(players.get(1).placeTiles(word2));


    }

    @org.junit.jupiter.api.Test
    public void discardTilesTest() {
        List<Tile> sampleHand = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.E), new Tile(Letter.R),new Tile(Letter.A),new Tile(Letter.N));
        List<Tile> word1 = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.E));
        List<Tile> word2 = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.X));
        Hand newHand = new Hand(model.getDrawPile());
        newHand.setTiles(sampleHand);
        players.get(1).setHand(newHand);

        //Word is in hand
        assertTrue(players.get(1).discardTiles(word1));
        //Word is not in hand
        assertFalse(players.get(1).discardTiles(word2));

    }


    @org.junit.jupiter.api.Test
    public void containsTilesTest() {
        List<Tile> sampleHand = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.E), new Tile(Letter.R),new Tile(Letter.A),new Tile(Letter.N));
        List<Tile> word1 = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.E));
        List<Tile> word2 = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.X));
        List<Tile> word3 = new ArrayList<>();

        Hand newHand = new Hand(model.getDrawPile());
        newHand.setTiles(sampleHand);
        players.get(1).setHand(newHand);

        //Word is in hand
        assertTrue(players.get(1).containsTiles(word1));
        //Word is not in hand
        assertFalse(players.get(1).containsTiles(word2));
        //Word is Null
        assertTrue(players.get(1).containsTiles(word3));
    }

    @org.junit.jupiter.api.Test
    public void outOfTilesTest() {
        //Make player hand empty
        List<Tile> empty = new ArrayList<>();
        List<Tile> sampleHand = Arrays.asList(new Tile(Letter.L),new Tile(Letter.I),
                new Tile(Letter.E), new Tile(Letter.R),new Tile(Letter.A),new Tile(Letter.N));
        Hand newHand = new Hand(model.getDrawPile());
        newHand.setTiles(empty);
        players.get(0).setHand(newHand);
        //Hand is Empty
        assertTrue(players.get(0).outOfTiles());
        newHand.setTiles(sampleHand);
        players.get(0).setHand(newHand);
        //Hand is not Empty
        assertFalse(players.get(0).outOfTiles());
    }

    @org.junit.jupiter.api.Test
    public void getHandTest() {
        Hand playerHand = players.get(0).getHand();
        assertTrue(playerHand instanceof Hand);
        assertEquals(playerHand.getHeldTiles().size(), 7);
    }

    @org.junit.jupiter.api.Test
    public void getScoreTest() {
        assertEquals(0, players.get(0).getScore());
        players.get(0).addPoints(5);
        assertEquals(5, players.get(0).getScore());
        players.get(0).addPoints(10);
        assertEquals(15, players.get(0).getScore());
    }

    @org.junit.jupiter.api.Test
    void getNameTest() {
        assertEquals("Vlad", players.get(0).getName());
        assertEquals("Alex", players.get(1).getName());
        assertEquals("Kieran", players.get(2).getName());
        assertEquals("Tim", players.get(3).getName());
    }
}