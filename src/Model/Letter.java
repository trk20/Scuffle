package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Enumeration class Model.Letter, contains constants for each Scrabble letter.
 * Assigns a letter score (int), and a character (String) for each Model.Letter.
 *
 * @author Alexandre Marques - 101189743
 * @version 2022-10-19
 */
public enum Letter {
    // Model.Letter enums, sorted by order of frequency, in tiles/game
    E('E',12),
    A('A',9),
    I('I',9),
    O('O',8),
    N('N',6),
    R('R',6),
    T('T',6),
    L('L',4),
    S('S',4),
    U('U',4),
    D('D',4),
    G('G',3),
    B('B',2),
    C('C',2),
    M('M',2),
    P('P',2),
    F('F',2),
    H('H',2),
    W('W',2),
    Y('Y',2),
    V('V',2),
    K('K',1),
    J('J',1),
    X('X',1),
    Q('Q', 1),
    Z('Z', 1),
    BLANK(' ',2);

    // Fields

    /** Character associated with letter (used for printing) */
    private final char character; // char to enforce only one character

    /** Frequency of appearance, how many copies are in a fresh draw pile*/
    private final int frequency;
    /**
     * Model.Letter Constructor, initializes fields with given parameters.
     *
     * @param character Character to associate with letter
     * @param freq      Frequency to associate with letter
     * @author Alexandre Marques - 101189743
     */
    Letter (char character, int freq){
        this.character = character;
        this.frequency = freq;
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
     * Convert a word string to a list of Letters.
     * Assumes all characters are valid.
     *
     * @author Vladimir Kovacina
     * @param word a word to be seperated into individual Model.Letter enums
     * @return An ArrayList of letters corresponding to the word's characters.
     * @author Alexandre Marques - 101189743
     */
    @Deprecated
    public static ArrayList<Letter> wordToLetters(String word){
        ArrayList<Letter> letters = new ArrayList<>();
        //Go through the letters of the word
        for (int i =0; i<word.length(); i++){
            //Look for the corresponding Model.Letter
            for(Letter l: Letter.values()){
                if(l.character == word.toUpperCase().charAt(i)){
                    //Add the corresponding Model.Letter to the returned ArrayList
                    letters.add(l);
                }
            }
        }
        return letters;
    }

    /**
     * Convert a list of Letters to a word string.
     * Assumes all characters are valid.
     *
     * @author Alexandre Marques
     * @param word A List of letters corresponding to the word's characters.
     * @return A string corresponding to the word's letters.
     * @author Alexandre Marques - 101189743
     */
    @Deprecated
    public static String lettersToString(List<Letter> word){
        StringBuilder str = new StringBuilder();
        for (Letter l: word) {
            str.append(l.toString());
        }
        return str.toString();
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
