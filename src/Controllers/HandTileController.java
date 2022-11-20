package Controllers;

import Model.Tile;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.ControllerEvents.TileClickEvent;
import ScrabbleEvents.Listeners.SControllerListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class HandTileController is a Controller for hand tiles.
 * Enables clicking on tiles, and hovering over them.
 * A view class has to extend this class, this allows for separation of the controller and view code.
 */
public class HandTileController extends MouseAdapter implements SController {
    private List<SControllerListener> listeners;
    private final Tile tile;

    /**
     * HandTileController constructor. Initiates fields.
     */
    public HandTileController(Tile tile){
        this.listeners = new ArrayList<>();
        this.tile = tile;
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
    public void notifyControllerListeners(ControllerEvent e) {
        for (SControllerListener l: listeners) {
            l.handleControllerEvent(e);
        }
    }

    /**
     * Send a tile click event to any listeners
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        notifyControllerListeners(new TileClickEvent(tile));
    }
}
