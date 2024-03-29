package Controllers;

import Model.Board;
import Model.ScrabbleModel;
import ScrabbleEvents.ControllerEvents.*;
import ScrabbleEvents.Listeners.BoardClickListener;
import ScrabbleEvents.Listeners.SControllerListener;
import ScrabbleEvents.ModelEvents.ME_ModelChangeEvent;
import ScrabbleEvents.ModelEvents.ModelEvent;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static Views.DebugView.DEBUG_VIEW;

/**
 * TurnActionController handles sending events resulting from clicking
 * buttons in the TurnActionView.
 *
 * @author Kieran
 * @author Alex
 * @version NOV-21
 */
public class TurnActionController extends TurnController implements BoardClickListener, ActionListener {
    /** Depending on the action, will send different action events to listeners*/
    public enum ActionState {
        PLACE (null), // Special case, event made outside
        // FIXME: I'm realising, this might be the odd one out, might not need to make an event field
        DISCARD (new DiscardClickEvent()),
        FLIP_DIR(null),
        SKIP (new C_SkipEvent());

        private final TurnActionEvent event;

        ActionState(TurnActionEvent e){
            event = e;
        }
    }
    
    private final ActionState action;
    private final List<SControllerListener> listeners;
    private static Board.Direction dir = Board.Direction.RIGHT;
    /** Indicates if placed was selected, and waiting for a board click*/
    private static boolean placing = false;

    /**
     * Constructor for a TurnActionController (normal actions)
     *  Precondition: action is NOT PLACE, unless called by the other constructor.
     * @param action Discard, Skip, Flip tile
     */
    public TurnActionController(ScrabbleModel model, ActionState action) {
        super(model);
        // TODO: should probably separate PLACE into its own class for safety (M3)
        this.action = action;
        listeners = new ArrayList<>();
        addControllerListener(model);
    }

    /**
     * Constructor for a TurnActionController (for the place action)
     * @param board The board controllers to listen to for click events
     */
    public TurnActionController(ScrabbleModel model, List<BoardTileController> board) {
        this(model, ActionState.PLACE);
        for(BoardTileController c: board){
            c.addControllerListener(this);
        }
        if(DEBUG_VIEW)
            model.addDebugController(this);
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!disableControl){
            // Only enter placing mode if placed was clicked, otherwise exit it
            if(!placing && action == ActionState.PLACE)
                placing = true;
            if(action == ActionState.FLIP_DIR) flipDir();
            // Notify controller listeners (if action has an event)
            if(action.event != null) notifyControllerListeners(action.event);
        }
    }

    /**
     * Go to the next direction in the list of available directions
     * (currently there are two: right, down; they alternate)
     */
    private void flipDir() {
        int dirInt = dir.ordinal();
        Board.Direction[] allDirections = Board.Direction.values();
        dir = allDirections[(dirInt+1)%(allDirections.length)];
        notifyControllerListeners(new C_DirectionChangeEvent(dir));
    }

    /**
     * Only send a board click event if place has been clicked last in the options,
     * and you have clicked it during your own turn.
     * If it is handled, sends the placement direction and location to the model.
     *
     * @param e C_BoardClickEvent with information on where the board was clicked
     */
    @Override
    public void handleBoardClickEvent(C_BoardClickEvent e) {
        if(placing && !disableControl) {
            placing = false; // Disable place mode before next turn
            notifyControllerListeners(new PlaceClickEvent(dir, e.origin()));
        }
    }

    @Override
    public void handleModelEvent(ModelEvent e) {
        super.handleModelEvent(e); // Ensures TurnController event handling occurs as well
        if(e instanceof ME_ModelChangeEvent mce){
            listeners.clear(); // Remove old model reference
            addControllerListener(mce.newModel());
        }
    }
        
    /**
     * Add a listener to notify when an event is raised.
     *
     * @param l the listener to add to this SController.
     */
    @Override
    public void addControllerListener(SControllerListener l) {
        listeners.add(l);
        // Make sure it starts with initial direction value
        l.handleControllerEvent(new C_DirectionChangeEvent(dir));
    }

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

    public Board.Direction getDir(){
        return dir;
    }
}
