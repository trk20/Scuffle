package Tests;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Class for testing AiPlayer
 *
 *  @author Kieran Rourke
 */
class AIPlayerTest {

    ScrabbleModel model;
    AIPlayer player;

    ArrayList<Tile> tiles = new ArrayList<>();
    Hand hand;
    Point start;
    @BeforeEach
    void setup(){
        HashMap<String,Boolean> playerInfo = new HashMap<>();
        playerInfo.put("AI",true);
        playerInfo.put("notAI",false);
        model = new ScrabbleModel(playerInfo);
        hand = model.getCurHand();
        player = (AIPlayer) model.getCurPlayer();
        tiles = new ArrayList<>();
        tiles.add(new Tile(Letter.L));
        tiles.add(new Tile(Letter.I));
        tiles.add(new Tile(Letter.E));
        start = new Point(7, 7);
        hand.setTiles(tiles);
        player.setHand(hand);

    }

    @Test
    void play() {
        player.play();

        assertNotNull(model.getBoard().getBoardTile(start).getLetter());
    }

    @Test
    void getValidWords() {
        BoardTile startTile = new BoardTile(start);
        startTile.setLetter(Letter.Q);

        DictionaryHandler dictionaryHandler = new DictionaryHandler();

        player.getValidWordsNew(startTile, hand).forEach(word->{
            assert(dictionaryHandler.isValidWord(String.join("",word)));
            assert(word.contains("Q"));
        });
    }

    @Test
    void testGetValidStartingWords() {
        HashSet<ArrayList<String>> expectedResult = new  HashSet<>();

        ArrayList<String> expectedResult1 = new ArrayList<>();
        expectedResult1.add("E");
        expectedResult1.add("L");

        ArrayList<String> expectedResult2 = new ArrayList<>();
        expectedResult2.add("L");
        expectedResult2.add("I");

        expectedResult.add(expectedResult1);
        expectedResult.add(expectedResult2);

        assert(player.getValidWordsNew(hand).contains(expectedResult1));
        assert(player.getValidWordsNew(hand).contains(expectedResult2));
    }
}
