package Controllers;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.event.ActionEvent;


public class TurnActionController {

    public enum ActionState {
        PLACE ("Place"),
        DISCARD ("Discard"),
        SKIP ("Skip");

        private final String name;

        private ActionState(String s){
            name = s;
        }

        private String getName(){
            return name;
        }


    }
    public enum Direction {
        HORIZONTAL("→"),
        VERTICAL("↓");

        private final String arrow;

        private Direction(String arrow){
            this.arrow = arrow;
        }
        public String getArrow(){
            return arrow;
        }
    }
    private final ScrabbleModel model;
    private Direction currentDirection = Direction.HORIZONTAL;

    public TurnActionController(ScrabbleModel model) {
        this.model = model;
    }

    public void handleDirectionPress(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        if(currentDirection.equals(Direction.HORIZONTAL)){
            currentDirection = Direction.VERTICAL;
            button.setText(Direction.VERTICAL.getArrow());
            return;
        }
        button.setText(Direction.HORIZONTAL.getArrow());
        currentDirection = Direction.HORIZONTAL;
    }

    public void handleButtonPress(ActionState action){
        System.out.println("CURRENT STATE "+ action.name());
        if(action.getName().equals("Place")){
            model.placeHand();
        }else if (action.getName().equals("Discard")) {
            model.discardHand();
        }else{
            model.skipTurn();
        }
    }


}
