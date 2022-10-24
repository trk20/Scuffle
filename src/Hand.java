import java.util.ArrayList;
import java.util.List;

/**
 * Class Hand takes care of holding, playing, and drawing
 * Letter tiles for a Player.
 * When playing letters, verifies the Hand contains them.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-23
 */
public class Hand {
    /** The game's draw pile (should be the same for all players/hands in the game) */
    final private DrawPile pile;
    /** The Hand's contained letters*/
    final private List<Letter> letters;
    /** Hand's maximum capacity. Always draw back up to it when using letters*/
    final static private int HAND_SIZE = 7;

    /**
     * Hand constructor, saves draw pile and
     * creates an "empty" hand, has no Letters yet.
     *
     * @author Alexandre Marques - 101189743
     */
    Hand(DrawPile pile){
        // Initialize fields to default / parameter values
        this.pile = pile;
        letters = new ArrayList<>();
    }

    /**
     * Keeps drawing (with draw()) until the HAND_SIZE Letter limit is reached.
     *
     * @see #draw()
     * @throws NullPointerException if draw pile has no more letters to draw.
     * @author Alexandre Marques - 101189743
     */
    // TODO: Consider making custom exception (i.e. EmptyPileException)
    public void fillHand() throws NullPointerException {
        // Keep drawing until reaching hand limit
        while(letters.size() < HAND_SIZE) {
            draw();
        }
    }

    /**
     * Draw a letter from the game's DrawPile,
     * then add it to the hand's letters.
     *
     * @throws NullPointerException if draw pile has no more letters to draw.
     * @author Alexandre Marques - 101189743
     */
    // TODO: Consider making custom exception (i.e. EmptyPileException)
    private void draw() throws NullPointerException {
        // Check for valid letter (non-null value)
        Letter newLetter = pile.draw();
        // Indicates empty draw pile
        if (newLetter == null) {
            throw new NullPointerException("No more letters in draw pile.");
        }
        // Should be a valid letter (not null)
        letters.add(newLetter);
    }

    /**
     * Checks if "used" letters are all in the Hand.
     *
     * @return If the letters used are all in the hand, returns true.
     * Otherwise, return False.
     * @author Alexandre Marques - 101189743
     */
    private boolean containsLetters(List<Letter> used){
        for(Letter l: used){
            // If l is not in hand, return false
            if(!(letters.contains(l)))
                return false;
        }
        // Every letter used is contained in the Hand
        return true;
    }

    /**
     * Remove letters used from the hand,
     * if they are all in the hand (return true).
     * Otherwise, does nothing (return false).
     *
     * @return If the letters used are all in the hand, returns true.
     *  Otherwise, return False.
     * @author Alexandre Marques - 101189743
     *
     * @throws NullPointerException to indicate that the game's DrawPile is empty.
     */
    public boolean useLetters(List<Letter> used) throws NullPointerException{
        // Does not contain letters
        if(!containsLetters(used)){
            return false;
        }
        // For each used letter, remove it from the hand
        for(Letter l: used){
            letters.remove(l);
        }

        /* Letters were used, so Hand needs to be filled.
         * Note: Throws exception for empty draw pile!
         */
        fillHand();

        // Valid removal, return true
        return true;
    }

    /**
     * Return the hand as an array of chars
     */
    public List<Character> getCharLetters(){
        List<Character> chars = new ArrayList<>(letters.size());
        for(Letter l: letters){
            chars.add(l.getCharLetter());
        }
        return chars;
    }

    /**
     * Shows letters contained in the Hand.
     * Format: "Hand: A B C D"
     *
     * @return String representation of Hand object.
     * @author Alexandre Marques - 101189743
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Hand: ");
        // Append each letter, + a trailing space
        for (Letter l: letters) {
            sb.append(l).append(" ");
        }
        // Trim last trailing space (and return string)
        return sb.toString().trim();
    }
}
