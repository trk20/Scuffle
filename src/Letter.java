/**
 * Enumeration class Letter, contains constants for each Scrabble letter.
 * Assigns a letter score (int), and a character (String) for each Letter.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-19
 */
public enum Letter {
    // Letter enums, sorted by order of frequency, in tiles/game
    E('E', 1), // 12 (12 tiles/game)
    A('A', 1), // 9 (9 tiles)
    I('I', 1), // 9
    O('O', 1), // 8 (8 tiles)
    N('N', 1), // 6 (6 tiles)
    R('R', 1), // 6
    T('T', 1), // 6
    L('L', 1), // 4 (4 tiles)
    S('S', 1), // 4
    U('U', 1), // 4
    D('D', 2), // 4
    G('G', 2), // 3 (3 tiles)
    B('B', 3), // 2 (2 tiles)
    C('C', 3), // 2
    M('M', 3), // 2
    P('P', 3), // 2
    F('F', 4), // 2
    H('H', 4), // 2
    V('V', 4), // 2
    W('W', 4), // 2
    Y('Y', 4), // 2
    K('K', 5), // 1 (1 tile)
    J('J', 8), // 1
    X('X', 8), // 1
    Q('Q', 10), // 1
    Z('Z', 10); // 1

    // Fields
    /** Score associated with letter (defined by Scrabble rules) */
    private final int score;
    /** Character associated with letter (used for printing) */
    private final char character; // char to enforce only one character

    /**
     * Letter Constructor, initializes fields with given parameters.
     *
     * @author Alexandre Marques - 101189743
     * @param character Character to associate with letter
     * @param score Score to associate with letter
     */
    Letter (char character, int score){
        this.score = score;
        this.character = character;
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
}

// TODO: decide if tile frequency is here, or in some kind of draw pile
// Leaning towards letting 'draw pile' take care of this