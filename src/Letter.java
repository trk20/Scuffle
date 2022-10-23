import java.util.ArrayList;

/**
 * Enumeration class Letter, contains constants for each Scrabble letter.
 * Assigns a letter score (int), and a character (String) for each Letter.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-19
 */
public enum Letter {
    // Letter enums, sorted by order of frequency, in tiles/game
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
    Z('Z', 10, 1);

    // Fields
    /** Score associated with letter (defined by Scrabble rules) */
    private final int score;
    /** Character associated with letter (used for printing) */
    private final char character; // char to enforce only one character
    /** Frequency of appearance, how many copies are in a fresh draw pile*/
    private final int frequency;

    /**
     * Letter Constructor, initializes fields with given parameters.
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
     * Returns the score associated to this Letter.
     *
     * @author Alexandre Marques - 101189743
     * @return Letter's Scrabble score.
     */
    // TODO: Unit tests for this
    public int getScore() {
        return score;
    }

    /**
     * Returns the frequency associated to this Letter.
     *
     * @author Alexandre Marques - 101189743
     * @return Letter's Scrabble frequency.
     */
    public int getFrequency() {
        return frequency;
    }

    /**
     * Convert a word string to a list of Letters.
     * Assumes all characters are valid.
     *
     * @param word a word to be seperated into individual Letter enums,
     *             contains only valid Letter characters.
     * @return An ArrayList of letters corresponding to the word's characters.
     * @author Alexandre Marques - 101189743
     */
    public static ArrayList<Letter> wordToLetters(String word){
        // Empty letters list
        ArrayList<Letter> letters = new ArrayList<>();
        // TODO: Find a letter for each char in the string...

        return letters;
    }

    /**
     * Returns the String representation of Letter
     *
     * @author Alexandre Marques - 101189743
     * @return The character associated to the Letter, as a string.
     */
    @Override
    // TODO: Unit tests for this
    public String toString() {
        return ""+character; // Concatenate char to convert to String
    }
}
