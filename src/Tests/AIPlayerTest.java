package Tests;

import ScrabbleEvents.ControllerEvents.C_SkipEvent;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.ControllerEvents.DiscardClickEvent;
import ScrabbleEvents.ControllerEvents.PlaceClickEvent;
import ScrabbleEvents.Listeners.SControllerListener;
import ScrabbleEvents.ModelEvents.NewPlayerEvent;
import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;

class AIPlayerTest {
    private AIPlayer ai1;
    private ScrabbleModel model;
    /** Serves as a way to pass turns*/
    final private static C_SkipEvent skipEvent = new C_SkipEvent();


    @BeforeEach
    public void setUp() {
        // Num AI = 3
        model = new ScrabbleModel(null, 3);
    }

    /**
     * Test special naming for AI players, and how they get initialized in the model
     */
    @Test
    public void testAINaming(){
        // On a new turn, grab the new player event from the model
        AtomicReference<String> newPlayerStr = new AtomicReference<>();
        model.addModelListener(e-> {if (e instanceof NewPlayerEvent ev)
            newPlayerStr.set(ev.player().getName());});

        model.handleControllerEvent(skipEvent); // Passes turn, raise NewPlayerEvent with second player
        assertEquals(newPlayerStr.get(), "Comp2");
        model.handleControllerEvent(skipEvent); // Passes turn
        assertEquals(newPlayerStr.get(), "Comp3");
        model.handleControllerEvent(skipEvent); // Passes turn
        assertEquals(newPlayerStr.get(), "Comp1");
        model.handleControllerEvent(skipEvent); // Passes turn (goes back to second player)
        assertEquals(model.getCurPlayer().getName(), "Comp2");
    }

    /**
     * Test the discard actions from the first AI player.
     * Plays a 3 AI game (see setup) until completion and checks for discard actions.
     */
    @Test
    public void testAIPlayDiscards(){
        ai1 = (AIPlayer) model.getPlayers().get(0);

        /*
         * If the AI decides to discard, the score should not change at the end of the turn
         */
        ai1.addControllerListener(new SControllerListener() {
            private static int oldScore = 0;

            @Override
            public void handleControllerEvent(ControllerEvent e) {
                // Check discard test assumption
                if (e instanceof DiscardClickEvent){
                    assertEquals(oldScore, ai1.getScore());
                }
                // Update score each turn taken
                oldScore = ai1.getScore();
            }
        });

        // I think model should auto-play when it only has AI players
        model.startGame();
    }

    /**
     * Test the place actions from the first AI player.
     * Plays a 3 AI game (see setup) until completion and checks for discard actions.
     */
    @Test
    public void testAIPlayPlaces(){
        ai1 = (AIPlayer) model.getPlayers().get(0);

        /*
         * If the AI decides to place, the score should increase after the end of the turn
         */
        ai1.addControllerListener(new SControllerListener() {
            private static int oldScore = 0;

            @Override
            public void handleControllerEvent(ControllerEvent e) {
                // Check place test assumption
                if (e instanceof PlaceClickEvent) {
                    assertTrue(oldScore > ai1.getScore());
                }
                // Update score each turn taken
                oldScore = ai1.getScore();
            }
        });

        // I think model should auto-play when it only has AI players
        model.startGame();
    }

}
