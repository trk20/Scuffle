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
}
