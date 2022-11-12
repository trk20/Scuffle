package Model;

/**
 * Tile model class, represents a tile in the scrabble game.
 * Has a Letter reference.
 * Serves as a wrapper to differentiate between two tiles with the same Letter.
 *
 * @version NOV-11
 * @author Alex
 */
public class Tile {
    Letter letter;

    /**
     * Tile constructor, initializes letter
     *
     * @param letter The letter enum the Tile represents
     */
    public Tile(Letter letter){
        this.letter = letter;
    }

    /**
     * Accessor for the Tile's held letter
     * @return letter enum held by tile
     */
    public Letter getLetter() {
        return letter;
    }

    /**
     * Compares two tiles to see if they are equal
     * @param obj tile being compared
     * @return true is tiles are the same, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof Tile)) {
            return false;
        }

        Tile t = (Tile) obj;

        return this.getLetter() == t.getLetter();
    }
}
