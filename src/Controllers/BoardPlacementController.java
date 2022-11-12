package Controllers;
import Model.ScrabbleModel;

public class BoardPlacementController{
    private final ScrabbleModel model;

    public BoardPlacementController(ScrabbleModel model) {
        this.model = model;
    }

    public void handleBoardPlacementSelection(int row,int col){
        model.setPlacementLocation(row,col);
    }

    public void handleBoardPlacementSelection(boolean direction){
        model.setPlacementDirection(direction);
    }


}
