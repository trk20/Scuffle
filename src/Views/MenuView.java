package Views;

import Controllers.MenuController;
import Model.ScrabbleModel;

import javax.swing.*;

public class MenuView extends JPanel {

    private ScrabbleModel model;

    private JPanel menuContainer;

    private JMenu menu;

    private JMenuBar menuBar;

    private JMenuItem newGame, saveGame, help;

    private MenuController mc;

    public MenuView(ScrabbleModel model){
        super();
        //Get the model
        this.model = model;
        mc = new MenuController(model);

        //Create container, menubar and menu
        menuContainer = new JPanel();
        menuBar = new JMenuBar();
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


        //add Menu to MenuBar
        menuBar.add(menu);

        //add menuBar to container (JPanel)
        menuContainer.add(menuBar);

        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        //Add JPanel to the view
        add(menuContainer);




    }

}
