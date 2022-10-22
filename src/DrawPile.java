import java.util.ArrayList;

/**
 * Class DrawPile takes care of the letter drawing logic.
 * Keeps track of drawn letters to reduce their draw likelihoods.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-22
 */
public class DrawPile {
    /** Contains all letter tiles not drawn in the game yet*/
    private ArrayList<Letter> letterPile;

    /**
     * DrawPile constructor, initializes letterPile to its initial state.
     *
     * @see #resetPile()
     */
    DrawPile(){
        resetPile();
    }

    /**
     * Sets letterPile to its initial state,
     * That is each Letter should appear exactly as many
     * times in the letterPile as their defined frequency says.
     */
    private void resetPile() {
        // Quick reset
        this.letterPile = new ArrayList<>();
    }

    /**
     * Removes and returns a random letter from the letterPile.
     * In the case of an empty pile, returns null.
     *
     * @return A randomly picked letter from the DrawPile if possible,
     *  otherwise null.
     */
    public Letter draw() {
        return null; //TODO
    }
}
