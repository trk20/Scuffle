package Views;

import Controllers.BoardTileController;
import Model.Grid2DArray;
import Model.ScrabbleModel;
import ScrabbleEvents.Listeners.BoardChangeListener;
import ScrabbleEvents.ModelEvents.BoardChangeEvent;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static Model.ScrabbleModel.BOARD_SIZE;

/**
 * View to display the board and tiles played on it.
 * Updates on BoardChangeEvents.
 *
 * @author Alex, Timothy
 * @version NOV-22
 */
public class BoardView extends JPanel implements BoardChangeListener {
    private final Grid2DArray<BoardTileView> boardTileViewGrid;

    /**
     * Constructor for a BoardView
     * Initialises the grid of tiles and adds itself as a model listener
     * @param model the model from which to base the board's state
     */
    public BoardView(ScrabbleModel model) {
        boardTileViewGrid = new Grid2DArray<>(BOARD_SIZE);
        model.addModelListener(this);

        setLayout(new GridLayout(BOARD_SIZE,BOARD_SIZE));
        setSize(800,500);

        // Build row by row, because that's how grid layout adds
        for (int y = 0; y < BOARD_SIZE; y++) {
            for (int x = 0; x < BOARD_SIZE; x++) {
                Point p = new Point(x, y);

                boardTileViewGrid.set(p, new BoardTileView(model, p));
                this.add(boardTileViewGrid.get(p));
            }
        }

        setVisible(true);
    }

    /**
     * Handles board change events, updates view to show new board state
     * @param e The event indicating a board change
     */
    @Override
    public void handleBoardChangeEvent(BoardChangeEvent e) {
        validate();
        repaint();
    }

    /**
     * Return all the board controllers (1/tile in the board)
     * @return all the board controllers in the board view.
     */
    public List<BoardTileController> getControllers() {
        List<BoardTileController> controllers = new ArrayList<>();
        for (int x = 0; x < BOARD_SIZE; x++) {
            for (int y = 0; y < BOARD_SIZE; y++) {
                Point p = new Point(x,y);

                controllers.add(boardTileViewGrid.get(p).getController());
            }
        }

        return controllers;
    }
}
