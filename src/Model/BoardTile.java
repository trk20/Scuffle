package Model;

/**
 * A class handling the board tiles.
 * Stores information on the board tile's type (score multiplier, score, neither),
 * location, and letter (if it is taken).
 *
 * @author Vladimir Kovacina
 * @author Timothy Kennedy
 * @author Alex
 * @version NOV-12
 */
public class BoardTile {

    private Letter letter;

    public enum Type {START, BLANK, X2WORD, X3WORD, X2LETTER, X3LETTER}

    private Type tileType;
    private int x;
    private int y;

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
     * Method used for setting the X position of the board Tile
     *
     * @param x, the new x position of the tile
     */

    public void setX(int x) {
        this.x = x;
    }

    /**
     * Method used for getting the X position of the board tile
     *
     * @return x, returns the x position of the tile
     */
    public int getX() {
        return x;
    }

    /**
     * Method used for setting the Y position of the board tile
     *
     * @param y, the y position of the tile
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Method used for getting to Y position of the board tile
     *
     * @return y, returns the y position of the tile
     */
    public int getY() {
        return y;
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
        //might need error checking here
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
        return tileType.toString().replaceAll("BLANK"," -- ").replace("START", "ST").replace("X2WORD", "2W").replace("X3WORD", "3W").replace("X2LETTER", "2L").replace("X3LETTER", "3L");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardTile boardTile = (BoardTile) o;
        return letter == boardTile.letter && tileType == boardTile.tileType;
    }

}
