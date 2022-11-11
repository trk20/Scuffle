package Controllers;
import Model.ScrabbleModel;


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

    private final ScrabbleModel model;

    public TurnActionController(ScrabbleModel model) {
        this.model = model;
    }

    public void handleButtonPress(ActionState action){
        System.out.println("CURRENT STATE "+ action.name());
        if(action.getName().equals("Place")){
            model.placeHand();
        }else if (action.getName().equals("Discard")){
            model.discardHand();
        } else {
            model.skipTurn();
        }
    }


}
