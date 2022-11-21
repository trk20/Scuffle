package Model;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.DiscardEvent;
import ScrabbleEvents.ModelEvents.ModelEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Model.Player takes care of information related to players.
 * They have names, letters to play, and a score to keep track of.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-23
 */
public class Player implements SModel {
    /** Model.Player's display name */
    final private String name;
    /** Model.Player's Model.Hand (holds their letters) */
    private Hand hand;
    /** Model.Player's cumulative score for the game */
    private int score;
    /** Game model, has a shared Model.DrawPile, and a Model.Board */

    private List<ModelListener> modelListeners;

    /**
     * Model.Player constructor, sets a name from the name parameter,
     * initializes an empty hand, and sets score to 0 initially.
     *
     * @param name the Model.Player's display name
     * @param pile the draw pile to notify on discard
     */
    public Player(String name, DrawPile pile) {
        //needs model to add it as a listener
        this.name = name;
        this.modelListeners = new ArrayList<>();
        this.hand = new Hand(pile);
        this.addModelListener(pile);
        this.score = 0;
    }

    /**
     * Called to try to place letters on the board.
     * Checks if PLayer has the letters to play.
     * Does not do any board verification!
     *
     * @param used List of letters to place (in order)
     *
     * @throws NullPointerException to indicate that the game's Model.DrawPile is empty.
     */
    // TODO: now that its void, can return a bool for empty draw piles
    public void placeTiles(List<Tile> used) throws NullPointerException{
        hand.useTiles(used);
    }

    /**
     * Called to discard letters (and draw the same amount)
     *
     * @param used List of letters to discard
     *
     * @author Alexandre Marques - 101189743
     */
    public void discardTiles(List<Tile> used){
        // Notify listeners of a discard event
        notifyModelListeners(new DiscardEvent(used));
        // Remove the letters from the hand
        hand.useTiles(used);
    }



    /**
     * Add points from a placement to this Model.Player's score.
     *
     * @param points points to be added to the score
     * @author Alexandre Marques - 101189743
     */
    public void addPoints(int points){
        this.score += points;
    }

    /**
     * String representation of player in this format:
     * "========{name}========
     *  Score: {score}
     *  {hand}"
     *
     * @return Model.Player's String representation
     * @author Alexandre Marques - 101189743
     */
    @Override
    public String toString() {
        // ========{name}========:\n
        return "=".repeat(8) + name + "=".repeat(8) + "\n" +
                // Score: {score}\n
                "Score: " + score + "\n" +
                // {hand}
                hand;
    }

    /**
     * Checks if the Model.Player has no more letters to play.
     * @return True if there are no more letters in the Model.Hand.
     */
    public boolean outOfTiles(){
        // No more letters in the hand -> size == 0
        return hand.getHeldTiles().size() == 0;
    }

    /**
     * Get player's hand.
     * @return the player's hand.
     */
    public Hand getHand() {
        return hand;
    }
    
    /**
     * Get player's score
     * @return the player's score
     */
    public int getScore() {
        return score;
    }

    /**
     * Gets the player's name
     * @return the player's name
     */
    public String getName() {
        return name;
    }

    /**
     * For testing purposes, sets the players hand
     * @param hand the new players hand
     */
    @Deprecated
    public void setHand(Hand hand) {
        this.hand = hand;
    }


    @Override
    public void addModelListener(ModelListener l) {
        modelListeners.add(l);
    }

    @Override
    public void notifyModelListeners(ModelEvent e) {
        for(ModelListener listener:modelListeners){
            listener.handleModelEvent(e);
        }
    }
}
