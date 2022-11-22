package Controllers;

import Model.SModel;
import ScrabbleEvents.ControllerEvents.C_BoardClickEvent;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.Listeners.SControllerListener;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

/**
 * BoardTileController is a TurnController that enables clicking on a tile in the board to select them.
 * Each tile in the board has its own controller.
 * Raises a C_BoardClickEvent when the tile is clicked on.
 *
 * @author Alex
 * @version NOV-21
 */
public class BoardTileController extends TurnController implements SController, MouseListener {
    private final List<SControllerListener> listeners;
    private final Point origin;

    public BoardTileController(Point origin, SModel model) {
        super(model);
        this.listeners = new ArrayList<>();
        this.origin = origin;
    }
    @Override
    public void addControllerListener(SControllerListener l) {listeners.add(l);}

    /**
     * Notify listeners by sending them a controller event.
     *
     * @param e the event to send to the listeners
     */
    @Override
    public void notifyControllerListeners(ControllerEvent e) {
        for (SControllerListener l: listeners) {
            l.handleControllerEvent(e);
        }
    }



    /**
     * Invoked when an action occurs, will send a board click event to listeners.
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        if(!disableControl){
            notifyControllerListeners(new C_BoardClickEvent(origin));
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}
}
