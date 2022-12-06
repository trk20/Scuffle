package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Record storing the tiles forming a word on the board.
 *
 * @author Alex
 * @version NOV-18
 */
public class BoardWord implements Cloneable {

    private List<BoardTile> tiles;

    public BoardWord(List<BoardTile> tiles) {
        this.tiles = tiles;
    }

    public List<BoardTile> getTiles() {
        return tiles;
    }

    /**
     * Creates and returns a string representation of the BoardWord.
     * If the tiles spell the word "cat", the string will be "CAT".
     *
     * @return the word contained in the Model.BoardWord record
     * @author Timothy Kennedy
     */


    @Override
    public String toString() {
        StringBuilder returnString = new StringBuilder();
        for (BoardTile tile : tiles) {
            returnString.append(tile.getLetter().toString());
        }
        return returnString.toString();
    }

    private void addTile(BoardTile t){
        tiles.add(t);
    }

    private void setTiles(List<BoardTile> tiles){
        this.tiles = tiles;
    }

    @Override
    public BoardWord clone() {
        try {
            BoardWord clone = (BoardWord) super.clone();
            clone.setTiles(new ArrayList<>());

            for (BoardTile t : tiles){
                clone.addTile(t);
            }

            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
