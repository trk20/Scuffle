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

    private final String START = "S";
    private String type;

    private String x;

    private int y;

    /**
     * Constructor for creating a new BoardTile object
     * @param type, the type of the board object
     */
    public BoardTile(String type, String x, int y){
        taken = false;
        if(type.equals(WORD_MULTI)){
            this.type = WORD_MULTI;
        }else if (type.equals(LETTER_MULTI)){
            this.type = LETTER_MULTI;
        }else if (type.equals(BLANK)){
            this.type = BLANK;
        }else{
            this.type = START;
        }
        this.x = x;
        this.y = y;
    }

    /**
     * Method used for setting the X position of the board Tile
     * @param x, the new x position of the tile
     */

    public void setX(String x){
        this.x = x;
    }

    /**
     * Method used for getting the X position of the board tile
     * @return x, returns the x postion of the tile
     */
    public String getX() {
        return x;
    }

    /**
     * Method used for setting the Y position of the board tile
     * @param y, the y position of the tile
     */
    public void setY(int y){
        this.y =y;
    }

    /**
     * Method used for getting to Y position of the board tile
     * @return y, returns the y position of the tile
     */
    public int getY() {
        return y;
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

    /**
     * Method to set the type of the board tile
     * @param type, String that contains the board tile type (B,LM,WM)
     */
    public void setType(String type){
        if (type.equals(BLANK) || type.equals(WORD_MULTI) || type.equals(LETTER_MULTI) || type.equals(START)){
            this.type = type;
        }
    }

}
