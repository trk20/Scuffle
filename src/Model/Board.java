package Model;

import Events.BoardPlaceEvent;

import java.awt.*;
import java.util.*;
import java.util.List;

import static Model.ScrabbleModel.BOARD_SIZE;

/**
 * Class that handles the Model.Board for the Scrabble game
 *
 * @author Vladimir Kovacina
 * @author Timothy Kennedy
 * @author Alex
 * @version NOV-12
 */
public class Board {
    public enum Direction{DOWN, RIGHT;}
    private BoardTile[][] board;
    private final String[] columnLabels = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O"};
    private ArrayList<PlacedWord> currentWords;
    /** Takes care of validating the board */
    private BoardValidator validator;
    private ArrayList<String> playedWords;

    /**
     * Constructor for a Model.Board object
     * @author Vladimir Kovacina
     * @author Timothy Kennedy
     */
    public Board(boolean randomBoard){
        board = new BoardTile[BOARD_SIZE][BOARD_SIZE];
        playedWords = new ArrayList<>();
        validator = new BoardValidator(this);
        currentWords = new ArrayList<>();

        // Set tile types to blank tiles
        for (int row = 0; row< BOARD_SIZE; row ++){
            for(int col = 0; col<BOARD_SIZE; col ++){
                board[row][col] = new BoardTile(row, col);
            }
        }

        if(randomBoard) randomiseBoard();
        //Initialize the start tile:
        board[BOARD_SIZE/2][BOARD_SIZE/2].setType(BoardTile.Type.START);

    }

    /**
     * Places a word on the board
     * @author Timothy Kennedy
     * @author Alex
     *
     * @param placeEvent The event containing the placement details
     * @return -1 if it is an invalid placement, otherwise, the score resulting from the placement.
     */
    public int placeWord(BoardPlaceEvent placeEvent){
        // Ensure valid placement
        if(!validator.isValidPlacement(placeEvent)) return -1;

        // Unpack relevant event info
        Point wordOrigin = placeEvent.getWordOrigin();
        Direction placementDirection = placeEvent.getDirection();
        List<Tile> word = placeEvent.getPlacedTiles();
        int overlaps = 0; // Place tile one further if a tile already occupies its spot

        // Place tiles in the board, skipping tiles that are already placed.
        for(int i = 0; i < word.size(); i++) {
            boolean overlapping = true; // (until proven otherwise)
            Point placementLocation = new Point();
            if (placementDirection == Board.Direction.RIGHT) {
                // Increment Col (x), until no overlap
                while (overlapping) {
                    placementLocation.setLocation((wordOrigin.x + (i + overlaps)), wordOrigin.y);
                    if (isTaken(placementLocation)) overlaps += 1;
                    else overlapping = false;
                }
            } else {
                // Decrement row (y), until no overlap
                while (overlapping) {
                    placementLocation.setLocation(wordOrigin.x, wordOrigin.y + (i + overlaps));
                    if (isTaken(placementLocation)) overlaps += 1;
                    else overlapping = false;
                }
            }
            placeTile(placementLocation, word.get(i).getLetter());
        }

        currentWords = new ArrayList<>();
        currentWords.addAll(allBoardWords(placeEvent));

        // Return score after palcing
        return getTurnScore(placeEvent);
    }

    /**
     * Place a tile in the board.
     * Precondition: The tile is verified to be empty beforehand
     * @param p The point where the tile should be placed
     * @param letter the letter to place at the board location.
     */
    private void placeTile(Point p, Letter letter) {
        board[p.x][p.y].setLetter(letter);
    }

    /**
     * Sets bonus tiles randomly throughout the board.
     */
    private void randomiseBoard(){
        Random r = new Random();
        for (int row = 0; row< BOARD_SIZE; row ++){
            for(int col = 0; col<BOARD_SIZE; col ++){
                int randomInt = r.nextInt(14);
                if(randomInt == 1){
                    //Set multiplier tiles at random
                    board[row][col].setType(BoardTile.Type.values()[2+r.nextInt(4)]);
                }
            }
        }
    }

    /**
     * Checks if a coordinate in the board is taken (x = col, y = row), starting top left.
     * @param p A point coordinate in the board to check
     * @return True if a tile is placed at that location, false otherwise.
     */
    boolean isTaken(Point p) {
        return board[p.x][p.y].isTaken();
    }

