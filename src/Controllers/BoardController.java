package Controllers;
import Events.BoardClickEvent;
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
        notifyControllerListeners();
    }

    public Point getOrigin(){
        return origin;
    }
    @Override
    public void addControllerListener(SControllerListener l) {listeners.add(l);}

    @Override
    public void notifyControllerListeners() {
        for (SControllerListener l: listeners) {
            l.handleControllerEvent(new BoardClickEvent(this));
        }
    }
}
