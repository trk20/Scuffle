package Views;

import Controllers.BoardController;
import ScrabbleEvents.ModelEvents.BoardChangeEvent;
import ScrabbleEvents.Listeners.BoardChangeListener;
import ScrabbleEvents.Listeners.ModelListener;
import Model.Board;
import Model.Grid2DArray;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Model.ScrabbleModel.BOARD_SIZE;
import static Views.DebugView.DEBUG_VIEW;

public class BoardView extends JPanel implements ModelListener, BoardChangeListener {
    private final Grid2DArray<JButton> gridButtons;
    private final List<BoardController> controllers;

    public BoardView(ScrabbleModel model) {
        model.addModelListener(this);
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        add(Box.createHorizontalGlue());
        setSize(800,500);
        setAlignmentX(RIGHT_ALIGNMENT);
        JPanel boardPanel = new JPanel();
        add(boardPanel);
        add(Box.createHorizontalGlue());
        boardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        boardPanel.setLayout(new GridLayout(BOARD_SIZE,BOARD_SIZE));
        boardPanel.setMaximumSize(new Dimension(BOARD_SIZE*30,BOARD_SIZE*30));

        controllers = new ArrayList<>();

        gridButtons = new Grid2DArray<>(BOARD_SIZE);
        // Build row by row, because that's how grid layout adds
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Point p = new Point(x,y);

                gridButtons.set(p, getNewGridButton(model, p));
                boardPanel.add(gridButtons.get(p));
            }
        }
        setVisible(true);
    }

    /**
     * Initialize and return a new button for the gridButtons.
     * This includes making a new controller for it, and styling it appropriately.
     * @return A new button to formatted for the board's button grid.
     */
    private JButton getNewGridButton(ScrabbleModel model,Point p) {
        controllers.add(new BoardController(p));
        if(DEBUG_VIEW) {
            model.addDebugController(controllers.get(controllers.size() - 1));
        }
        // Initialize button
        JButton button = new JButton();
        button.addActionListener(controllers.get(controllers.size()-1));
//        button.addActionListener(e -> System.out.println("Pressed"));
        button.setText(model.getBoardTileText(p)); // Might be able to do// this through an update instead
        button.setMaximumSize(new Dimension(30,30));
        return button;
    }

    /**
     * Update the board view to represent the given board
     * @param board A modeled board
     */
    public void update(Board board){
//        System.out.println("Updating");
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Point p = new Point(x,y);

                gridButtons.get(p).setText(board.getBoardTile(p).toString());
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
        update(e.board());
    }

    /**
     * Return all the board controllers (1/tile in the board)
     * @return all the board controllers in the board view.
     */
    public List<BoardController> getControllers() {
        return this.controllers;
    }
}
