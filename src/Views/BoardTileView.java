package Views;

import Controllers.BoardTileController;
import Model.BoardTile;
import Model.ScrabbleModel;
import ScrabbleEvents.Listeners.BoardChangeListener;
import ScrabbleEvents.ModelEvents.BoardChangeEvent;

import javax.swing.*;
import java.awt.*;

public class BoardTileView extends TileView implements BoardChangeListener {
    private final Point boardPoint;
    private final BoardTileController controller;

    BoardTileView(ScrabbleModel model, Point p){
        boardPoint = p;
        model.addModelListener(this);
        controller = new BoardTileController(p);
        this.addMouseListener(controller);
        // Empirically found a size to stick with
        setPreferredSize(new Dimension(30,30));
    }

    @Override
    public void handleBoardChangeEvent(BoardChangeEvent e) {
        BoardTile updatedTile = e.board().getBoardTile(boardPoint);
        styleBoardTile(updatedTile);
    }

    /**
     * This function is a one way finite state machine:
     * State 0: Tile created, not styled yet.
     * State 1: Update received, and was in State 0 ->
     *  Set unoccupied tile style, based on the type of tile (premiums, normals).
     * State 2: Update received, was in State 1,
     *   and a letter was placed on this board tile ->
     *   Style tile as a taken tile, based on the letter placed.
     * State 2 is a final state, ignores updates afterwards
     * @param updatedTile The updated tile in the model corresponding to this view
     */
    private void styleBoardTile(BoardTile updatedTile) {
        if(getComponentCount() == 0){ // Checks if in State 0
            // Shared properties for unoccupied tiles (1x1 grid, white border, premium label)
            setLayout(new GridLayout(1,1));
            setBorder(BorderFactory.createLineBorder(Color.WHITE, 2));
            // Set centered label (with tile type text)
            JLabel label = new JLabel(updatedTile.toString());
            label.setHorizontalAlignment(JLabel.CENTER);
            add(label);
            setBackground(updatedTile.getType().getColor()); // Get color from type enum
            return; // State 1 style set
        }
        if(isStyledAsLetter()){ // Checks if in State 2 (do nothing)
            return;
        }
        if(updatedTile.isTaken()){ // In State 1, check for placed letter
            remove(0); // Remove initial component (From State 1)
            styleLetterTile(updatedTile.getLetter());
            // Disable placement on placed tiles
            removeMouseListener(controller);
            return; // Style State 2
        }

    }

    /**
     * Checks if the tile is currently styled as a letter.
     * @return True if this view is already styled to show a letter.
     */
    private boolean isStyledAsLetter() {
        /* Since letter tiles use a 3x3 grid layout,
         * and non-taken tiles use a 1x1 grid,
         * if there is more than 1 component in the panel,
         * it has to be in the "letter tile" style already.
         */
        return this.getComponentCount() > 1;
    }

    public BoardTileController getController() {
        return controller;
    }
}
