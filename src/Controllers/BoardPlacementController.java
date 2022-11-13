package Controllers;
import Model.ScrabbleModel;

import java.awt.*;

public class BoardPlacementController{
    private final ScrabbleModel model;

    public BoardPlacementController(ScrabbleModel model) {
        this.model = model;
    }

    public void handleBoardPlacementSelection(Point origin){
        model.setPlacementLocation(origin);
    }

    public void handleBoardPlacementSelection(boolean direction){
        model.setPlacementDirection(direction);
    }


}
