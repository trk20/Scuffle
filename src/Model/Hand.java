package Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Class Model.Hand takes care of holding, playing, and drawing
 * Model.Letter tiles for a Model.Player.
 * When playing letters, verifies the Model.Hand contains them.
 *
 * @author Alexandre Marques - 101189743
 * @version NOV-9
 */
public class Hand implements Serializable {
    /** The game's draw pile (should be the same for all players/hands in the game) */
    final private DrawPile pile;
    /** The Model.Hand's contained letters*/
    private List<Tile> tiles;
    /** Model.Hand's maximum capacity. Always draw back up to it when using letters*/
    final static private int HAND_SIZE = 7;

    /**
     * Model.Hand constructor, saves draw pile and
     * creates an "empty" hand, has no Letters yet.
     *
     * @author Alexandre Marques - 101189743
     */
    public Hand(DrawPile pile, boolean fillHand){
        // Initialize fields to default / parameter values
        this.pile = pile;
        tiles = new ArrayList<>();
        // Start hand in filled state
        if (fillHand) fillHand();
        // TOOD: M2, reset hand, set tile, use selectedtiles field in useTiles.
    }

    /**
     * Keeps drawing (with draw()) until the HAND_SIZE Model.Letter limit is reached.
     *
     * @see #draw()
     * @throws NullPointerException if draw pile has no more letters to draw.
     * @author Alexandre Marques - 101189743
     */
    private void fillHand() throws NullPointerException {
        // Keep drawing until reaching hand limit
        while(tiles.size() < HAND_SIZE) {
            draw(); // Throws here (if empty draw)
        }
    }

    /**
     * Draw a letter from the game's Model.DrawPile,
     * then add it to the hand's letters.
     *
     * @throws NullPointerException if draw pile has no more letters to draw.
     * @author Alexandre Marques - 101189743
     */
    private void draw() throws NullPointerException {
        // Check for valid letter (non-null value)
        Tile newTile = pile.draw();
        // Indicates empty draw pile
        if (newTile == null) {
            throw new NullPointerException("No more letters in draw pile.");
        }
        // Should be a valid letter (not null)
        tiles.add(newTile);
    }

    /**
     * Checks if "used" tiles are all in the Hand.
     *
     * @return If the letters used are all in the hand, returns true.
     * Otherwise, return False.
     * @author Alexandre Marques - 101189743
     */
    private boolean containsTiles(List<Tile> used){
        for(Tile t: used){
            // If l is not in hand, return false
            if(!(tiles.contains(t)))

                return false;
        }
        // Every letter used is contained in the Model.Hand
        return true;
    }

    /**
     * Remove letters used from the hand,
     * Precondition: Assumes tiles are contained
     *
     * @author Alexandre Marques - 101189743
     * @throws NullPointerException to indicate that the game's Model.DrawPile is empty.
     */
    public void useTiles(List<Tile> used) throws NullPointerException{
        // We assume with the GUI that the player/model would never choose tiles not contained in hand
        assert(containsTiles(used));

        // For each used letter, remove it from the hand
        tiles.removeIf(used::contains);

        /* Letters were used, so Model.Hand needs to be filled.
         * Note: Throws exception for empty draw pile!
         */
        fillHand();
    }

    /**
     * Shows letters contained in the Model.Hand.
     * Format: "Model.Hand: (A, 1) (B, 1) {other letters}"
     *
     * @return String representation of Model.Hand object.
     * @author Alexandre Marques - 101189743
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("Model.Hand: ");
        // Append each letter, + a trailing space
        for (Tile t: tiles) {
            Letter l = t.getLetter();
            // Format: "(A, 1) "
            sb.append("(").append(l).append(", ").append(l.getScore()).append(") ");
        }
        // Trim last trailing space (and return string)
        return sb.toString().trim();
    }


    /**
     * Get held tiles.
     *
     * @return List of held Tile(s)
     * @author Alexandre Marques
     */
    public List<Tile> getHeldTiles() {
        return tiles;
    }

    /**
     * For Testing purposes, used to set the tiles in the Hand
     * @param tiles List of Tiles in the Hand
     */

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    public DrawPile getDrawPile() {
        return pile;
    }

    public void addTile(Tile t){
        tiles.add(t);
    }
}
