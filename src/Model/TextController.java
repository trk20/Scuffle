package Model;

import Model.Letter;
import Model.ScrabbleModel;

import java.util.*;
/**
 * Class that handles user console input
 * The functions follow a pattern of a verifier/prompter where the prompter will run in a loop until the verifier
 * validates the input
 *
 * @Author: Kieran Rourke
 * @Version OCT-23
 */
public class TextController {

    private Scanner inputHandler;

    public TextController(){
        inputHandler = new Scanner(System.in);
    }

    /**
     * Verifies that the action given is either d or p
     * @param action: the user input
     * @return: True/false depending on if it's valid
     */
    public boolean isValidAction(String action){
        if (action.length() != 1){
            System.out.println("Invalid action expected length 1 got length "+action.length());
            return false;
        }
        if (!action.equals("p") && !action.equals("d")){
           System.out.println("Expected value p or d received "+action);
           return false;
        }
        return true;
    }
    /**
     * Asks the user if they want to discard or place cards
     * @return: their answer
     */
    public boolean getUserAction(){
        String input = "";
        boolean isValidInput = false;

        while (!isValidInput){
            System.out.println("Please indicate if you would like to place letters or discard letters");
            System.out.println("To discard enter in d, To place enter in p");
            input = inputHandler.nextLine();
            isValidInput = isValidAction(input);
        }
        return input.equals("d") ? ScrabbleModel.DISCARD : ScrabbleModel.PLACE;
    }
    /**
     * Checks if a word is only letters
     * @param word: input
     * @return: True/False depending on if it's valid
     */
    private boolean isValidWord(String word){
        if (!word.chars().allMatch(Character::isLetter)){
            System.out.println("Invalid Characters entered please only enter letters");
            return false;
        }
        return true;
    }

    /**
     * Asks a user for a word,
     *
     * @param message Prompting message *optional
     * @return A list of Letters corresponding to the input string.
     *
     * @author Kieran, Alexandre
     */
    public List<Letter> askForWord(String message){
        String word = "";
        boolean validWord = false;
        while (!validWord){
            System.out.println(Objects.requireNonNullElse(message, "Please Enter your word"));
            word = inputHandler.nextLine();
            validWord = isValidWord(word);
        }
        return Letter.wordToLetters(word);
    }

    /**
     * Checks if a coord is of the right length and that it follows the desired format
     * @param coords: input
     * @return: True/False depending on if it's valid
     */
    private Boolean isValidCoords(String coords){
        if (coords.length() != 2 && coords.length() != 3){
            System.out.println("Invalid input, Coords are too long must be length 2 found "+coords.length());
            return false;
        }
        if (coords.length() == 3){
            if (Character.isLetter(coords.charAt(0))){
                if (!Character.isDigit(coords.charAt(1)) || !Character.isDigit(coords.charAt(2))){
                    System.out.println("Invalid input, Coords are not one letter and a number");
                    return false;
                }
            }else{
                if (!Character.isDigit(coords.charAt(0)) || !Character.isDigit(coords.charAt(1))){
                    System.out.println("Invalid input, Coords are not one letter and a number");
                    return false;
                }
            }
        } else {
            if (!(Character.isDigit(coords.charAt(0)) && Character.isLetter(coords.charAt(1)) || Character.isDigit(coords.charAt(1)) && Character.isLetter(coords.charAt(0)))){
                System.out.println("Invalid input, Coords are not one letter and a number");
                return false;
            }
        }
        return true;
    }

    /**
     * Asks a user for coordinates
     * @return: the coords
     */
    public String askForCoords(){
        String coords = "";
        boolean validCoords = false;

        while (!validCoords){
            System.out.println("Please enter your coordinates. They must be in one of the following formats: 2d, d2");
            System.out.println("Model.Letter/Row first (d2): Horizontal, left to right placement");
            System.out.println("Number/Column first (2d): Vertical, top to bottom placement");
            coords = inputHandler.nextLine();
            validCoords = isValidCoords(coords);
        }
        return coords;

    }

    /**
     * Checks if a string is a number
     * @param num: input
     * @return: True/False depending on if it's valid
     */
    private boolean isValidNum(String num){
        if (num.length() != 1){
            System.out.println(String.format("Invalid input, %s is too long must be length 1 found "+num.length(), num));
            return false;
        }
        if (!Character.isDigit(num.charAt(0))){
            System.out.println(String.format("Invalid input, %s is not a number", num));
            return false;
        }
        return true;
    }

    /**
     * Asks a user for num players
     * @return: num players
     */
    public int askForNumPlayers(){
        String numPlayers = "";
        int playersInt = 0;
        boolean validNum = false;

        // Makes sure the given input is within the model's max players
        while (playersInt <= 0){
            System.out.println("How many players would like to play? Up to "
                    +ScrabbleModel.MAX_PLAYERS+" players can play");
            numPlayers = inputHandler.nextLine();
            // Checks if it's a single digit (0-9)
            validNum = isValidNum(numPlayers);

            // Check if the number is within the range
            if(validNum) { // Makes sure the parseInt doesn't crash the program
                playersInt = Integer.parseInt(numPlayers);
                if (playersInt > ScrabbleModel.MAX_PLAYERS) {
                    // Set back to 0 if the players are above the model's max players. Ask again.
                    System.out.println("Too many players want to play, max is 4. " +
                            "Asked for " + playersInt);
                    playersInt = 0;
                } else if (playersInt < ScrabbleModel.MIN_PLAYERS) {
                    System.out.println("At least "+ScrabbleModel.MIN_PLAYERS+" player(s) have to play! " +
                            "Asked for "+ playersInt);
                }
            }
        }

        return playersInt;
    }

    /**
     * Asks for player name
     * @param index: the index of the player list
     * @return: the name
     */
    public String askForPlayerName(int index){
        String word = "";
        boolean validWord = false;
        while (!validWord){
            System.out.println("Please enter the name of Model.Player "+ (index+1));
            word = inputHandler.nextLine();
            validWord = isValidWord(word);
        }
        return word;
    }
}
