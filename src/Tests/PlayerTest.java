package Tests;

import Model.DrawPile;
import Model.Hand;
import Model.Player;
import Model.ScrabbleModel;
import com.sun.source.tree.AssertTree;
import org.junit.Assert;

import java.util.ArrayList;

import static org.junit.Assert.*;

class PlayerTest {


    @org.junit.jupiter.api.BeforeEach
    public void setUp() {

    }



    @org.junit.jupiter.api.Test
    public void placeTilesTest() {
    }

    @org.junit.jupiter.api.Test
    public void discardTilesTest() {
    }


    @org.junit.jupiter.api.Test
    public void containsTilesTest() {
    }

    @org.junit.jupiter.api.Test
    public void outOfTilesTest() {

    }

    @org.junit.jupiter.api.Test
    public void getHandTest() {
        ScrabbleModel model = new ScrabbleModel();
        ArrayList<Player> players = model.getPlayers();

        Hand playerHand = players.get(0).getHand();
        assertTrue(playerHand instanceof Hand);
        assertEquals(playerHand.getHeldTiles().size(), 7);

    }

    @org.junit.jupiter.api.Test
    public void getScoreTest() {
        ScrabbleModel model = new ScrabbleModel();
        ArrayList<Player> players = model.getPlayers();
        assertEquals(0, players.get(0).getScore());
        players.get(0).addPoints(5);
        assertEquals(5, players.get(0).getScore());
        players.get(0).addPoints(10);
        assertEquals(15, players.get(0).getScore());
    }

    @org.junit.jupiter.api.Test
    void getNameTest() {
        ScrabbleModel model = new ScrabbleModel();
        ArrayList<Player> players = model.getPlayers();
        //Check if getName works properly
        assertEquals("Vlad", players.get(0).getName());
        assertEquals("Alex", players.get(1).getName());
        assertEquals("Kieran", players.get(2).getName());
        assertEquals("Tim", players.get(3).getName());

    }
}