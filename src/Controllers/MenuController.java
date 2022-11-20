package Controllers;

import Model.ScrabbleModel;
import Views.ScrabbleFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MenuController implements ActionListener {
    private ScrabbleModel model;

    public MenuController(ScrabbleModel model){
        this.model = model;
    }

    /**
     * Used to handle the actions of the Menu based off of which event is selected
     * The possible actions are:
     *
     * New game
     * Show the Game Rules
     * Save Game
     *
     * @author Vladimir Kovacina
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();

            if(e.getActionCommand().equals("New Game")){//FIXME: Not finished yet
                model.newGame(); // TODO: could be an event, on controller listeners in the future

            }
            if(e.getActionCommand().equals("Game Rules")){
                String url = "https://scrabble.hasbro.com/en-us/rules";
                openRules(url);
            }
            if(e.getActionCommand().equals("Save Game")){ //FIXME: Not finished yet

            }
    }

    /**
     * Opens the url to the website provided
     * @author Vladimir Kovacina
     * @param urlString the url to the website
     */

    private void openRules(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

