import java.util.*;


/**
 * A class handling the board tiles,
 *
 * @author Vladimir Kovacina
 * @version 1.0
 */


public class BoardTile {

    private char letter;
    private boolean taken;
    private final String BLANK = "B";
    private final String LETTER_MULTI = "LM";
    private final String WORD_MULTI = "WM";
    private String type;

    /**
     * Constructor for creating a new BoardTile object
     * @param type, the type of the board object
     */
    public BoardTile(String type){
        taken = false;
        if(type.equals(WORD_MULTI)){
            this.type = WORD_MULTI;
        }else if (type.equals(LETTER_MULTI)){
            this.type = LETTER_MULTI;
        }else{
            this.type = BLANK;
        }
    }

    /**
     * Sets the board tile to taken (used when letter is placed)
     */
    public void setTaken(){
        //Should add some error checking to check if letter is a char
        taken = true;
    }

    /**
     * Method used for checking is a board tile is taken
     * @return true is the tile is taken (contains a letter), false otherwise
     */
    public boolean isTaken(){
        return taken;
    }

    /**
     * Method used for setting the letter (char) of the board tile
     * @param letter
     */
    public void setLetter(char letter){
        this.letter = letter;
    }

    /**
     *Method used for getting the letter (char) of the board tile
     * @return letter, the character that the tile contains
     */
    public char getLetter(){
        return letter;
        //might need error checking here
    }
    /**
     * Method used to get the type of tile
     * @return return the type of tile, String
     */
    public String getType(){
        return type;
    }

}
