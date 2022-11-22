package Views;

import Model.BoardValidator;
import Model.Letter;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
    @Deprecated
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
        if (!Character.isDigit(num.charAt(0))) {
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
                displayError("Invalid Input! Please enter a valid number");
            }else{
                if (Integer.parseInt(String.valueOf(numPlayers.charAt(0))) > ScrabbleModel.MAX_PLAYERS){
                    displayError("Invalid Input! Please enter a number below "+ScrabbleModel.MAX_PLAYERS);
                    validNum = false;
                }else if (Integer.parseInt(String.valueOf(numPlayers.charAt(0))) == 0){
                    displayError("Invalid Input! Please enter a number above 0");
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
    public Object[] askForPlayerInfo(int index){
        String word = "";
        boolean validWord = false;
        boolean isAi = false;
        while (!validWord){
            JCheckBox aiCheckbox = new JCheckBox("AI player?");
            String message = "What is Player "+ (index+1) + "'s name";
            Object[] params = {message,aiCheckbox};
            word = JOptionPane.showInputDialog(params);
            validWord = isValidWord(word);
            isAi = aiCheckbox.isSelected();
            if(!validWord){
                displayError("Invalid Input! Please enter only valid characters");
            }
            if(isAi)
                word += " - (AI)";
        }
        return new Object[]{word,isAi};
    }

    /**
     * Asks for a name for each player that would like to play in a new game.
     * Amount is limited by the model's player limits.
     *
     * @return List of player names for a new game.
     *
     * TODO: make player info HashMap or similar
     */
    public HashMap<String, Boolean> getNewPlayers() {

        int numPlayers = this.askForNumPlayers();
        HashMap<String,Boolean> players = new HashMap<>();

        for (int i = 0; i < numPlayers; i++){
            Object[] playerInfo = this.askForPlayerInfo(i);
            players.put((String)playerInfo[0],(boolean)playerInfo[1]);
        }
        return players;
    }

    public void displayError(String message){
        JOptionPane.showMessageDialog(null, message, "Invalid Input", JOptionPane.ERROR_MESSAGE);
    }

    public void displayMessage(String message){
        JOptionPane.showMessageDialog(null, message, "Message", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Asks user for a character when the user wants to place a blank tile.
     * The blank tile will then contain that character
     * @author Vladimir Kovacina
     * @return Letter chosen by the user
     */
    public Letter getChosenLetter(){
        List<Enum> letters = new ArrayList<Enum>(EnumSet.allOf(Letter.class));
        letters.remove(Letter.BLANK);
        Object[] choices = letters.toArray();


        Letter choice = (Letter) JOptionPane.showInputDialog(null,"Which Letter Do You Want..",
              "Blank Tile Choice", JOptionPane.QUESTION_MESSAGE,null,choices,choices[0]);
        return choice;
    }
}
