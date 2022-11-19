package Controllers;
import ScrabbleEvents.ControllerEvents.C_BoardClickEvent;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.Listeners.SControllerListener;
import Model.ScrabbleModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class BoardController implements SController, ActionListener {
    private List<SControllerListener> listeners;
    private Point origin;
    private ScrabbleModel model;

    public BoardController(ScrabbleModel model, Point origin) {
        this.listeners = new ArrayList<>();
        this.model = model;
        this.origin = origin;
    }

    @Deprecated
    public Point getOrigin(){
        return origin;
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
    public void actionPerformed(ActionEvent e) {
        notifyControllerListeners(new C_BoardClickEvent(origin));
    }
}
