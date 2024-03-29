package Model;

import java.awt.*;
import java.io.Serializable;

/**
 * A class handling the board tiles.
 * Stores information on the board tile's type (score multiplier, score, neither),
 * location, and letter (if it is taken).
 *
 * @author Vladimir Kovacina
 * @author Timothy Kennedy
 * @author Alex
 * @version NOV-19
 */
public class BoardTile implements Serializable {

    public enum Type {
        START(new Color(233, 187, 171), "★"),
        BLANK(new Color(207, 197, 161), ""),
        X2WORD(new Color(232, 177, 156), "2W"),
        X3WORD(new Color(232, 113, 115), "3W"),
        X2LETTER(new Color(171, 207, 205), "2L"),
        X3LETTER(new Color(76, 169, 191), "3L");
        final private Color c;
        final private String s;
        Type(Color c, String s){
            this.c = c;
            this.s = s;
        }
        public Color getColor(){return c;}

        @Override
        public String toString() {
            return s;
        }
    }
    private Letter letter;

    private Type tileType;
    private final int x;
    private final int y;

    /**
     * Constructor for creating a new Model.BoardTile object
     *
     * @param type, the type of the board object
     */
    public BoardTile(Type type, int x, int y) {
        this.tileType = type;
        this.x = x;
        this.y = y;
    }

    /**
     * Constructor for creating a new BoardTile object,
     *  Default type is BLANK
     * @param x The x location of the tile on the board
     * @param y The y location of the tile on the board
     */
    public BoardTile(int x, int y) {
        this(Type.BLANK, x, y);
    }

    /**
     * Constructor for creating a new BoardTile object,
     *  Default type is BLANK
     * @param p The Point location of the tile on the board
     */
    public BoardTile(Point p) {
        this(p.x, p.y);
    }

    /**
     * Constructor creating a deep copy of the passed BoardTile
     * @param boardTile the BoardTile to copy
     */
    public BoardTile(BoardTile boardTile) {
        this(boardTile.tileType, boardTile.x, boardTile.y);
        this.letter = boardTile.letter;
    }


    /**
     * Method used for checking is a board tile is taken
     *
     * @return true is the tile is taken (contains a letter), false otherwise
     */
    public boolean isTaken() {
        return letter != null ;
    }

    /**
     * Method used for setting the letter (char) of the board tile
     *
     * @param letter the letter the tile now has
     */
    public void setLetter(Letter letter){
        if(!isTaken()) {
            this.letter = letter;
        }
    }

    /**
     * Method used for getting the letter (char) of the board tile
     *
     * @return letter, the character that the tile contains
     */
    public Letter getLetter(){
        return letter;
    }

    /**
     * Method used to get the type of tile
     *
     * @return return the type of tile, String
     */
    public Type getType() {
        return tileType;
    }

    /**
     * Method to set the type of the board tile
     *
     * @param type, String that contains the board tile type (B,LM,WM)
     */
    public void setType(Type type) {
        this.tileType = type;
    }

    /**
     * Gets the tile's x location (AI ONLY)
     *
     * @return tile's x location
     */
    public int getX() {
        return x;
    }

    /**
     * Gets the tile's y location (AI ONLY)
     *
     * @return tile's y location
     */
    public int getY() {
        return y;
    }

    /**
     * Creates a copy of this Model.BoardTile object
     *
     * @return a copy of this Model.BoardTile
     */
    public BoardTile copy(){
        BoardTile copy = new BoardTile(tileType,x,y);
        copy.setLetter(this.letter);
        return copy;
    }

    @Override
    public String toString() {
        if(this.isTaken()){
            return String.valueOf(letter);
        }
        return tileType.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardTile boardTile)) return false;

        return letter == boardTile.letter &&
                tileType == boardTile.tileType &&
                x == boardTile.x &&
                y == boardTile.y;
    }

}
