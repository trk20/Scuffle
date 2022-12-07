package Controllers;

import Model.ScrabbleModel;
import ScrabbleEvents.ControllerEvents.C_LoadEvent;
import ScrabbleEvents.ControllerEvents.C_SaveEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;

/**
 * MenuController Class controls the logic behind what happens when one of the menu items are pressed
 *
 * @author Vladimir Kovacina
 * @version 22 Nov 2022
 */
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
            if(e.getActionCommand().equals("Load Game")){
                // Get file to load in
                JFileChooser fileChooser = new JFileChooser();
                // Set directory to "Saves" folder
                fileChooser.setCurrentDirectory(new File((System.getProperty("user.dir"))+File.separator+"Saves"));
                fileChooser.showOpenDialog(null);
                File chosenFile = fileChooser.getSelectedFile();

                model.handleControllerEvent(new C_LoadEvent(chosenFile));
            }

            if(e.getActionCommand().equals("Game Rules")){
                String url = "https://scrabble.hasbro.com/en-us/rules";
                openRules(url);
            }
            if(e.getActionCommand().equals("Save Game")){
                model.handleControllerEvent(new C_SaveEvent(new File("Saves"+File.separator+System.currentTimeMillis()+".sav")));
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

