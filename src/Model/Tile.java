package Model;

/**
 * Tile model class, represents a tile in the scrabble game.
 * Has a Letter reference.
 * Serves as a wrapper to differentiate between two tiles with the same Letter.
 *
 * @author Alex
 * @version NOV-12
 */
public class Tile {
    /**
     * Compares two tiles to see if they are equal.
     * For tiles, this means they are referring to the same record in memory. (same letters does not mean equal!!)
     *
     * @param obj object being compared
     * @return true if the object is referring to this tile in memory, false otherwise
     */
    private Letter letter;
    private int score;
    public Tile(Letter letter){
        this.letter = letter;
        score = letter.getScore();
    }
    @Override
    public boolean equals(Object obj) {
        // If the object is compared with itself then return true otherwise return false
        return this == obj;
    }
    public int getScore(){
        return score;
    }

    public void setLetter(Letter letter) {
        //Check to see if initial tile was blank
        if (this.score == 0) {
            this.letter = letter;
        }
    }
    public Letter getLetter() {
        return letter;
    }
}
