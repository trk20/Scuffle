package Views;

import Controllers.BoardController;
import Events.BoardChangeEvent;
import Events.Listeners.BoardChangeListener;
import Events.Listeners.ModelListener;
import Model.Board;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardView extends JPanel implements ModelListener, BoardChangeListener {

    private JPanel boardPanel;
    private JButton[][] gridButtons;
    private int boardSize;
    private List<BoardController> controllers;

    public BoardView(ScrabbleModel model) {
        boardSize = 15;
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
        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize; row++) {
                controllers.add(new BoardController(model,new Point(col, row)));
                gridButtons[col][row] = new JButton();
                gridButtons[col][row].addActionListener(controllers.get(controllers.size()-1));
                gridButtons[col][row].setText(model.getBoardTileText(new Point(col, row)));
                gridButtons[col][row].setMaximumSize(new Dimension(30,30));
                boardPanel.add(gridButtons[col][row]);
            }
        }
        setVisible(true);
    }

    /**
     * Update the board view to represent the given board
     * @param board A modeled board
     */
    public void update(Board board){
//        System.out.println("Updating");
        for (int col = 0; col < boardSize; col++) {
            for (int row = 0; row < boardSize; row++) {
                gridButtons[col][row].setText(board.getBoardTile(new Point(col, row))
                        .toString());
            }
        }
        validate();
        repaint();
    }

    /**
     * Handles board change events, updates view to show new board state
     * @param e The event indicating a board change
     */
    @Override
    public void handleBoardChangeEvent(BoardChangeEvent e) {
        update(e.getBoard());
    }

    /**
     * Return all the board controllers (1/tile in the board)
     * @return all the board controllers in the board view.
     */
    public List<BoardController> getControllers() {
        return this.controllers;
    }
}
