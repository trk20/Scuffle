import java.util.ArrayList;
import java.util.List;

/**
 * Class Player takes care of information related to players.
 * They have names, letters to play, and a score to keep track of.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-23
 */
public class Player {
    /** Player's display name */
    final private String name;
    /** Player's Hand (holds their letters) */
    final private Hand hand;
    /** Player's cumulative score for the game */
    private int score;
    /** Game model, has a shared DrawPile, and a Board */
    final private ScrabbleModel model;

    /**
     * Player constructor, sets a name from the name parameter,
     * initializes an empty hand, and sets score to 0 initially.
     *
     * @param name the Player's display name
     * @param model the Scrabble game's model
     */
    public Player(String name, ScrabbleModel model) {
        this.name = name;
        this.model = model;
        this.hand = new Hand(model.getDrawPile());
        this.score = 0;
        fillHand();
    }

    /**
     * Called to try to place letters on the board.
     * Checks if PLayer has the letters to play.
     * Does not do any board verification!
     *
     * @param used List of letters to place (in order)
     * @return True if hand contains used letters, false otherwise.
     *
     * @throws NullPointerException to indicate that the game's DrawPile is empty.
     */
    public boolean placeLetters(List<Letter> used) throws NullPointerException{
        return hand.useLetters(used);
    }

    /**
     * Called to discard letters (and draw the same amount)
     *
     * @param used List of letters to discard
     * @return True if hand contains used letters, false otherwise.
     *
     * @author Alexandre Marques - 101189743
     */
    public boolean discardLetters(List<Letter> used){
        // Add the letters to be removed to the model's DrawPile
        model.getDrawPile().addToPile(used);
        // Remove the letters from the hand (return true if hand contains used letters)
        return hand.useLetters(used);

        /* Note: Will always be able to draw enough letters. -> no empty pile exception
         * Worst case scenario: DrawPile is empty, discard hand, Player draws their own hand back.
         * This works only because "useLetters" is called after "addToPile" -> order is important!
         */
    }

    /**
     * Add points from a placement to this Player's score.
     *
     * @param points points to be added to the score
     * @author Alexandre Marques - 101189743
     */
    public void addPoints(int points){
        this.score += points;
    }

    /**
     * Fills the hand
     */
    private void fillHand(){
        hand.fillHand();
    }

    public Hand getHand(){
        return hand;
    }

    /**
     * Verifies if a player has the letters to place a given word
     * @param word
     * @return
     */
    public boolean canPlaceWord(String word){
        char[] ch = word.toCharArray();
        List<Character> handLetters = hand.getCharLetters();
        for(char c : ch){
            if (!handLetters.contains(Character.toUpperCase(c))){
                return false;
            }
        }
        return true;
    }

    /**
     * String representation of player, includes their player name.
     *
     * @return Player's name String
     * @author Alexandre Marques - 101189743
     */
    @Override
    public String toString() {
        return name;
    }
}
