package Views;

import Controllers.MenuController;
import Model.ScrabbleModel;

import javax.swing.*;

public class MenuView extends JMenuBar {

    private ScrabbleModel model;

    private JMenu menu;

    private JMenuItem newGame, saveGame, help;

    private MenuController mc;

    public MenuView(ScrabbleModel model){
        super();
        //Get the model
        this.model = model;
        mc = new MenuController(model);
        menu = new JMenu("Options");

        //Create Menu Items
        newGame = new JMenuItem("New Game");
        saveGame = new JMenuItem("Save Game");
        help = new JMenuItem("Game Rules");

        newGame.addActionListener(mc);
        saveGame.addActionListener(mc);
        help.addActionListener(mc);

        //Add items to Menu
        menu.add(newGame);
        menu.add(saveGame);
        menu.add(help);

        add(menu);
    }

}
