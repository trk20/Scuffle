import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;

/**
 * A class handling the loading of the set of valid words, and the evaluation of the validity of possible words
 *
 * @author Timothy Kennedy
 * @version 1.1
 */
public class DictionaryHandler {
    private ArrayList<String> allWords; // Storage for all valid words

    /**
     * Constructs a new DictionaryHandler object with all valid words loaded into an ArrayList
     */
    DictionaryHandler() {
        allWords = new ArrayList<>();
        /*
         * Load all valid words from the 2019 version of Scrabble into an ArrayList
         * If something goes wrong, print an error message
         */
        try {
            System.out.println("preparing to load file");
            File dictionary = new File("Collins Scrabble Words (2019).txt");
            Scanner scanner = new Scanner(dictionary);
            while (scanner.hasNextLine()) {
                allWords.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("uh oh, something went wrong with loading the dictionary");

        }


    }

    /**
     * Evaluates the validity of a given string as a word as a boolean value
     *
     * @param maybeAWord the word whose validity is to be evaluated
     * @return whether the word is valid according to the 2019 Scrabble word list
     */
    public boolean isValidWord(String maybeAWord){
        return allWords.contains(maybeAWord.toUpperCase());
    }

    public static void main(String[] args) {
        /*
        DictionaryHandler dict = new DictionaryHandler();
        System.out.println(dict.isValidWord("maybe"));
        System.out.println(dict.isValidWord("no"));
        System.out.println(dict.isValidWord("explosive"));
        System.out.println(dict.isValidWord("indiscretion"));
        System.out.println(dict.isValidWord("expletive"));
        System.out.println(dict.isValidWord("notavalidword"));
        */
        DictionaryHandler dic = new DictionaryHandler();
        System.out.println(dic.isValidWord("re"));
        Board board = new Board(15,15);
        System.out.println(board);
        board.placeWord("revoting",5,7,false);
        System.out.println(board);
        //System.out.println(board.getNewWords("revoting",5,7,false));
        //board.getWordScore("revoting",5,7,false);
        board.boardScore("revoting",5,7,false);

        board.placeWord("joked", 8,6,true);
        System.out.println(board);
        board.boardScore("joked", 8,6,true);

        board.placeWord("yex",4,8,false);
        System.out.println(board);
        board.boardScore("yex",4,8,false);

        //BoardTile[][] b1 = board.getBoard();
       // System.out.println(" Is it taken: " + b1[5][7].isTaken()+ " The x coord:"+b1[5][7].getX() + " The y coord: "+ b1[5][7].getY() );


    }
}
