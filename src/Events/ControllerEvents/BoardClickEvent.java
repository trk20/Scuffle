package Events.ControllerEvents;

import java.awt.*;

public class BoardClickEvent extends ControllerEvent {
    private final Point origin;
    public BoardClickEvent(Point origin){
        this.origin = origin;
    }

    public Point getOrigin(){
        return origin;
    }
}
