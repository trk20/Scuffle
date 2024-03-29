package Model;

import java.io.*;
import java.util.*;
import java.util.stream.IntStream;

/**
 * A class handling the loading of the set of valid words, and the evaluation of the validity of possible words
 *
 * @author Timothy Kennedy
 * @version NOV-29
 */
public class DictionaryHandler {
    private final HashSet<String> allWords; // Storage for all valid words

    private final ArrayList<HashMap<String,HashMap<String,Integer>>> sortedLetterCounts;


    /**
     * Constructs a new Model.DictionaryHandler object with all valid words loaded into an ArrayList, and a HashMap of their letter frequencies.
     */
    public DictionaryHandler() {
        allWords = new HashSet<>();
        sortedLetterCounts = new ArrayList<>();
        /*
         * Load all valid words from the 2019 version of Scrabble into an ArrayList
         * If something goes wrong, print an error message
         */
        try {
            File dictionary = new File("Collins Scrabble Words (2019).txt");
            Scanner scanner = new Scanner(dictionary);
            while (scanner.hasNextLine()) {
                String word = scanner.nextLine();
                allWords.add(word);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Something went wrong with loading the dictionary");
        }

        for(int i = 0; i < 15; i++){
            sortedLetterCounts.add(new HashMap<>());
        }

        //create Hashmap of words and their associated letter frequencies
        allWords.forEach(word->{
            HashMap<String, Integer> letterCount = new HashMap<>();
            IntStream distinct = word.chars().distinct();
            for (int letter : distinct.toArray()) {
                letterCount.put(Character.toString(letter), (int) word.chars().filter(e -> e == letter).count());
            }
            sortedLetterCounts.get(word.length()-1).put(word,letterCount);
        });

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

    /**
     * Find all dictionary words that match the letter frequencies passed to it, only looking at words with the given length
     *
     * @param letterCount the letter frequencies to consider
     * @return a list of all valid words with those letters
     */
    public ArrayList<ArrayList<String>> optimizedGetValidWords(HashMap<String,Integer> letterCount, int len){
        ArrayList<ArrayList<String>> words = new ArrayList<>();
        sortedLetterCounts.get(len-1).entrySet().parallelStream()
                .filter(entry -> letterCount.equals(entry.getValue()))
                .map(Map.Entry::getKey)
                .forEach(
                        word->words.add(new ArrayList<>(Arrays.stream(word.split("")).toList()))
                );
        return words;
    }
}
