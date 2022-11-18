package Model;

import Controllers.SController;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.Listeners.SControllerListener;

import java.util.ArrayList;
import java.util.List;

public class AIPlayer extends Player implements SController {
    private List<ModelListener> modelListeners;

    /**
     * Model.Player constructor, sets a name from the name parameter,
     * initializes an empty hand, and sets score to 0 initially.
     *
     * @param model the Scrabble game's model
     */
    public AIPlayer(ScrabbleModel model) {
        super("stubName", model); // TODO: planning to refactor model out (of player)
        this.modelListeners = new ArrayList<>();
    }


    public void play(){
        // Send event to model to place tiles, or discard them
    }
    /**
     * Add a listener to notify when an event is raised.
     *
     * @param l the listener to add to this SController.
     */
    @Override
    public void addControllerListener(SControllerListener l) {

    }

    /**
     * Notify listeners by sending them a controller event.
     *
     * @param e the event to send to the listeners
     */
    @Override
    public void notifyControllerListeners(ControllerEvent e) {

    }
}
