package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Class Model.Hand takes care of holding, playing, and drawing
 * Model.Letter tiles for a Model.Player.
 * When playing letters, verifies the Model.Hand contains them.
 *
 * @author Alexandre Marques - 101189743
 * @version NOV-9
 */
public class Hand {
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
    public Hand(DrawPile pile){
        // Initialize fields to default / parameter values
        this.pile = pile;
        tiles = new ArrayList<>();
        // Start hand in filled state
        fillHand();
        // TOOD: M2, reset hand, set tile, use selectedtiles field in useTiles.
    }

    /**
     * Keeps drawing (with draw()) until the HAND_SIZE Model.Letter limit is reached.
     *
     * @see #draw()
     * @throws NullPointerException if draw pile has no more letters to draw.
     * @author Alexandre Marques - 101189743
     */
    // TODO: Consider making custom exception (i.e. EmptyPileException)
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
    // TODO: Consider making custom exception (i.e. EmptyPileException)
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
    @Deprecated // We won't need to check this if they can't input their own tiles.
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
//        if(!containsTiles(used)){
//            return false;
//        }

        // For each used letter, remove it from the hand
        Iterator tileIterator = tiles.listIterator();
        while(tileIterator.hasNext()){
            if(used.contains(tileIterator.next())) tileIterator.remove();
        }

        /* Letters were used, so Model.Hand needs to be filled.
         * Note: Throws exception for empty draw pile!
         */
        fillHand();
//        return true;
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
            Letter l = t.letter();
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

//    /**
//     * Discards the tiles selected, and draws back from the draw pile afterwards.
//     * Precondition: Assumes all selected tiles are in the hand
//     * @param selectedTiles list of tiles to remove from the hand
//     */
//    // TODO: change this call to use tiles
//    public void discardSelected(List<Tile> selectedTiles) {
//        for(Tile t: selectedTiles){
//            tiles.remove(t);
//        }
//        fillHand();
//    }
}
