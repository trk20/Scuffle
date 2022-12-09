package Model;


import java.util.ArrayList;

import java.io.Serializable;

import java.util.List;

/**
 * Record storing the tiles forming a word on the board.
 *
 * @author Alex
 * @version NOV-18
 */
public class BoardWord implements Cloneable, Serializable {

    private List<BoardTile> tiles;

    public BoardWord(List<BoardTile> tiles) {
        this.tiles = tiles;
    }

    public List<BoardTile> getTiles() {
        return tiles;
    }

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
