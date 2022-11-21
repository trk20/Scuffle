package Controllers;

import Model.SModel;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.AIPlayingEvent;
import ScrabbleEvents.ModelEvents.ModelEvent;

import java.awt.event.MouseAdapter;

/**
 *
 * @author Alex
 * @version NOV-21
 */
public abstract class TurnController extends MouseAdapter implements ModelListener {
    protected boolean disableControl;

    TurnController(SModel model){
        model.addModelListener(this);
    }

    @Override
    public void handleModelEvent(ModelEvent e){
        // Disable turn controllers if AI is playing
        if(e instanceof AIPlayingEvent ev)
            disableControl = ev.isPlaying();
    }
}
