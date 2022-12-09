package Views;

import Controllers.MenuController;
import Model.ScrabbleModel;

import javax.swing.*;

/**
 * The GUI of the Menu in the Scrabble game
 * @author Vladimir Kovacina
 * @version 22 Nov 2022
 */
public class MenuView extends JMenuBar {

    private JMenu menu;

    private JMenuItem loadGame, saveGame, help, undo, redo;

    private MenuController mc;

    public MenuView(ScrabbleModel model){
        super();
        //Get the model
        mc = new MenuController(model);
        menu = new JMenu("Options");

        //Create Menu Items
        loadGame = new JMenuItem("Load Game");
        saveGame = new JMenuItem("Save Game");
        help = new JMenuItem("Game Rules");
        undo = new JMenuItem("Undo");
        redo = new JMenuItem("Redo");

        //Set action listeners
        loadGame.addActionListener(mc);
        saveGame.addActionListener(mc);
        help.addActionListener(mc);
        undo.addActionListener(mc);
        redo.addActionListener(mc);

        //Add items to Menu
        menu.add(loadGame);
        menu.add(saveGame);
        menu.add(help);
        menu.add(undo);
        menu.add(redo);

        add(menu);
    }

}
