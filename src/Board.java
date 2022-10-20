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
    private String[] column = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
    private int[] row = {1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};

    public Board(int length, int width){
        board = new BoardTile[width][length];
        for (int i = 0; i< length; i ++){
            for(int j = 0; j<width; j ++){
                board[i][j] = new BoardTile("B", column[i],row[j]);
            }
        }
        //Add code to initialize the special tiles on the board here

    }



}
