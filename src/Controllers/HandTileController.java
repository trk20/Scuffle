package Controllers;

import Model.SModel;
import Model.Tile;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.ControllerEvents.TileClickEvent;
import ScrabbleEvents.Listeners.SControllerListener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * HandTileController is a TurnController that enables clicking on tiles in hand to select them.
 * Raises TileClickEvent(s) when that happens.
 *
 * @author Alex
 * @version NOV-21
 */
public class HandTileController extends TurnController implements SController, MouseListener {
    private final List<SControllerListener> listeners;
    private final Tile tile;

    /**
     * HandTileController constructor. Initiates fields.
     */
    public HandTileController(Tile tile, SModel model){
        super(model);
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
        if(!disableControl)
            notifyControllerListeners(new TileClickEvent(tile));
    }
}
