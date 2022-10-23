
import java.util.*;
public class TextController {
    private Scanner inputHandler;


    public TextController(){
        inputHandler = new Scanner(System.in);
    }

    private boolean isValidWord(String word){
        if (!word.chars().allMatch(Character::isLetter)){
            System.out.println("Invalid Characters entered please only enter letters");
            return false;
        }
        return true;
    }
    public String askForWord(){
        String word = "";
        boolean validWord = false;
        while (!validWord){
            System.out.println("Please Enter your word");
            word = inputHandler.nextLine();
            validWord = isValidWord(word);
        }
        return word;
    }

    private Boolean isValidCoords(String coords){
        if (coords.length() != 2){
            System.out.println("Invalid input, Coords are too long must be length 2 found "+coords.length());
            return false;
        }
        if (!(Character.isDigit(coords.charAt(0)) && Character.isLetter(coords.charAt(1)) || Character.isDigit(coords.charAt(1)) && Character.isLetter(coords.charAt(0)))){
            System.out.println("Invalid input, Coords are not one letter and a number");
            return false;
        }
        return true;
    }

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
    public int askForNumPlayers(){
        String numPlayers = "";
        boolean validNum = false;

        while (!validNum){
            System.out.println("How many players would like to play?");
            numPlayers = inputHandler.nextLine();
            validNum = isValidNum(numPlayers);
        }

        return Integer.parseInt(numPlayers);
    }


    public static void main(String[] args){
        TextController t = new TextController();
        t.askForWord();
        t.askForCoords();
    }
}
