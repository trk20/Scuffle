package Model;

/**
 * Enumeration class Model.Letter, contains constants for each Scrabble letter.
 * Assigns a letter score (int), and a character (String) for each Model.Letter.
 *
 * @author Alexandre Marques - 101189743
 * @version NOV-21
 */
public enum Letter {
    // Model.Letter enums, sorted by order of frequency, in tiles/game
    E('E', 1, 12),
    A('A', 1, 9),
    I('I', 1, 9),
    O('O', 1, 8),
    N('N', 1, 6),
    R('R', 1, 6),
    T('T', 1, 6),
    L('L', 1, 4),
    S('S', 1, 4),
    U('U', 1, 4),
    D('D', 2, 4),
    G('G', 2, 3),
    B('B', 3, 2),
    C('C', 3, 2),
    M('M', 3, 2),
    P('P', 3, 2),
    F('F', 4, 2),
    H('H', 4, 2),
    W('W', 4, 2),
    Y('Y', 4, 2),
    V('V', 4, 2),
    K('K', 5, 1),
    J('J', 8, 1),
    X('X', 8, 1),
    Q('Q', 10, 1),
    Z('Z', 10, 1),
    BLANK(' ', 0, 2);

    // Fields
    /** Score associated with letter (defined by Scrabble rules) */
    private final int score;

    /** Character associated with letter (used for printing) */
    private final char character; // char to enforce only one character

    /** Frequency of appearance, how many copies are in a fresh draw pile*/
    private final int frequency;
    /**
     * Model.Letter Constructor, initializes fields with given parameters.
     *
     * @param character Character to associate with letter
     * @param score     Score to associate with letter
     * @param freq      Frequency to associate with letter
     * @author Alexandre Marques - 101189743
     */
    Letter (char character, int score, int freq){
        this.score = score;
        this.character = character;
        this.frequency = freq;
    }

    /**
     * Returns the score associated to this Model.Letter.
     *
     * @author Alexandre Marques - 101189743
     * @return Model.Letter's Scrabble score.
     */
    public int getScore() {
        return score;
    }

    /**
     * Returns the frequency associated to this Model.Letter.
     *
     * @author Alexandre Marques - 101189743
     * @return Model.Letter's Scrabble frequency.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Returns the String representation of Model.Letter
     *
     * @author Alexandre Marques - 101189743
     * @return The character associated to the Model.Letter, as a string.
     */
    @Override
    public String toString() {
        return ""+character; // Concatenate char to convert to String

    }
}
