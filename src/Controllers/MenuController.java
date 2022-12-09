package Controllers;

import Model.ScrabbleModel;
import ScrabbleEvents.ControllerEvents.C_LoadEvent;
import ScrabbleEvents.ControllerEvents.C_SaveEvent;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.Listeners.SControllerListener;
import ScrabbleEvents.ModelEvents.ME_ModelChangeEvent;
import ScrabbleEvents.ModelEvents.ModelEvent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * MenuController Class controls the logic behind what happens when one of the menu items are pressed
 *
 * @author Vladimir Kovacina
 * @version 22 Nov 2022
 */
public class MenuController implements ActionListener, ModelListener, SController {
    private final List<SControllerListener> listeners;
    public MenuController(ScrabbleModel model){
        listeners = new ArrayList<>();
        model.addModelListener(this);
        this.addControllerListener(model);
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
            if(e.getActionCommand().equals("Load Game")){
                notifyControllerListeners(new C_LoadEvent(chooseSaveFile()));
            }

            if(e.getActionCommand().equals("Game Rules")){
                String url = "https://scrabble.hasbro.com/en-us/rules";
                openRules(url);
            }
            if(e.getActionCommand().equals("Save Game")){
//                File saveFile = new File("Saves"+File.separator+System.currentTimeMillis()+".sav");
                notifyControllerListeners(new C_SaveEvent(chooseSaveFile()));
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

    private File chooseSaveFile(){
        // Get file to load in
        JFileChooser fileChooser = new JFileChooser();
        // Set directory to "Saves" folder
        fileChooser.setCurrentDirectory(new File((System.getProperty("user.dir"))+File.separator+"Saves"));
        fileChooser.showOpenDialog(null);
        return fileChooser.getSelectedFile();
    }

    @Override
    public void handleModelEvent(ModelEvent e) {
        if(e instanceof ME_ModelChangeEvent mce){
            listeners.clear(); // Remove old model reference
            addControllerListener(mce.newModel()); // Control new model
        }
    }

    /**
     * Add a listener to notify when an event is raised.
     *
     * @param l the listener to add to this SController.
     */
    @Override
    public void addControllerListener(SControllerListener l) {
        listeners.add(l);
    }

    /**
     * Notify listeners by sending them a controller event.
     *
     * @param e the event to send to the listeners
     */
    @Override
    public void notifyControllerListeners(ControllerEvent e) {
        for (SControllerListener l: listeners) {
            l.handleControllerEvent(e);
        }
    }
}