    /**
     * Checks if no words have been placed on the board yet.
     * @return True if the board is empty, false otherwise.
     */
    // Package private: only accessible by model classes (for the validator)
    boolean boardIsEmpty(){
        return currentWords.size() == 0;
    }

    /**
     * Checks if the given board tile is the board's start tile
     *
     * @param p the coordinates of the tile to check for the "start" type
     * @return True if the given board tile is the board's start tile, false otherwise.
     */
    boolean isStartTile(Point p) {
        return board[p.x][p.y].getType() == BoardTile.Type.START;
    }

    /**
     * Creates a word starting from given coordinates
     * @author Timothy Kennedy
     *
     * @param aBoard the state of the board to check
     * @param row the row to start from
     * @param column the column to start from
     * @param direction whether the direction of the word is right-to-left
     * @return the word starting at the given coordinates
     */
    private PlacedWord getWordStartingFrom(BoardTile[][] aBoard, int row, int column, boolean direction){
        //initialize a string with the first character of the word
        ArrayList<BoardTile> wordTiles = new ArrayList<>();
        wordTiles.add(aBoard[row][column]);
        int index = 1;

        //going in the direction of the word, add each character to the word
        if(direction){
            while (row+index < 15 && aBoard[row+index][column].isTaken()){
                wordTiles.add(aBoard[row+index][column]);
                index+=1;
            }
        }else{
            while (column+index < 15 && aBoard[row][column+index].isTaken()){
                wordTiles.add(aBoard[row][column+index]);
                index+=1;
            }
        }

        return new PlacedWord(wordTiles);
    }

    /**
     * Finds all words after a word placement
     * @author Timothy Kennedy
     *
     * @param placeEvent The event containing the placement details
     * @return the list of words originating from the word placement
     */
    private List<PlacedWord> allBoardWords(BoardPlaceEvent placeEvent){
        // FIXME: Big function, cohesion could be improved (using helper methods)
        Direction direction = placeEvent.getDirection();
        List<Tile> word = placeEvent.getPlacedTiles();
        int row = placeEvent.getWordOrigin().y;
        int col = placeEvent.getWordOrigin().x;

        //make a copy of the board
        BoardTile[][] boardCopy = new BoardTile[board.length][board[0].length];
        for(int i = 0; i < board.length; i++){
            for (int j = 0; j < board[0].length; j++){
                boardCopy[i][j] = board[i][j].copy();
            }
        }

        Set<BoardTile> allTiles = new HashSet<>();
        ArrayList<PlacedWord> words = new ArrayList<>();

        for(int index = 0; index < word.size(); index++){ //place word on the copy of the board
            boardCopy[row+(direction==Direction.DOWN ? index : 0)]
                    [col+(direction==Direction.RIGHT ? index : 0)]
                    .setLetter(word.get(index).getLetter());
        }

        for(BoardTile[] currentRow:boardCopy){ //get all taken tiles
            for(BoardTile tile:currentRow){
                if(tile.isTaken()){
                    allTiles.add(tile);
                }
            }
        }

        for (BoardTile tile:allTiles){
            //find words
            if(tile.isTaken()) {
                //if tile is the start of a word, IE: no letters before it, and 1+ letters after it, add the word starting from that position to the list of words
                if ((tile.getX() > 0 && !boardCopy[tile.getX() - 1][tile.getY()].isTaken()
                        || tile.getX() == 0) && tile.getX() < 14 && boardCopy[tile.getX() + 1][tile.getY()].isTaken()) {
                    words.add(getWordStartingFrom(boardCopy, tile.getX(), tile.getY(), true));
                }
                if ((tile.getY() > 0 && !boardCopy[tile.getX()][tile.getY() - 1].isTaken()
                        || tile.getY() == 0) && tile.getY() < 14 && boardCopy[tile.getX()][tile.getY() + 1].isTaken()) {
                    words.add(getWordStartingFrom(boardCopy, tile.getX(), tile.getY(), false));
                }
            }
        }
        return words;
    }

    /**
     * Temporary boolean to enum conversion until the board is fully refactored
     * to stop using boolean directions.
     *
     * @param direction boolean direction
     * @return enum representation of the direction
     */
    @Deprecated
    public static Direction boolDirToEnum(boolean direction) {
        return direction ? Direction.RIGHT : Direction.DOWN;
    }

