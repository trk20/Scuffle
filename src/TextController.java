
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
            System.out.println("For discard enter in d, For place enter in p");
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
     * Asks a user for a word
     * @param message: Prompting message *optional
     * @return: the word
     */
    public String askForWord(String message){
        String word = "";
        boolean validWord = false;
        while (!validWord){
            System.out.println(Objects.requireNonNullElse(message, "Please Enter your word"));
            word = inputHandler.nextLine();
            validWord = isValidWord(word);
        }
        return word;
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
            System.out.println("If the Row(Letter) is entered first the word will be placed horizontally and" +
                    " if the Column(number) is entered first the word will be placed vertically");
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
        boolean validNum = false;

        while (!validNum){
            System.out.println("How many players would like to play? Maximum 9");
            numPlayers = inputHandler.nextLine();
            validNum = isValidNum(numPlayers);
        }

        return Integer.parseInt(numPlayers);
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
            System.out.println("Please enter the name of Player "+ (index+1));
            word = inputHandler.nextLine();
            validWord = isValidWord(word);
        }
        return word;
    }


    public static void main(String[] args){
        TextController t = new TextController();
        t.askForCoords();
    }
}
