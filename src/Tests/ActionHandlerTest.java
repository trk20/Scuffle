package Tests;

import Model.*;
import ScrabbleEvents.ModelEvents.RedoHandlerEvent;
import ScrabbleEvents.ModelEvents.UndoHandlerEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;
import java.util.List;

/**
 * Tests for the action(redo/undo) handler
 *
 * @Author: Kieran
 */
public class ActionHandlerTest {
    private ScrabbleModel model;

    @BeforeEach
     void setUp(){
        List<String> playerNames = Arrays.asList("Vlad", "Alex");
        model = new ScrabbleModel(playerNames);
    }

    @Test
    void testUndoStackNotEmpty(){
        model.notifyModelListeners(new UndoHandlerEvent(model.getPlayers(), model.getBoard()));
        assertFalse(model.getUndoHandler().isStackEmpty());

    }

    @Test
    void testUndoStackEmpty(){
        assertTrue(model.getUndoHandler().isStackEmpty());

    }

    @Test
    void testUndoPop(){
        model.notifyModelListeners(new UndoHandlerEvent(model.getPlayers(), model.getBoard()));
        assertNotNull(model.getUndoHandler().getPreviousBoard());
        assertNotNull(model.getUndoHandler().getPreviousPlayerState());
    }

    @Test
    void testRedoStackEmpty(){
        model.notifyModelListeners(new RedoHandlerEvent(model.getPlayers(), model.getBoard()));
        assertFalse(model.getRedoHandler().isStackEmpty());

    }

    @Test
    void testRedoStackNotEmpty(){
        assertTrue(model.getRedoHandler().isStackEmpty());

    }

    @Test
    void testRedoPop(){
        model.notifyModelListeners(new RedoHandlerEvent(model.getPlayers(), model.getBoard()));
        assertNotNull(model.getRedoHandler().getPreviousBoard());
        assertNotNull(model.getRedoHandler().getPreviousPlayerState());
    }

}
