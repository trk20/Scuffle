import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * Class that handles the Board for the Scrabble game
 *
 * @author : Vladimir Kovacina
 * @version : 1.0
 *
 * @author Timothy Kennedy
 * @version 1.1
 */
public class Board {
    private BoardTile[][] board;
    private String[] column = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};

    private DictionaryHandler dictionary;

    public Board(int length, int width){
        board = new BoardTile[length][width];
        dictionary = new DictionaryHandler();
        Random r = new Random();
        for (int row = 0; row< length; row ++){
            for(int col = 0; col<width; col ++){
                int randomInt = r.nextInt(14);
                if(randomInt == 1){
                    //set multiplier tiles at random
                    board[row][col] = new BoardTile(BoardTile.Type.values()[2+r.nextInt(4)], row, col);

                } else {
                    //set all other tiles to blank
                    board[row][col] = new BoardTile(BoardTile.Type.BLANK, row, col);
                }
            }
        }
        //Initialize the start tile:
        board[(int)length/2][(int)width/2].setType(BoardTile.Type.START);

    }

    /**
     * Checks whether a tile is connected to the start tile
     *
     * @param column the column of the tile
     * @param row the row of the tile
     * @return whether the tile is connected to the start
     */
    private boolean connectedToStart(int row,int column){
        //checks to see if adjacent tiles are taken (which would mean they are connected to the start) or if the tile checked is the starting tile
        return board[row][column].getType() == BoardTile.Type.START || column!=15 && board[row][column+1].isTaken() || column!=0 && board[row][column-1].isTaken() || row!=15 && board[row+1][column].isTaken() || row!=0 && board[row-1][column].isTaken();
    }

    /**
     * Checks whether a specified word is allowed to be placed in a certain spot
     * Ignores the creation of invalid words due to placement location
     *
     * @param word the word to be checked
     * @param row the row the word would start at
     * @param column the column the word would start at
     * @param direction whether the word is left-to-right
     * @return whether the word's placement is valid
     */
    private boolean wordPlacementOk(String word, int row, int column, boolean direction){
        if(!dictionary.isValidWord(word)){
            return false;
        }

        //flag to track if any of the letters are connected to the start tile
        boolean connectedToStart = false;

        if(row+((!direction) ? word.length() : 0) >= 15 || column+((direction) ? word.length() : 0) >= 15){
            //if the word would run off the board
            return false;
        }
        for(int i = 0; i < word.length(); i++){
            BoardTile currentTile = board[row+((!direction) ? i : 0)][column+((direction) ? i : 0)];
            if(currentTile.isTaken() && currentTile.getLetter().getChar() != word.toUpperCase().charAt(i)){
                //if a letter of the word would overwrite a previous word's letter
                return false;
            }
            if(this.connectedToStart(row+((!direction) ? i : 0),column+((direction) ? i : 0))){
                //if any letters in the word are connected to the start, the word is connected to the start
                connectedToStart = true;
            }
        }

        return connectedToStart;

    }

    /**
     * calculates the score given by a placed word
     *
     * @param length the length of the word
     * @param row the row of the board to the word starts on
     * @param column the column of the board the word starts on
     * @param direction whether the word is placed top-to-bottom
     * @return the score of the word placement
     */
    private int boardScore(int row, int column, int length,boolean direction){
        // todo: calculate score
        return 0;
    }

    /**
     * Places a word on the board
     *
     * @param word the word to be placed
     * @param row the row of the board to start the word on
     * @param column the column of the board to start the word on
     * @param direction whether the word is left-to-right
     * @return true is word was placed successfully, false otherwise
     */
    public boolean placeWord(String word, int row, int column, boolean direction){
        ArrayList<Letter> wordLetters = Letter.wordToLetters(word);

        //check if word placement is ok
        if(!wordPlacementOk(word,row,column,direction)){
            System.out.println("Word placement invalid");
            return false;
        }

        for(int index = 0; index < word.length(); index++){
            //place letters on the board
            board[row+((!direction) ? index : 0)][column+((direction) ? index : 0)].setLetter(wordLetters.get(index));
        }
        return true;
    }

    public int getWordScore(String word,int row, int column, boolean direction){

        ArrayList<Letter> wordLetters = Letter.wordToLetters(word);
        int score = 0;
        if (!wordPlacementOk(word,row,column,direction)){
            return score;
        }
        boolean x3word = false;
        boolean x2word = false;
        //if direction is horizontal
        if(direction) {
            for (int i = 0; i < wordLetters.size(); i++) {
                if(board[row][column + i].getType() == BoardTile.Type.BLANK){
                    score += wordLetters.get(i).getScore();
                } else if (board[row][column + i].getType() == BoardTile.Type.X2LETTER) {
                    score += 2 * wordLetters.get(i).getScore();
                } else if (board[row][column + i].getType() == BoardTile.Type.X3LETTER) {
                    score += 3 * wordLetters.get(i).getScore();
                } else if (board[row][column + i].getType() == BoardTile.Type.X2WORD) {
                    x2word = true;
                }else{
                    x3word = true;
                }
            }
            if (x2word){
                score *= 2;
            }
            if (x3word){
                score +=3;
            }
        }else{
            for (int i = 0; i < wordLetters.size(); i++) {
                if(board[row + i][column].getType() == BoardTile.Type.BLANK){
                    score += wordLetters.get(i).getScore();
                } else if (board[row + i][column].getType() == BoardTile.Type.X2LETTER) {
                    score += 2 * wordLetters.get(i).getScore();
                } else if (board[row + i][column].getType() == BoardTile.Type.X3LETTER) {
                    score += 3 * wordLetters.get(i).getScore();
                } else if (board[row + i][column].getType() == BoardTile.Type.X2WORD) {
                    x2word = true;
                }else{
                    x3word = true;
                }
            }
            if (x2word){
                score *= 2;
            }
            if (x3word){
                score +=3;
            }
        }
        System.out.println("Player Score:"+ score);
        return score;

    }

    @Override
    public String toString() {
        String returnString = "";
        for(BoardTile[] row:board){
            for(BoardTile tile:row){
                returnString += " ".repeat(5-tile.toString().length()) + tile;
            }
            returnString+="\n\n";
        }
        return returnString;
    }
}
