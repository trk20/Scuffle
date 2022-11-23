package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class handling the loading of the set of valid words, and the evaluation of the validity of possible words
 *
 * @author Timothy Kennedy
 * @version 1.1
 */
public class DictionaryHandler {
    private final ArrayList<String> allWords; // Storage for all valid words

    /**
     * Constructs a new Model.DictionaryHandler object with all valid words loaded into an ArrayList
     */
    public DictionaryHandler() {
        allWords = new ArrayList<>();
        /*
         * Load all valid words from the 2019 version of Scrabble into an ArrayList
         * If something goes wrong, print an error message
         */
        try {
            File dictionary = new File("Collins Scrabble Words (2019).txt");
            Scanner scanner = new Scanner(dictionary);
            while (scanner.hasNextLine()) {
                allWords.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong with loading the dictionary");

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
}
