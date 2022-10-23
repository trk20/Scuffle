import java.util.*;

public class ScrabbleModel {
    private Board board;
    private TextController inputHandler;
    private ArrayList<Player> players;
    private DictionaryHandler wordDictionary;
    private int numPlayers;
    private final int SIZE = 15;
    private int turn = 0;

    public ScrabbleModel() {
        this.board = new Board(SIZE, SIZE);
        this.inputHandler = new TextController();
        this.wordDictionary = new DictionaryHandler();
    }

    private int incrementTurn(int turn){
        return turn == numPlayers ? 0 : ++turn;
    }

    private int getXCoord(String coords){
        return Character.isLetter(coords.charAt(0)) ? Integer.parseInt(String.valueOf(coords.charAt(1))) : Integer.parseInt(String.valueOf(coords.charAt(0)));
    }

    private int getYCoord(String coords){
        return Character.isLetter(coords.charAt(0)) ? (int) Character.toUpperCase(coords.charAt(0)) - 65: (int) Character.toUpperCase(coords.charAt(1)) - 65;
    }

    private boolean getDirection(String coords){
        return Character.isLetter(coords.charAt(1));
    }


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

    public void startGame(){
        numPlayers = inputHandler.askForNumPlayers();
        boolean running = true;
        while(running){
            nextTurn();
        }

    }
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

        System.out.println(board.placeWord(word, x, y, direction));
        System.out.print(board);
        //TODO calculate score add it to player



    }
    public static void main(String[] args){
        ScrabbleModel s = new ScrabbleModel();
        s.startGame();
    }
}
