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

    private ArrayList<String> playedWords;

    public Board(int length, int width){
        board = new BoardTile[length][width];
        dictionary = new DictionaryHandler();
        playedWords = new ArrayList<>();
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
    public boolean wordPlacementOk(String word, int row, int column, boolean direction){
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
     * @param word the word the user placed on the board
     * @param row the row of the board to the word starts on
     * @param column the column of the board the word starts on
     * @param direction whether the word is placed top-to-bottom
     * @return the score of the word placement
     */
    public int boardScore(String word, int row, int column,boolean direction){
        int totalScore = 0;
        totalScore += this.getWordScore(word,row,column,direction);
        System.out.println("Score without additional words: "+ totalScore);
        totalScore += this.getAdditionalScores(word,row,column,direction);
        System.out.println("Total Score: "+ totalScore);
        return totalScore;
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
        //add word to list of played words
        playedWords.add(word.toUpperCase());
        System.out.println("Played Words So far: "+ playedWords);
        return true;
    }

    /**
     * Calculates the score for any additional words created when the user placed his word
     * @param word The initial word the user placed
     * @param row The row the word begins in
     * @param column The column the word begins in
     * @param direction The direction of the word: true is horizontal, false is vertical
     * @return returns the combined score of all the additional words
     */
    public int getAdditionalScores(String word, int row, int column, boolean direction){
        int score = 0;
        ArrayList<String> newWords = new ArrayList<>();
        for (int i = 0; i< word.length(); i++){
            BoardTile current = board[row+((!direction) ? i : 0)][column+((direction) ? i : 0)];
            BoardTile start= current;
            BoardTile end = current;
            //X is row, Y is col
            int j = 0;
            //if words is Vertical
            if(!direction){
                //Check left /find start

                while(board[current.getX()][current.getY() - j].isTaken()){
                    start = board[current.getX()][current.getY() - j];
                    j ++;
                }

                current = board[row+((!direction) ? i : 0)][column+((direction) ? i : 0)];
                j = 0;
                //Check right /find end
                while(board[current.getX()][current.getY() + j].isTaken()){
                    end = board[current.getX()][current.getY() + j];
                    j ++;
                }

            // word is Horizontal
            }else{
                while(board[current.getX() -j][current.getY()].isTaken()){
                    start = board[current.getX() -j][current.getY()];
                    // decrease row
                    j ++;
                }
                current = board[row+((!direction) ? i : 0)][column+((direction) ? i : 0)];
                j = 0;

                while(board[current.getX() +j][current.getY()].isTaken()){
                    end = board[current.getX() +j][current.getY()];
                    // increase row
                    j ++;
                }
            }
            //new word will be opposite direction from current word
            String newWord = createWord(board,start,end,!direction );
            if(dictionary.isValidWord(newWord) && !playedWords.contains(newWord)){
                score +=getWordScore(newWord, start.getX(), start.getY(), !direction);
                newWords.add(newWord);
            }
        }
        System.out.println("New Words: "+ newWords);
        playedWords.addAll(newWords);
        return score;
    }


    /**
     * Creates a word, given the start and end tile and the direction of the word
     *
     * @author: Vladimir Kovacina
     *
     * @param aBoard the board on which the tiles are on
     * @param start the start tile of the word, tile where the word begins
     * @param end the end tile of the word, tile where the word ends
     * @param direction the diredtion of the word
     * @return returns the new created word
     */
    public String createWord(BoardTile aBoard[][], BoardTile start, BoardTile end, boolean direction){
        String createdWord = "";
        //System.out.println("Start:"+ start.getLetter().getCharLetter() + " End:" + end.getLetter().getCharLetter());
        int length = 0;
        if (direction){
            length = end.getY() - start.getY() + 1;

        }
        else{
            length = end.getX() - start.getX() + 1;

        }

        for ( int i = 0; i < length ;i++ ){
            //Horizontal
            if (direction){
               createdWord += aBoard[start.getX()][start.getY() + i].getLetter().getCharLetter();
            //Vertical Word
            }else{
                createdWord += aBoard[start.getX() + i][start.getY()].getLetter().getCharLetter();
            }
        }
        //System.out.println("Created Word: " + createdWord);
        return createdWord;
    }

    /**
     * Calculates the score of a single word, based off the value of the letters and tile multipliers
     *
     * @author Vladimir Kovacina
     *
     * @param word The word to calculate the score of
     * @param row The row the word begins in
     * @param column The column the word begins in
     * @param direction The direction of the word
     * @return int The score of the word
     */
    public int getWordScore(String word,int row, int column, boolean direction){

        ArrayList<Letter> wordLetters = Letter.wordToLetters(word);
        int score = 0;
        if (!wordPlacementOk(word,row,column,direction)){
            return score;
        }
        boolean x3word = false;
        boolean x2word = false;

        for (int i = 0; i < wordLetters.size(); i++) {
            if(board[row+((!direction) ? i : 0)][column+((direction) ? i : 0)].getType() == BoardTile.Type.X3WORD){
                x3word = true;
            } else if (board[row+((!direction) ? i : 0)][column+((direction) ? i : 0)].getType() == BoardTile.Type.X2LETTER) {
                score += 2 * wordLetters.get(i).getScore();
            } else if (board[row+((!direction) ? i : 0)][column+((direction) ? i : 0)].getType() == BoardTile.Type.X3LETTER) {
                score += 3 * wordLetters.get(i).getScore();
            } else if (board[row+((!direction) ? i : 0)][column+((direction) ? i : 0)].getType() == BoardTile.Type.X2WORD) {
                x2word = true;
            }else{
                //Blank or Start tile
                score += wordLetters.get(i).getScore();
            }
        }
        if (x2word){
            score *= 2;
        }
        if (x3word){
            score *=3;
        }

        System.out.println("Player Score:"+ score);
        return score;

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
        for(int j  = 0; j < column.length ; j ++){
            returnString += " ".repeat(4) + column[j];
        }
        returnString += "\n\n";
        for(BoardTile[] row:board){
            returnString += i + "" ;
            for(BoardTile tile:row){
                returnString += " ".repeat(5-tile.toString().length()) + tile;
            }
            i ++;
            returnString+="\n\n";
        }
        return returnString;
    }
}
