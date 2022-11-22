package Controllers;

import Model.SModel;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.AIPlayingEvent;
import ScrabbleEvents.ModelEvents.ModelEvent;

import java.awt.event.MouseAdapter;

/**
 * Abstract class for any controller that affects turn behavior.
 * Disabled when an AI is playing, enabled otherwise.
 *
 * @author Alex
 * @version NOV-21
 */
public abstract class TurnController extends MouseAdapter implements ModelListener {
    protected boolean disableControl;

    /**
     * Turn controller constructor, adds itself as a listener to
     * a model that should send AIPlaying signals to this controller.
     * @param model Model to listen to for AI events
     */
    TurnController(SModel model){
        model.addModelListener(this);
    }

    /**
     * Disable controller if AI is playing, enable it if it stopped playing.
     * Do nothing otherwise.
     * @param e If AIPlayingEvent, contains information on AI playing status. Otherwise, ignored.
     */
    @Override
    public void handleModelEvent(ModelEvent e){
        // Disable turn controllers if AI is playing
        if(e instanceof AIPlayingEvent ev)
            disableControl = ev.isPlaying();
    }
}
