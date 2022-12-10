package Tests;

import Model.*;
import ScrabbleEvents.ControllerEvents.*;
import ScrabbleEvents.ModelEvents.ME_ModelChangeEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


class ScrabbleModelTest {

    private ScrabbleModel model;
    private List<Tile> validWord;


    /**
     * Sets up a new model for each test case with 2 players
     */
    @BeforeEach
    void setUp() {
        model = new ScrabbleModel(Arrays.asList("Tim","Not Tim"));

        //Initialize palced Word
        validWord = new ArrayList<>();
        validWord.add(new Tile(Letter.H));
        validWord.add(new Tile(Letter.E));
        validWord.add(new Tile(Letter.L));
        validWord.add(new Tile(Letter.L));
        validWord.add(new Tile(Letter.O));
    }


    /**
     * Tests the initialization and the retrieval of player names
     */
    @Test
    void initializeAndGetPlayerNames(){
        ArrayList<Player> players= new ArrayList<>();
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
     */
    @Test
    void handleEvents(){
//        BoardTileController boardController = new BoardTileController(new Point(7,7), model);
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

    /**
     * Handle serialization events
     */
    @Test
    void handleSerialization(){
        // Check initial state
        assertEquals("Tim",model.getCurPlayer().getName());
        assertEquals(7, model.getCurHand().getHeldTiles().size());
        assertEquals(0, model.getCurPlayer().getScore());
        model.handleControllerEvent(new C_SaveEvent(new File("Saves/SaveTest")));

        // Change model state by placing on the board
        model.getCurHand().setTiles(validWord);
        assertEquals(5, model.getCurHand().getHeldTiles().size());
        model.handleControllerEvent(new TileClickEvent(model.getCurHand().getHeldTiles().get(0)));
        model.handleControllerEvent(new TileClickEvent(model.getCurHand().getHeldTiles().get(1)));
        model.handleControllerEvent(new TileClickEvent(model.getCurHand().getHeldTiles().get(2)));
        model.handleControllerEvent(new TileClickEvent(model.getCurHand().getHeldTiles().get(3)));
        model.handleControllerEvent(new TileClickEvent(model.getCurHand().getHeldTiles().get(4)));
        model.handleControllerEvent(new PlaceClickEvent(Board.Direction.RIGHT, new Point(7,7)));

        // Check new state difference
        assertEquals("Not Tim",model.getCurPlayer().getName());
        assertEquals(7,model.getCurHand().getHeldTiles().size());

        // Check if load brings back to initial state.
        // Loaded model reference needs to be gotten with a listener
        final ScrabbleModel[] loadedModel = new ScrabbleModel[1];
        model.addModelListener(e -> {if(e instanceof ME_ModelChangeEvent mce) loadedModel[0] = mce.newModel();});
        model.handleControllerEvent(new C_LoadEvent(new File("Saves/SaveTest")));

        assertEquals("Tim",loadedModel[0].getCurPlayer().getName());
        assertEquals(7, loadedModel[0].getCurHand().getHeldTiles().size());
        assertEquals(0, loadedModel[0].getCurPlayer().getScore());
    }
}