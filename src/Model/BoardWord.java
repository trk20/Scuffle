package Model;

import java.util.List;

/**
 * Record storing the tiles forming a word on the board.
 *
 * @param tiles Tiles forming a word on the board
 * @author Alex
 * @version NOV-18
 */
public record BoardWord(List<BoardTile> tiles) {
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
}
