package Model;

import java.io.Serializable;

/**
 * Tile model class, represents a tile in the scrabble game.
 * Stores the score of its initial letter.
 * The letter can be changed if the tile was originally a blank (0 score),
 * but never the score.
 *
 * @author Alex, Vladimir
 * @version NOV-20
 */
public class Tile implements Serializable {
    /**
     * Compares two tiles to see if they are equal.
     * For tiles, this means they are referring to the same record in memory. (same letters does not mean equal!!)
     *
     * @param obj object being compared
     * @return true if the object is referring to this tile in memory, false otherwise
     */
    private Letter letter;
    private int score;

    /**
     * Constructor of the Tile class, stores the letter and score of the Tile
     * @author Vladimir Kovacina
     * @param letter: the letter of the Tile
     */
    public Tile(Letter letter){
        this.letter = letter;
        score = letter.getScore();
    }
    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true otherwise return false
        return this == obj;
    }

    /**
     * Method used to get the Score of the tile, the score of the Tile
     * should not be able to change once the Tile is created
     * @author Vladimir Kovacina
     * @return the score of the Tile, int
     */
    public int getScore(){
        return score;
    }

    /**
     * Method used to set the Letter of the Tile
     * @author: Vladimir Kovacina
     * @param letter ,the letter to set the Tile to
     */
    public void setLetter(Letter letter) {
        //Check to see if initial tile was blank
        if (this.score == 0) {
            this.letter = letter;
        }
    }

    /**
     * Method used to get the Tile's letter
     * @author Vladimir Kovacina
     * @return Letter, the Tile's letter
     */
    public Letter getLetter() {
        return letter;
    }
}
