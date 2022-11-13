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
        DISCARD (new DiscardClickEvent(new TurnActionController())),
        // TODO: FLIP_DIR(); ESSENTIAL FOR M2
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
    /** Flipped by FLIP_DIR action, for PLACE to use */
    private static Board.Direction dir = Board.Direction.DOWN;
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
        // Notify controller listeners (if action has an event)
        if(action.event != null) notifyControllerListeners(action.event);
    }

    
    @Override
    public void handleBoardClickEvent(BoardClickEvent e) {
        if(placing)
            notifyControllerListeners(new PlaceClickEvent(this, dir, e.getOrigin()));
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
