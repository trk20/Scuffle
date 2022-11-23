package Tests;

import Model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AIPlayerTest {
    List<String> playerNames = Arrays.asList("Vlad","Alex","Tim","Kieran");

    ScrabbleModel model = new ScrabbleModel(playerNames);
    AIPlayer player = new AIPlayer("Ai", model);

    ArrayList<Tile> tiles = new ArrayList<>();
    Hand hand = new Hand(model.getDrawPile());
    Point start;
    @BeforeEach
    void setup(){
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

        ArrayList<ArrayList<String>> expectedResult = new ArrayList<>();
        ArrayList<String> expectedResult1 = new ArrayList<>();

        expectedResult1.add("Q");
        expectedResult1.add("I");
        expectedResult.add(expectedResult1);

        assertEquals(expectedResult, player.getValidWords(startTile, hand));

    }

    @Test
    void testGetValidStartingWords() {
        ArrayList<ArrayList<String>> expectedResult = new ArrayList<>();

        ArrayList<String> expectedResult1 = new ArrayList<>();
        expectedResult1.add("E");
        expectedResult1.add("L");

        ArrayList<String> expectedResult2 = new ArrayList<>();
        expectedResult2.add("L");
        expectedResult2.add("I");

        expectedResult.add(expectedResult1);
        expectedResult.add(expectedResult2);

        assertEquals(expectedResult, player.getValidWords(hand));
    }
}