    /**
     * @deprecated
     * Calculates the score for any additional words created when the user placed his word
     * @param word The initial word the user placed
     * @param row The row the word begins in
     * @param column The column the word begins in
     * @param direction The direction of the word: true is horizontal, false is vertical
     * @return returns the combined score of all the additional words
     */
    @Deprecated
    private int getAdditionalScores(List<Letter> word, int row, int column, boolean direction){
        int score = 0;
        ArrayList<String> newWords = new ArrayList<>();
        for (int i = 0; i< word.size(); i++){
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
            String newWord = createWord(board,start,end,!direction);
//            if(dictionary.isValidWord(newWord) && !playedWords.contains(newWord)){
//                score +=getWordScore(Letter.wordToLetters(newWord), start.getX(), start.getY(), !direction);
//                newWords.add(newWord);
//            }
        }
        playedWords.addAll(newWords);
        return score;
    }


    /**
     * @deprecated
     * Creates a word, given the start and end tile and the direction of the word
     *
     * @author Vladimir Kovacina
     *
     * @param aBoard the board on which the tiles are on
     * @param start the start tile of the word, tile where the word begins
     * @param end the end tile of the word, tile where the word ends
     * @param direction the direction of the word
     * @return returns the new created word
     */
    @Deprecated
    private String createWord(BoardTile aBoard[][], BoardTile start, BoardTile end, boolean direction){
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
               createdWord += aBoard[start.getX()][start.getY() + i].getLetter();
            //Vertical Word
            }else{
                createdWord += aBoard[start.getX() + i][start.getY()].getLetter();
            }
        }
        //System.out.println("Created Word: " + createdWord);
        return createdWord;
    }

    /**
     * Calculates the score of a given word placement, based off the value of the letters and tile multipliers
     *
     * @author Vladimir Kovacina
     * @author Timothy Kennedy
     * @author Alex
     *
     * @return int The score of the word
     */
    private int getTurnScore(BoardPlaceEvent placeEvent){

        int turnScore = 0;
//        if (!validPlacement(word,row,column,direction)){
//            return turnScore;
//        }

        for (PlacedWord newWord:getNewWords(placeEvent)) {
            int wordScore = 0;
            int multiplier = 1;

            for (BoardTile tile:newWord.getTiles()) {
                    switch (tile.getType()){
                        case X2LETTER:
                            wordScore+=tile.getLetter().getScore()*2;
                            break;
                        case X3LETTER:
                            wordScore+=tile.getLetter().getScore()*3;
                            break;
                        case X2WORD: case START:
                            wordScore+=tile.getLetter().getScore();
                            multiplier*=2;
                            break;
                        case X3WORD:
                            wordScore+=tile.getLetter().getScore();
                            multiplier*=3;
                            break;
                        default:
                            wordScore+=tile.getLetter().getScore();
                            break;
                    }
            }
            turnScore += wordScore*multiplier;
        }

        // Set placed tiles to blank type (to disable bonus types on subsequent turns)
        Direction dir = placeEvent.getDirection();
        int row = placeEvent.getWordOrigin().y;
        int col = placeEvent.getWordOrigin().x;
        for(int index = 0; index < placeEvent.getPlacedTiles().size(); index++){
            board[row+(dir == Direction.DOWN ? index : 0)]
                    [col+(dir == Direction.RIGHT ? index : 0)]
                    .setType(BoardTile.Type.BLANK);
        }

        return turnScore;
    }

    /**
     * Gets a list of all new words created by a word placement
     *
     * @param placementEvent Event containing relevant information on placement
     * @return a list of all new words
     */
    List<PlacedWord> getNewWords(BoardPlaceEvent placementEvent){
        // All words in the board - words that were in the board last turn.
        List<PlacedWord> newWords = new ArrayList<>(allBoardWords(placementEvent));
        for (PlacedWord aWord:currentWords) {
            if(newWords.contains(aWord)){
                newWords.remove(aWord);
            }
        }
        return newWords;
    }

    public BoardTile getBoardTile(int row,int col){
        return board[row][col];
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
        for(int j  = 0; j < columnLabels.length ; j ++){
            returnString += " ".repeat(4) + columnLabels[j];
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
