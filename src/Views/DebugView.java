package Views;

import Controllers.SController;
import Model.ScrabbleModel;
import ScrabbleEvents.ControllerEvents.*;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.Listeners.SControllerListener;
import ScrabbleEvents.ModelEvents.*;

import java.util.List;

/**
 * Debug view used to print to terminal event information from the game.
 * Can enable entire class with "DEBUG_VIEW"
 * or specific event traces with their respective methods' traces: "TRACE_ON".
 *
 * @author Alex
 * @version NOV-19
 */
public class DebugView implements ModelListener, SControllerListener {
    final public static boolean DEBUG_VIEW = true;
    public DebugView(ScrabbleModel model) {
        model.addModelListener(this);
    }

    @Override
    public void handleModelEvent(ModelEvent e) {
        System.out.print("Model Event: ");
        if(e instanceof BoardChangeEvent ev){
            showBoardChangeEvent(ev);
        } else if(e instanceof BoardPlaceEvent){
            System.out.println("BoardPlaceEvent received");
        } else if(e instanceof NewPlayerEvent){
            System.out.println("NewPlayerEvent received");
        } else if(e instanceof PlayerChangeEvent){
            System.out.println("PlayerChangeEvent received");
        } else if(e instanceof TileSelectEvent){
            System.out.println("TileSelectEvent received");
        }
        else{
            System.out.println("(unhandled)");
        }
    }

    /**
     * Process Controller events when one is raised.
     *
     * @param e the event to process
     */
    @Override
    public void handleControllerEvent(ControllerEvent e) {
        System.out.print("Controller Event: ");
        if(e instanceof C_BoardClickEvent){
            System.out.println("BoardClickEvent received");
        } else if(e instanceof C_DirectionChangeEvent ev){
            showDirectionEvent(ev);
        } else if(e instanceof C_SkipEvent){
            System.out.println("C_SkipEvent received");
        } else if(e instanceof DiscardClickEvent){
            System.out.println("DiscardClickEvent received");
        } else if(e instanceof PlaceClickEvent ev){
            showPlaceClickEvent(ev);
        } else if(e instanceof TileClickEvent){
            System.out.println("TileClickEvent received");
        }
        else{
            System.out.println("(unhandled)");
        }
    }

    @SuppressWarnings({"ConstantConditions"})
    private void showBoardChangeEvent(BoardChangeEvent e) {

        final boolean TRACE_ON = true; // Can disable hard code trace here
        if(TRACE_ON) {
            System.out.println("BoardChangeEvent received");
            System.out.println("Board: "+ e.board());
        }
    }

    @SuppressWarnings({"ConstantConditions"})
    private void showDirectionEvent(C_DirectionChangeEvent e) {
        final boolean TRACE_ON = true; // Can disable hard code trace here
        if(TRACE_ON) {
            System.out.println("C_DirectionChangeEvent received");
            System.out.println("Dir: "+ e.dir());
        }
    }

    @SuppressWarnings({"ConstantConditions"})
    private void showPlaceClickEvent(PlaceClickEvent e) {
        final boolean TRACE_ON = true; // Can disable hard code trace here
        if(TRACE_ON) {
            System.out.println("PlaceClickEvent received");
            System.out.println("Origin: "+ e.origin());
            System.out.println("Dir: "+ e.dir());
        }
    }

    public void listenToControllers(List<SController> debugControllers) {
        for(SController c: debugControllers){
            c.addControllerListener(this);
        }
    }
}
