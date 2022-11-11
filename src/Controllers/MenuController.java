package Controllers;

import Model.ScrabbleModel;
import Views.ScrabbleFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

public class MenuController extends JPanel implements ActionListener {
    private ScrabbleModel model;

    public MenuController(ScrabbleModel model){
        super();
        this.model = model;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
            String s = e.getActionCommand();

            if(e.getActionCommand().equals("New Game")){//FIXME: Not finished yet
                model.setGameFinished(true);

            }
            if(e.getActionCommand().equals("Game Rules")){
                String url = "https://scrabble.hasbro.com/en-us/rules";
                openRules(url);
            }
            if(e.getActionCommand().equals("Save Game")){ //FIXME: Not finished yet

            }
    }

    public static void openRules(String urlString) {
        try {
            Desktop.getDesktop().browse(new URL(urlString).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

