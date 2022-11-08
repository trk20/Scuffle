package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class Model.DrawPile takes care of the letter drawing logic.
 * Keeps track of drawn letters to reduce their draw likelihoods.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-22
 */
public class DrawPile {
    /** Contains all letter tiles not drawn in the game yet*/
    private List<Letter> letterPile;

    /**
     * Model.DrawPile constructor, initializes letterPile to its initial state.
     *
     * @see #resetPile()
     * @author Alexandre Marques - 101189743
     */
    DrawPile(){
        resetPile();
    }

    /**
     * Sets letterPile to its initial state,
     * That is each Model.Letter should appear exactly as many
     * times in the letterPile as their defined frequency says.
     * Then, randomises the list order (for random draws).
     *
     * @author Alexandre Marques - 101189743
     */
    private void resetPile() {
        // Quick reset -> makes empty list
        this.letterPile = new ArrayList<>();

        // For each Model.Letter enum...
        for(Letter l: Letter.values()){
            // Add one copy of the letter until
            // there are as many copies as the letter's frequency
            for(int i = 0; i < l.getFrequency(); i++){
                letterPile.add(l);
            }
        }
        // Shuffle list for random draw order
        Collections.shuffle(letterPile);
    }

    /**
     * Adds a list of discarded letters back into the pile.
     * Reshuffles pile to keep a random order.
     *
     * @param discarded List of discarded letters to add back to the pile
     * @author Alexandre Marques - 101189743
     */
    public void addToPile(List<Letter> discarded) {
        // Add all the discarded letters back to the pile
        letterPile.addAll(discarded);
        // Shuffle list for random draw order
        Collections.shuffle(letterPile);
    }

    /**
     * Removes and returns a random letter from the letterPile.
     * In the case of an empty pile, returns null.
     *
     * @return A randomly picked letter from the Model.DrawPile if possible,
     *  otherwise null.
     *  @author Alexandre Marques - 101189743
     */
    public Letter draw() {
        // If list is empty, return null
        if(letterPile.isEmpty()) return null;
        // Pile order is already randomised so return first element
        return letterPile.remove(0);
    }
}
