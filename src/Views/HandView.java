package Views;

import Events.HandChangeEvent;
import Events.Listeners.HandChangeListener;
import Events.NewPlayerHandEvent;
import Events.TileClickEvent;
import Events.TileSelectEvent;
import Model.Hand;
import Model.Letter;
import Model.ScrabbleModel;
import Model.Tile;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * HandView is responsible for displaying information about modeled Hand objects.
 * Displays held tiles, shows which ones are selected, and in what order.
 *
 * @author Alex
 * @version NOV-9
 */
public class HandView extends JPanel implements HandChangeListener {
    /** Row with the selected tiles*/
    final private JPanel selected_row;
    /** Row with the tiles not yet selected*/
    final private JPanel unselected_row;
    /** Maps model tiles to their respective tile view (for selection referencing)*/
    final private HashMap<Tile, TileView> handTileMap;

    /**
     * HandView constructor, initializes fields and components of the View.
     */
    HandView(ScrabbleModel model){
        unselected_row = new JPanel();
        selected_row = new JPanel();
        handTileMap = new HashMap<>();

        // Composed of two sections, selected tiles on top, unselected on the bottom.
        setLayout(new GridLayout(2, 1));
        add(selected_row);
        add(unselected_row);

        model.addModelListener(this);

        // FIXME: remove after testing
//        selected_row.add(new TileView(new Tile(Letter.S)));
        unselected_row.add(new TileView(new Tile(Letter.N)));
        unselected_row.add(new TileView(new Tile(Letter.U)));
        unselected_row.add(new TileView(new Tile(Letter.L)));
        unselected_row.add(new TileView(new Tile(Letter.L)));

        // Selection test
        Tile st = new Tile(Letter.S);
        TileView sv = new TileView(st);
        handTileMap.put(st, sv);
        unselected_row.add(sv);
        updateSelectionRow(new TileSelectEvent(new ScrabbleModel(), st, true));
    }

    /**
     * From the HandView perspective, toggling selection means switching a tile view's row.
     * Precondition: tile passed is in handTileMap (should be handled when creating the view instance)
     *
     * @param e The tile selection event which triggered the selection call
     * @throws NullPointerException if the hand tile map does not find the provided tile
     */
    private void updateSelectionRow(TileSelectEvent e) throws NullPointerException{
        // Get view from model reference of tile
        TileView view = handTileMap.get(e.getTile());
        if(view == null){
            throw new NullPointerException("Hand tile map not properly set");
        }

        // Add in desired row, remove in other (since it should always be in a row, but not both)
        final boolean selected = e.getSelection();
        if(selected){ // Select tile
            selected_row.add(view);
            unselected_row.remove(view);
        } else{ // Un-select tile
            unselected_row.add(view);
            selected_row.remove(view);
        }
    }

    /**
     * New hand means clearing all the old tiles, and setting all the new ones.
     * They are all considered "unselected" initially and go to the bottom row.
     * @param h The new hand to set in the unselected row
     */
    private void updateNewHand(@NotNull Hand h){
        // Clear old hand (including tile->view mappings from old hand)
        unselected_row.removeAll();
        selected_row.removeAll();
        handTileMap.clear();

        // Set new Hand
        for(Tile t: h.getHeldTiles()){
            System.out.println(t.getLetter());
            TileView view = new TileView(t);
            unselected_row.add(view);
            // Update map for the new hand, for each tile
            handTileMap.put(t, view);
        }

        // Update GUI to show new hand
        validate();
        repaint();
    }

    /**
     * Handles hand change events. This either indicates a selection, or a discard/draw event.
     * @param e the information related to the event.
     */
    @Override
    public void handleHandChangeEvent(HandChangeEvent e) {
        // Handle based on event type
        if(e instanceof TileSelectEvent sel) updateSelectionRow(sel);
        if(e instanceof NewPlayerHandEvent newHand) updateNewHand(newHand.getHand());
    }
}
