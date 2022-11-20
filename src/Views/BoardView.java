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

public class BoardView extends JPanel implements BoardChangeListener {
    private final Grid2DArray<BoardTileView> boardTileViewGrid;

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
     * Update the board view to represent the given board
     */
    public void update(BoardChangeEvent e){
//        for (int x = 0; x < BOARD_SIZE; x++) {
//            for (int y = 0; y < BOARD_SIZE; y++) {
//                Point p = new Point(x,y);
//
//                boardTileViewGrid.get(p).handleBoardChangeEvent(e);
//            }
//        }
        validate();
        repaint();
    }

    /**
     * Handles board change events, updates view to show new board state
     * @param e The event indicating a board change
     */
    @Override
    public void handleBoardChangeEvent(BoardChangeEvent e) {
        update(e);
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
