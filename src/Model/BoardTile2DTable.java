package Model;

import java.awt.*;
import java.util.Random;

import static Model.ScrabbleModel.BOARD_SIZE;

/**
 * Handles accesses to a 2D table of BoardTiles.
 * Allows to pass Point objects for table location,
 * (0,0) is the top left tile of the board, negative values are outside the board.
 *
 * @author Alex
 * @version NOV-12
 */

/**
 * Use Grid2DArray instead, more generalized.
 * Some of this code should be merged back into the board class now, or board views
 */
@Deprecated
public class BoardTile2DTable {
    // Indexing convention: X, Y
    BoardTile[][] tile2DArray;
    /** Board view responsibility */
    private final String[] columnLabels = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};

    /**
     * BoardTile2DTable constructor, initializes tile2DArray with default tiles (blank).
     */
    BoardTile2DTable(){
        tile2DArray = new BoardTile[BOARD_SIZE][BOARD_SIZE];
        // Fill tile array
        for (int col = 0; col< BOARD_SIZE; col ++){
            for(int row = 0; row<BOARD_SIZE; row ++){
                tile2DArray[col][row] = new BoardTile(new Point(col, row));
            }
        }
    }

    /**
     * Sets bonus tiles randomly throughout the board.
     */
    public void randomiseTable(){
        Random r = new Random();
        for (int col = 0; col< BOARD_SIZE; col ++){
            for(int row = 0; row<BOARD_SIZE; row ++){
                int randomInt = r.nextInt(14);
                if(randomInt == 1){
                    //Set multiplier tiles at random
                    tile2DArray[col][row].setType(BoardTile.Type.values()[2+r.nextInt(4)]);
                }
            }
        }
    }

    /**
     * Set a letter in the table.
     * Precondition: The tile is verified to be empty beforehand
     *
     * @param p The point where the tile should be placed
     * @param letter the letter to place at the board location.
     */
    public void setLetter(Point p, Letter letter) {
        tile2DArray[p.x][p.y].setLetter(letter);
    }

    /**
     * Get a letter in the table (at the given location).
     * Precondition: The tile is verified to be empty beforehand
     *
     * @param p The point where the tile should be placed
     * @return the letter at the given location
     */
    public BoardTile getTile(Point p) {
        return tile2DArray[p.x][p.y];
    }


    /**
     * Set a type in the table (at the given location).
     *
     * @param p The point where the tile should be placed
     * @param type the type to set at the board location.
     */
    public void setType(Point p, BoardTile.Type type) {
        tile2DArray[p.x][p.y].setType(type);
    }

    /**
     * Checks if a coordinate in the table is taken (x = col, y = row), starting top left.
     * @param p A point coordinate in the board to check
     * @return True if a tile is placed at that location, false otherwise.
     */
    public boolean isTaken(Point p) {

        return tile2DArray[p.x][p.y].isTaken();
    }

    /**
     * Get a type from the table (at the given location).
     *
     * @param p The point where the tile should be placed
     * @return the type at the board location.
     */
    public BoardTile.Type getType(Point p) {
        return tile2DArray[p.x][p.y].getType();
    }

    /**
     * Return a deep copy of this board (not a reference!)
     * @return a deep copy of this 2D table
     */
    public BoardTile2DTable copy() {
        BoardTile2DTable copiedBoard = new BoardTile2DTable();
        for (int col = 0; col< BOARD_SIZE; col ++){
            for(int row = 0; row<BOARD_SIZE; row ++){
                Point currentPoint = new Point(col, row);
                // Create a new copy of the tile at the current point, copying its information over
                copiedBoard.setTile(currentPoint, new BoardTile(currentPoint));
                copiedBoard.setLetter(currentPoint, this.getTile(currentPoint).getLetter());
                copiedBoard.setType(currentPoint, this.getType(currentPoint));
            }
        }
        return copiedBoard;
    }

    /**
     * Set a tile at the given point. Mostly used during construction.
     *
     * @param p A point coordinate in the table
     * @param boardTile the tile to set at p
     */
    private void setTile(Point p, BoardTile boardTile) {
        this.tile2DArray[p.x][p.y] = boardTile;
    }

    /**
     * Creates and returns a string representation of the board
     * @author Timothy Kennedy and Vladimir Kovacina
     *
     * @return a string representation of the board
     */
    @Override
    public String toString() {
        String returnString = "";
        int i = 0;
        for (int j = 0; j < columnLabels.length; j++) {
            returnString += " ".repeat(4) + columnLabels[j];
        }
        returnString += "\n\n";
        for (BoardTile[] row : tile2DArray) {
            returnString += i + "";
            for (BoardTile tile : row) {
                returnString += " ".repeat(5 - tile.toString().length()) + tile;
            }
            i++;
            returnString += "\n\n";
        }
        return returnString;
    }
}
