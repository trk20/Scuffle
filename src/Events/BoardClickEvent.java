package Events;

import Controllers.BoardController;
import Controllers.HandTileController;
import Controllers.SController;

import java.awt.*;

public class BoardClickEvent extends ControllerEvent{
    SController controller;
    public BoardClickEvent(SController controller){
        super(controller);
        this.controller = controller;
    }

    public Point getOrigin(){
        BoardController c = (BoardController) getController();
        return c.getOrigin();
    }
}
