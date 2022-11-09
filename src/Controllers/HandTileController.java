package Controllers;

import Events.Listeners.SControllerListener;
import Events.TileClickEvent;
import Model.Tile;

import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class HandTileController is a Controller for hand tiles.
 * Enables clicking on tiles, and hovering over them.
 * A view class has to extend this class, this allows for separation of the controller and view code.
 */
public abstract class HandTileController extends JPanel implements SController {
    private List<SControllerListener> listeners;
    private final Tile tile;

    /**
     * HandTileController constructor. Initiates fields.
     */
    protected HandTileController(Tile tile){
        this.listeners = new ArrayList<>();
        this.tile = tile;
        // See inner class to see how mouse handler works
        this.addMouseListener(new MouseHandler());
    }

    /**
     * Add a listener to notify when an event is raised.
     *
     * @param l the listener to add to this SController.
     */
    @Override
    public void addControllerListener(SControllerListener l) {
        listeners.add(l);
    }

    /**
     * Notify listeners by sending them a controller event.
     */
    @Override
    public void notifyControllerListeners() {
        for (SControllerListener l: listeners) {
            l.handleControllerEvent(new TileClickEvent(this));
        }
    }

    /**
     * Getter for tile
     * @return tile for this controller.
     */
    public Tile getTile() {
        return tile;
    }


    /**
     * Flips the selection of the tile in the model.
     */
    protected void flipSelection(){
        notifyControllerListeners();
    }

    /**
     * Highlights the tile in the view (lower prio)
     */
    abstract protected void highlight();

    /**
     * Undo highlighting for the tile in the view (lower prio)
     */
    abstract protected void undoHighlight();

    /**
     * Inner class do handle mouse events on this JPanel.
     * Handles highlighting on hover, and tile selections on click.
     */
    private class MouseHandler extends MouseAdapter {
        /**
         * Flip tile selection on click
         * @param e the event to be processed
         */
        @Override
        public void mouseClicked(MouseEvent e) {
            flipSelection();
        }

        /**
         * Highlight tile when mouse enters it
         * @param e the event to be processed
         */
        @Override
        public void mouseEntered(MouseEvent e) {
            highlight();
        }

        /**
         * Remove highlighting when mouse exits tile.
         * @param e the event to be processed
         */
        @Override
        public void mouseExited(MouseEvent e) {
            undoHighlight();
        }
    }
}
