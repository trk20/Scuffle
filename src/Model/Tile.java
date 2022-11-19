package Model;

/**
 * Tile model class, represents a tile in the scrabble game.
 * Has a Letter reference.
 * Serves as a wrapper to differentiate between two tiles with the same Letter.
 *
 * @author Alex
 * @version NOV-12
 */
public record Tile(Letter letter){
    /**
     * Compares two tiles to see if they are equal.
     * For tiles, this means they are referring to the same record in memory. (same letters does not mean equal!!)
     *
     * @param obj object being compared
     * @return true if the object is referring to this tile in memory, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true otherwise return false
        return this == obj;
    }

    /**
     * Gets a tile's score
     *
     * @return the tile's score
     */
    public int getScore() {
        return 0;
    }
}
