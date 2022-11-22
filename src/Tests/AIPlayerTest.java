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

import java.util.ArrayList;
import java.util.Arrays;
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
        model = new ScrabbleModel(Arrays.asList("Tim"));
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
//        ai.addControllerListener(new SControllerListener() {
//            private static int oldScore = 0;
//
//            @Override
//            public void handleControllerEvent(ControllerEvent e) {
//                // Check discard test assumption
//                if (e instanceof DiscardClickEvent){
//                    assertEquals(oldScore, ai1.getScore());
//                }
//                // Update score each turn taken
//                oldScore = ai1.getScore();
//            }
//        });

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
//        ai1.addControllerListener(new SControllerListener() {
//            private static int oldScore = 0;
//
//            @Override
//            public void handleControllerEvent(ControllerEvent e) {
//                // Check place test assumption
//                if (e instanceof PlaceClickEvent) {
//                    assertTrue(oldScore > ai1.getScore());
//                }
//                // Update score each turn taken
//                oldScore = ai1.getScore();
//            }
//        });

        // I think model should auto-play when it only has AI players
        model.startGame();
    }

    @Test
    public void dictionaryGetter(){
        DictionaryHandler dict = new DictionaryHandler();
        Hand hand = new Hand(new DrawPile());
        System.out.println(hand.getHeldTiles());
        BoardTile boardTile = new BoardTile(7,7);
        boardTile.setLetter(Letter.X);
        AIPlayer player = new AIPlayer("BOB",model);
        System.out.println(player.getValidWords(boardTile,hand));
    }

    @Test
    public void transferTest(){
        AIPlayer player = new AIPlayer("BOB",model);
        Hand hand = new Hand(new DrawPile());
        ArrayList<Tile> tiles1 = new ArrayList<>(hand.getHeldTiles().subList(0,3));
        ArrayList<Tile> tiles2 = new ArrayList<>();
        ArrayList<String> word = new ArrayList<String>();
        for(Tile tile:tiles1){
            word.add(tile.getLetter().name());
        }
        System.out.println(tiles1 + " -> " + word + " -> "  + tiles2);
        player.transferTilesMatchingWord(tiles1,tiles2,word);
        System.out.println(tiles1 + " -> " + tiles2);

    }

}
