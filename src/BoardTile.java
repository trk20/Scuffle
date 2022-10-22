import java.util.*;


/**
 * A class handling the board tiles,
 *
 * @author Vladimir Kovacina
 * @version 1.0
 *
 * @author Timothy Kennedy
 * @version 1.1
 */


public class BoardTile {

    private char letter;

    public enum Type {START, BLANK, X2WORD, X3WORD, X2LETTER, X3LETTER}

    private Type tileType;

    //x and y unused, possibly removable
    private int x;
    private int y;

    /**
     * Constructor for creating a new BoardTile object
     * @param type, the type of the board object
     */
    public BoardTile(Type type, int x, int y){
        this.tileType = type;
        this.letter = ' ';
        this.x = x;
        this.y = y;
    }

    /**
     * Method used for setting the X position of the board Tile
     * @param x, the new x position of the tile
     */

    public void setX(int x){
        this.x = x;
    }

    /**
     * Method used for getting the X position of the board tile
     * @return x, returns the x position of the tile
     */
    public int getX() {
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
     * Method used for checking is a board tile is taken
     * @return true is the tile is taken (contains a letter), false otherwise
     */
    public boolean isTaken(){
        return letter != ' ';
    }

    /**
     * Method used for setting the letter (char) of the board tile
     * @param letter the letter the tile now has
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
    public Type getType(){
        return tileType;
    }

    /**
     * Method to set the type of the board tile
     * @param type, String that contains the board tile type (B,LM,WM)
     */
    public void setType(Type type){
        this.tileType = type;
    }

    @Override
    public String toString() {
        if(this.letter != ' '){
            return String.valueOf(letter);
        }
        return tileType.toString().replaceAll("BLANK"," -- ").replace("START", "S").replace("X2WORD", "2W").replace("X3WORD", "3W").replace("X2LETTER", "2L").replace("X3LETTER", "3L");
    }
}