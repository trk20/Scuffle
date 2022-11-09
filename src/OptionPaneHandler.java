
import javax.swing.*;

/**
 * Class that handles user console input
 * The functions follow a pattern of a verifier/prompter where the prompter will run in a loop until the verifier
 * validates the input
 *
 * @Author: Kieran Rourke
 * @Version OCT-23
 */
public class OptionPaneHandler {

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

        // Makes sure the given input is within the model's max players
        while (!validNum){
            numPlayers = JOptionPane.showInputDialog("How many players are playing? Maximum "+ScrabbleModel.MAX_PLAYERS);

            // Checks if it's a single digit (0-9)
            validNum = isValidNum(numPlayers);
            if (!validNum){
                JOptionPane.showMessageDialog(null, "Invalid Input! Please enter a valid number",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }else{
                if (Integer.parseInt(String.valueOf(numPlayers.charAt(0))) >= ScrabbleModel.MAX_PLAYERS){
                    JOptionPane.showMessageDialog(null, "Invalid Input! Please enter a number below "+ScrabbleModel.MAX_PLAYERS,
                            "Invalid Input", JOptionPane.ERROR_MESSAGE);
                    validNum = false;
                }
            }

        }
        return Integer.parseInt(String.valueOf(numPlayers.charAt(0)));
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
            word = JOptionPane.showInputDialog("What is Player "+ (index+1) + "'s name");
            validWord = isValidWord(word);
            if(!validWord){
                JOptionPane.showMessageDialog(null, "Invalid Input! Please enter only valid characters",
                        "Invalid Input", JOptionPane.ERROR_MESSAGE);
            }
        }
        return word;
    }
}
