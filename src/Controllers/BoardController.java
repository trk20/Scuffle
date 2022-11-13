package Controllers;
import Events.BoardClickEvent;
import Events.ControllerEvent;
import Events.Listeners.SControllerListener;
import Model.ScrabbleModel;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BoardController implements SController{
    private List<SControllerListener> listeners;
    private Point origin;
    private ScrabbleModel model;

    public BoardController(ScrabbleModel model, Point origin) {
        this.listeners = new ArrayList<>();
        this.model = model;
        this.origin = origin;
    }

    public void handleBoardClick(){
        model.setPlacementLocation(origin);
        notifyControllerListeners(new BoardClickEvent(this));
    }

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
}
