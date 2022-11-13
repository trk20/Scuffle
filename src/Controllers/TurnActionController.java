package Controllers;
import Events.*;
import Events.Listeners.BoardClickListener;
import Events.Listeners.SControllerListener;
import Model.Board;
import Model.ScrabbleModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * TurnActionController handles sending events resulting from clicking
 * buttons in the TurnActionView.
 *
 * @author Kieran
 * @author Alex
 * @version NOV-13
 */
public class TurnActionController implements SController, BoardClickListener, ActionListener {
    /** Depending on the action, will send different action events to listeners*/
    public enum ActionState {
        PLACE (null), // Special case, event made outside
        // FIXME: I'm realising, this might be the odd one out, might not need to make an event field
        DISCARD (new DiscardClickEvent(new TurnActionController())),
        FLIP_DIR(null),
        SKIP (null); // TODO (discard should work well enough for M2)

        private final TurnActionEvent event;

        ActionState(TurnActionEvent e){
            event = e;
        }

        private TurnActionEvent getEvent() {
            return event;
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
        // TODO: should probably separate PLACE into its own class for safety (M3)
        this.action = action;
        listeners = new ArrayList<>();
        listeners.add(model);
    }

    /**
     * Constructor for a TurnActionController (for the place action)
     * @param board The board controllers to listen to for click events
     */
    public TurnActionController(ScrabbleModel model, List<BoardController> board) {
        this(model, ActionState.PLACE);
        for(BoardController c: board){
            c.addControllerListener(this);
        }
    }
    
    /**
     * Null constructor for TurnActionController, temporary fix to create events in enum!
     */
    @Deprecated
    private TurnActionController() {
        action = null;
        listeners = null;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        // Only enter placing mode if placed was clicked, otherwise exit it
        placing = action == ActionState.PLACE;
        if(action == ActionState.FLIP_DIR) flipDir();
        // Notify controller listeners (if action has an event)
        if(action.event != null) notifyControllerListeners(action.event);
    }

    /**
     * Go to the next direction in the list of available directions
     * (currently there are two: right, down; they alternate)
     */
    private void flipDir() {
        int dirInt = dir.ordinal();
        Board.Direction[] allDirections = Board.Direction.values();
        dir = allDirections[(dirInt+1)%(allDirections.length)];
        notifyControllerListeners(new DirectionChangeEvent(this));
    }

    /** Get board direction (for direction change events) */
    public Board.Direction getDir() {
        System.out.println(dir.toString());
        return dir;
    }

    /**
     * Only send a board click event if place has been clicked last in the options,
     * and you have clicked it during your own turn.
     * If it is handled, sends the placement direction and location to the model.
     *
     * @param e BoardClickEvent with information on where the board was clicked
     */
    @Override
    public void handleBoardClickEvent(BoardClickEvent e) {
        if(placing) {
            placing = false; // Disable place mode before next turn
            notifyControllerListeners(new PlaceClickEvent(this, dir, e.getOrigin()));
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

}
