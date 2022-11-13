package Views;

import Controllers.BoardController;
import Events.BoardPlaceEvent;
import Events.Listeners.ModelListener;
import Events.ModelEvent;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardView extends JPanel implements ModelListener {

    private JPanel boardPanel;
    private JButton[][] gridButtons;
    private int boardSize;
    private List<BoardController> controllers;
    private ScrabbleModel model;
    public BoardView(ScrabbleModel model) {
        boardSize = 15;
        this.model = model;
        model.addModelListener(this);
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        add(Box.createHorizontalGlue());
        setSize(800,500);
        setAlignmentX(RIGHT_ALIGNMENT);
        boardPanel = new JPanel();
        Font buttonFont = new Font("Consolas",Font.PLAIN,10);
        add(boardPanel);
        add(Box.createHorizontalGlue());
        boardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        boardPanel.setLayout(new GridLayout(boardSize,boardSize));
        boardPanel.setMaximumSize(new Dimension(boardSize*30,boardSize*30));

        controllers = new ArrayList<>();

        gridButtons = new JButton[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                controllers.add(new BoardController(model,new Point(row, col)));
                gridButtons[row][col] = new JButton();
                gridButtons[row][col].addActionListener(controllers.get(controllers.size()-1));
                gridButtons[row][col].setText(model.getBoardTileText(row,col));
                gridButtons[row][col].setMaximumSize(new Dimension(30,30));
                boardPanel.add(gridButtons[row][col]);
            }
        }
        setVisible(true);
    }
    public void update(){
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                gridButtons[row][col].setText(model.getBoardTileText(row,col));
            }
        }
    }
    @Override
    public void handleModelEvent(ModelEvent e) {
        if(e instanceof BoardPlaceEvent) update();
    }

    /**
     * Return all the board controllers (1/tile in the board)
     * @return all the board controllers in the board view.
     */
    public List<BoardController> getControllers() {
        return this.controllers;
    }
}
