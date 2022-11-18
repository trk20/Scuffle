package Model;

import java.util.List;

/**
 * Class Model.Player takes care of information related to players.
 * They have names, letters to play, and a score to keep track of.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-23
 */
public class Player {
    /** Model.Player's display name */
    final private String name;
    /** Model.Player's Model.Hand (holds their letters) */
    private Hand hand;
    /** Model.Player's cumulative score for the game */
    private int score;
    /** Game model, has a shared Model.DrawPile, and a Model.Board */
    final private ScrabbleModel model;

    /**
     * Model.Player constructor, sets a name from the name parameter,
     * initializes an empty hand, and sets score to 0 initially.
     *
     * @param name the Model.Player's display name
     * @param model the Scrabble game's model
     */
    public Player(String name, ScrabbleModel model) {
        this.name = name;
        this.model = model;
        this.hand = new Hand(model.getDrawPile());
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
     * @return True if hand contains used letters, false otherwise.
     *
     * @author Alexandre Marques - 101189743
     */
    @Deprecated
    public void discardTiles(List<Tile> used){
        // Add the letters to be removed to the model's Model.DrawPile
        model.getDrawPile().addToPile(used);
        // Remove the letters from the hand (return true if hand contains used letters)
        hand.useTiles(used);

        /* Note: Will always be able to draw enough letters. -> no empty pile exception
         * Worst case scenario: Model.DrawPile is empty, discard hand, Model.Player draws their own hand back.
         * This works only because "useLetters" is called after "addToPile" -> order is important!
         */
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
        StringBuilder sb = new StringBuilder();
        // ========{name}========:\n
        sb.append("=".repeat(8)).append(name).append("=".repeat(8)).append("\n");
        // Score: {score}\n
        sb.append("Score: ").append(score).append("\n");
        // {hand}
        sb.append(hand);
        return sb.toString();
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
}
