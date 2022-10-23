import java.util.*;

/**
 * Class that controls/models the overall scrabble game
 *
 * @author: Kieran Rourke
 * @version OCT-23
 */
public class ScrabbleModel {
    private Board board;
    private TextController inputHandler;
    private ArrayList<Player> players;
    private DictionaryHandler wordDictionary;
    private int numPlayers;
    private final int SIZE = 15;
    private int turn = 0;
    /** Model's shared DrawPile */
    private final DrawPile drawPile;

    public ScrabbleModel() {
        this.board = new Board(SIZE, SIZE);
        this.inputHandler = new TextController();
        this.wordDictionary = new DictionaryHandler();
        this.drawPile = new DrawPile();
    }

    /**
     * Increments turn
     * @param turn: current value
     * @return new value
     */
    private int incrementTurn(int turn){
        return turn == numPlayers ? 0 : ++turn;
    }

    /**
     * Prints Board
     */
    private void printBoard(){
        System.out.print(board);
    }

    /**
     * Following Methods handle parsing the user coord input
     * @param coords: String inputted by the user. Will be in the form 2f or f2.
     * @return: The integer/boolean value from the input
     */
    private int getXCoord(String coords){
        return Character.isLetter(coords.charAt(0)) ? Integer.parseInt(String.valueOf(coords.charAt(1))) : Integer.parseInt(String.valueOf(coords.charAt(0)));
    }

    private int getYCoord(String coords){
        return Character.isLetter(coords.charAt(0)) ? (int) Character.toUpperCase(coords.charAt(0)) - 65: (int) Character.toUpperCase(coords.charAt(1)) - 65;
    }

    private boolean getDirection(String coords){
        return Character.isLetter(coords.charAt(1));
    }

    /**
     * The following functions validate that a given user input is valid.
     * This does not check general format mistakes as that is handled in the text controller
     * @param word/coords: Raw string user input
     * @return: True/false depending on if the input is valid
     */
    private boolean validateWord(String word){
        if(!wordDictionary.isValidWord(word)){
            System.out.println("Invalid Word not found in dictionnary");
            return false;
        }
        return true;
    }

    private boolean validateCoords(String coords, String word){
        int x = getXCoord(coords);
        int y = getYCoord(coords);
        boolean direction = getDirection(coords);

        if(!board.wordPlacementOk(word, x, y, direction)){
            System.out.println("Coords are not valid based on current board arrangement");
            return false;
        }
        return true;
    }

    /**
     * Handles starting the game
     */
    public void startGame(){
        numPlayers = inputHandler.askForNumPlayers();
        boolean running = true;
        printBoard();
        while(running){
            nextTurn();
        }

    }

    /**
     * Handles running a turn, will be called in a loop until the game is over
     */
    public void nextTurn(){
        String word = "";
        String coords = "";
        boolean isValidInput = false;
        int x, y;
        boolean direction;

        turn = incrementTurn(turn);
        System.out.printf("It is Player %d turn", turn);
        while(!isValidInput){
            word = inputHandler.askForWord();
            isValidInput = validateWord(word);
        }
        isValidInput = false;

        while(!isValidInput){
            coords = inputHandler.askForCoords();
            isValidInput = validateCoords(coords, word);
        }

        x = getXCoord(coords);
        y = getYCoord(coords);
        direction = getDirection(coords);

        board.placeWord(word, x, y, direction);
        printBoard();
        //TODO calculate score add it to player



    }
    public static void main(String[] args){
        ScrabbleModel s = new ScrabbleModel();
        s.startGame();
    }

    /**
     * Getter for model drawPile.
     * @author Alexandre marques - 101189743
     * @return model's shared DrawPile
     */
    public DrawPile getDrawPile() {
        return drawPile;
    }
}
