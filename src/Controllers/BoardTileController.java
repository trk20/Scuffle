package Controllers;

import ScrabbleEvents.ControllerEvents.C_BoardClickEvent;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.Listeners.SControllerListener;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class BoardTileController extends MouseAdapter implements SController {
    private final List<SControllerListener> listeners;
    private final Point origin;

    public BoardTileController(Point origin) {
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
        notifyControllerListeners(new C_BoardClickEvent(origin));
    }
}
