package Tests;

import Controllers.BoardTileController;
import Model.*;
import ScrabbleEvents.ControllerEvents.DiscardClickEvent;
import ScrabbleEvents.ControllerEvents.PlaceClickEvent;
import ScrabbleEvents.ControllerEvents.TileClickEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


class ScrabbleModelTest {

    ScrabbleModel model;


    /**
     * Sets up a new model for each test case with 2 players
     */
    @BeforeEach
    void setUp() {
        model = new ScrabbleModel(Arrays.asList("Tim","Not Tim"));
    }


    /**
     * Tests the initialization and the retrieval of player names
     */
    @Test
    void initializeAndGetPlayerNames(){
        ArrayList<Player> players= new ArrayList<>();
//        players.add(new Player("Tim", model));
//        players.add(new Player("Not Tim",model));
        assertEquals(players.get(0).getName(),model.getPlayers().get(0).getName());
        assertEquals(players.get(1).getName(),model.getPlayers().get(1).getName());
    }

    /**
     * Tests that a DrawPile was initialised on creation of the model
     */
    @Test
    void getDrawPile(){
        assertFalse(model.getDrawPile().getLetterPile().isEmpty());
        assertNotNull(model.getDrawPile().draw());
    }

    /**
     * Tests that the current player method correctly returns the name of the first player
     */
    @Test
    void currentPlayer(){
        assertEquals("Tim",model.getCurPlayer().getName());
        assertFalse(model.getCurHand().getHeldTiles().isEmpty());
    }


    /**
     * Tests Event handling of ScrabbleModel: TileClick, DiscardClick, and PlaceClick events
     * TODO: separate into multiple tests
     */
    @Test
    void handleEvents(){
        BoardTileController boardController = new BoardTileController(new Point(7,7));
        Player player = model.getCurPlayer();
        model.getCurHand().getHeldTiles().add(0,new Tile(Letter.A));
        model.getCurHand().getHeldTiles().add(1,new Tile(Letter.Y));
        // send a TileClickEvent to select the first tile, then place it with a PlaceClickEvent
        model.handleControllerEvent(new TileClickEvent(model.getCurHand().getHeldTiles().get(0)));
        model.handleControllerEvent(new PlaceClickEvent(Board.Direction.RIGHT, new Point(7,7)));
        // Active player should be changed
        assertNotEquals(player,model.getCurPlayer());
        // Tile should have been played
        assertEquals(Letter.Y,player.getHand().getHeldTiles().get(0).getLetter());
        player = model.getCurPlayer();
        model.getCurHand().getHeldTiles().add(0,new Tile(Letter.A));
        model.getCurHand().getHeldTiles().add(1,new Tile(Letter.Y));
        // send a TileClickEvent to select the first tile, then discard it with a DiscardClickEvent
        model.handleControllerEvent(new TileClickEvent(model.getCurHand().getHeldTiles().get(0)));
        model.handleControllerEvent(new DiscardClickEvent());
        // Tile should have been discarded
        assertEquals(Letter.Y,player.getHand().getHeldTiles().get(0).getLetter());
    }
}