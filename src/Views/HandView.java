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
    /** Model reference, needed to pass it to new controllers as a listener*/
    final private ScrabbleModel model;

    /**
     * HandView constructor, initializes fields and components of the View.
     */
    HandView(ScrabbleModel model){
        unselected_row = new JPanel();
        selected_row = new JPanel();
        handTileMap = new HashMap<>();
        this.model = model;

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
     * Keeps GUi up to date any time something is added/removed.
     * Should be called at the end of each "update" method.
     */
    private void updateGUI(){
        validate();
        repaint();
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
        if(e.getSelection()){ // Select tile
            selected_row.add(view);
            unselected_row.remove(view);
        } else{ // Un-select tile
            unselected_row.add(view);
            selected_row.remove(view);
        }

        updateGUI();
    }

    /**
     * New hand means clearing all the old tiles, and setting all the new ones.
     * They are all considered "unselected" initially and go to the bottom row.
     * @param h The new hand to set in the unselected row
     */
    private void updateNewHand(Hand h){
        // Clear old hand (including tile->view mappings from old hand)
        unselected_row.removeAll();
        selected_row.removeAll();
        handTileMap.clear();

        // Set new Hand
        for(Tile t: h.getHeldTiles()){
//            System.out.println(t.getLetter());
            addNewTileView(t);
        }

        updateGUI();
    }

    /**
     * Adds a new tile view to the HandView.
     * Groups a set of operations that need to be done each time to ensure correct behavior.
     *
     * @param tile The tile to add to the hand
     */
    private void addNewTileView(Tile tile){

        TileView view = new TileView(tile);
        unselected_row.add(view);
        // Add model listener to tile
        view.addControllerListener(model);
        // Update map for the new hand, for each tile
        handTileMap.put(tile, view);
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
