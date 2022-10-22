import java.util.ArrayList;

/**
 * Class Hand takes care of holding Letter tiles for a Player.
 * Keeps track of letters currently help by the Player,
 * checking if the Player has the Letter(s) to play a word,
 * getting new letters from the DrawPile.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-22
 */
public class Hand {
    /** The game's draw pile (should be the same for all players/hands in the game) */
    final private DrawPile pile;
    /** The Hand's contained letters*/
    final private ArrayList<Letter> letters;

    /**
     * Hand constructor, saves draw pile and
     * creates an "empty" hand, has no Letters yet.
     */
    Hand(DrawPile pile){
        // Initialize fields to default / parameter values
        this.pile = pile;
        letters = new ArrayList<>();
    }

    /**
     * Draw a letter from the game's DrawPile,
     * then add it to the hand's letters.
     */
    public void draw(){
        // letters.add(pile.draw());
        letters.add(Letter.A); // TODO
    }

    /**
     * Checks if "used" letters are all in the Hand.
     *
     * @return If the letters used are all in the hand, returns true.
     * Otherwise, return False.
     */
    private boolean containsLetters(ArrayList<Letter> used){
        return false; // TODO
    }

    /**
     * Remove letters used from the hand,
     * if they are all in the hand (return true).
     * Otherwise, does nothing (return false).
     *
     * @return If the letters used are all in the hand, returns true.
     *  Otherwise, return False.
     */
    public boolean useLetters(ArrayList<Letter> used){
        return false; // TODO
    }

    /**
     * Shows letters contained in the Hand.
     * Format: "Hand: A, B, C, D."
     *
     * @return String representation of Hand object.
     */
    @Override
    public String toString(){
        return ""; // TODO
    }
}
