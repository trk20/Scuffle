import java.util.*;

/**
 * Class that controls/models the overall scrabble game.
 * For Milestone 1, also acts as a text "view".
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
    public static final Boolean DISCARD = false;
    public static final Boolean PLACE = true;
    /** Model's shared DrawPile */
    private final DrawPile drawPile;
    /** Max players limited by the four racks (see README setup rules) */
    public static final int MAX_PLAYERS = 4;
    /** Min players, should be 2 but 1 could work if we want to allow solo play */
    public static final int MIN_PLAYERS = 1;

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
        return turn == numPlayers ? 1 : ++turn;
    }

    /**
     * Prints Board
     */
    private void printBoard(){
        System.out.print(board);
    }

    private void initializePlayers(){
        String name = "";


        numPlayers = inputHandler.askForNumPlayers();
        players = new ArrayList<>(numPlayers);

        for (int i = 0; i < numPlayers; i++){
            name = inputHandler.askForPlayerName(i);
            players.add(i, new Player(name, this));
        }
    }

    /**
     * Following Methods handle parsing the user coord input
     * @param coords String inputted by the user. Will be in the form 2f or f2.
     * @return The integer/boolean value from the input
     */
    private int getXCoord(String coords){
        if(coords.length() == 3){
             return Character.isLetter(coords.charAt(0)) ? Integer.parseInt(String.valueOf(coords.substring(1, 2))) : Integer.parseInt(String.valueOf(coords.substring(0, 1)));
        }
        return Character.isLetter(coords.charAt(0)) ? Integer.parseInt(String.valueOf(coords.charAt(1))) : Integer.parseInt(String.valueOf(coords.charAt(0)));
    }

    private int getYCoord(String coords){
        if (coords.length() == 3){
             return Character.isLetter(coords.charAt(0)) ? (int) Character.toUpperCase(coords.charAt(0)) - 65: (int) Character.toUpperCase(coords.charAt(2)) - 65;
        }
        return Character.isLetter(coords.charAt(0)) ? (int) Character.toUpperCase(coords.charAt(0)) - 65: (int) Character.toUpperCase(coords.charAt(1)) - 65;
    }

    private boolean getDirection(String coords){
        return Character.isLetter(coords.charAt(0));
    }

    /**
     * The following functions validate that a given user input is valid.
     * This does not check general format mistakes as that is handled in the text controller
     * @param word/coords Raw string user input
     * @return: True/false depending on if the input is valid
     */
    private boolean validateWord(String word, Player p){
        if(!p.canPlaceWord(word)){
            System.out.printf("Invalid Word: %s can not be assembled based on your current letters\n", word);
            return false;
        }
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
     * Gets the user action either place or discard
     * @return boolean mapped to the action
     */
    public boolean getAction(){
        return inputHandler.getUserAction();
    }

    /**
     * Handles the user wanting to discard letters
     * @param currentPlayer The player whose turn it is
     */
    public void handleDiscard(Player currentPlayer){
        boolean validInput = false;
        String word = "";

        while(!validInput){
            word = inputHandler.askForWord("What letters would you like to discard? Write them as one word");
            validInput = currentPlayer.canPlaceWord(word);
            if (!validInput){
               System.out.println("You do not contain these letters please try again");
            }
        }
        currentPlayer.discardLetters(Letter.wordToLetters(word));

    }
    /**
     * Handles the user wanting to place letters
     * @param currentPlayer The player whose turn it is
     */
    public void handlePlace(Player currentPlayer){
        String word = "";
        String coords = "";
        boolean isValidInput = false;
        int x, y;
        boolean direction;

        while(!isValidInput){
            word = inputHandler.askForWord(null);
            isValidInput = validateWord(word, currentPlayer);
        }
        isValidInput = false;

        while(!isValidInput){
            coords = inputHandler.askForCoords();
            isValidInput = validateCoords(coords, word);
            currentPlayer.placeLetters(Letter.wordToLetters(word));
        }

        x = getXCoord(coords);
        y = getYCoord(coords);
        direction = getDirection(coords);

        board.placeWord(word, x, y, direction);
        printBoard();
        System.out.println("Your new " + currentPlayer.getHand());
        //TODO calculate score add it to player
    }

    /**
     * Handles starting the game
     */
    public void startGame(){
        initializePlayers();
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
        boolean action;
        Player currentPlayer;

        turn = incrementTurn(turn);
        currentPlayer = players.get(turn-1);
        System.out.println("==========================");
        System.out.printf("It is Player %d/%s's turn\n", turn, currentPlayer);
        System.out.println("Your current hand is \n\t" + currentPlayer.getHand());
        action = getAction();

        if(action == PLACE){
            handlePlace(currentPlayer);
        }else{
            handleDiscard(currentPlayer);
        }
        System.out.println("Your new hand is \n\t" + currentPlayer.getHand());
    }

    /**
     * Getter for model drawPile.
     * @author Alexandre marques - 101189743
     * @return model's shared DrawPile
     */
    public DrawPile getDrawPile() {
        return drawPile;
    }
    public static void main(String[] args){
        ScrabbleModel s = new ScrabbleModel();
        s.startGame();
        System.out.println(Letter.S);
    }
}
