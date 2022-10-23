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

    /**
     * Player constructor, sets a name from the name parameter,
     * initializes an empty hand, and sets score to 0 initially.
     *
     * @param name the Player's display name
     * @param drawPile the game's shared drawPile
     */
    public Player(String name, DrawPile drawPile) {
        this.name = name;
        this.hand = new Hand(drawPile);
        this.score = 0;
    }

    /**
     * Called to try to place letters on the board
     *
     * @param used List of letters to place (in order)
     */
    public void placeLetters(List<Letter> used){
        return;
    }

    /**
     * Called to discard letters (and draw the same amount)
     *
     * @param used List of letters to discard
     */
    public void discardLetters(List<Letter> used){
        // TODO: need to pass "used" to draw pile
        //  -> make a way to add letters back in (DrawPile method)
        return;
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
