package Tests;

import Controllers.BoardController;
import Controllers.HandTileController;
import Events.*;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;


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
        players.add(new Player("Tim",model));
        players.add(new Player("Not Tim",model));
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
     * Tests Event handling of ScrabbleModel
     */
    @Test
    void handleEvents(){
        BoardController boardController = new BoardController(model,new Point(7,7));
        Player player = model.getCurPlayer();
        model.getCurHand().getHeldTiles().add(0,new Tile(Letter.A));
        model.getCurHand().getHeldTiles().add(1,new Tile(Letter.Y));
        // send a TileClickEvent to select the first tile, then place it with a PlaceClickEvent
        model.handleControllerEvent(new TileClickEvent(new HandTileController(model.getCurHand().getHeldTiles().get(0)) {
            @Override
            protected void highlight() {
            }
            @Override
            protected void undoHighlight() {
            }
        }));
        model.handleControllerEvent(new PlaceClickEvent(boardController,Board.Direction.RIGHT, boardController.getOrigin()));
        // Active player should be changed
        assertNotEquals(player,model.getCurPlayer());
        // Tile should have been played
        assertEquals(Letter.Y,player.getHand().getHeldTiles().get(0).letter());
        player = model.getCurPlayer();
        model.getCurHand().getHeldTiles().add(0,new Tile(Letter.A));
        model.getCurHand().getHeldTiles().add(1,new Tile(Letter.Y));
        // send a TileClickEvent to select the first tile, then discard it with a DiscardClickEvent
        model.handleControllerEvent(new TileClickEvent(new HandTileController(model.getCurHand().getHeldTiles().get(0)) {
            @Override
            protected void highlight() {
            }
            @Override
            protected void undoHighlight() {
            }
        }));
        model.handleControllerEvent(new DiscardClickEvent(boardController));
        // Tile should have been discarded
        assertEquals(Letter.Y,player.getHand().getHeldTiles().get(0).letter());
    }
}