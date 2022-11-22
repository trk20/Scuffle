import Model.BoardValidator;
import Model.ScrabbleModel;
import Views.DebugView;
import Views.OptionPaneHandler;
import Views.ScrabbleFrame;

import static Views.DebugView.DEBUG_VIEW;

/**
 * The main executable for the scrabble project.
 * Takes care of setting up the views and model initially.
 *
 * @author Alex
 * @version NOV-11
 */
public class Main {
    public static void main(String[] args){
        OptionPaneHandler optionPaneHandler = new OptionPaneHandler();
        // TODO: may change model placement, here for testing atm
        ScrabbleModel model = new ScrabbleModel(optionPaneHandler.getNewPlayers());
        model.addModelListener(optionPaneHandler);
        ScrabbleFrame frame = new ScrabbleFrame(model);

        // Text view for debug outputs
        DebugView debug;
        if(DEBUG_VIEW) {
            debug = new DebugView(model);
            debug.listenToControllers(model.getDebugControllers());
        }

        model.startGame();
    }
}
