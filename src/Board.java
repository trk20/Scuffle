import java.util.ArrayList;
import java.util.Arrays;

/**
 * Class that handles the Board for the Scrabble game
 *
 * @author : Vladimir Kovacina
 * @version : 1.0
 *
 */
public class Board {
    private BoardTile[][] board;
    private ArrayList<String> column = new ArrayList<>(Arrays.asList("A","B","C","D","E","F","G","H","I","J"));
    private ArrayList<Integer> row = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9,10));

    public Board(int length, int width){
        board = new BoardTile[length][width];
        for (int i = 0; i< length; i ++){
            for(int j = 0; j<width; j ++){
                board[i][j].setX(column.get(i));
                board[i][j].setY(row.get(j));
            }
        }

    }

}
