package Views;

import Controllers.HandTileController;
import Model.Tile;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import java.awt.*;

public class TileView extends HandTileController {
    public static final Color TILE_BEIGE = new Color(245, 245, 220);
    public static final int TILE_MARGINS = 5;
    /**
     * TileView constructor, initializes fields.
     *
     * @param tile The tile to view
     */
    TileView(Tile tile){
        super(tile);

        setPreferredSize(new Dimension(80,80));
        // Add a label with the Letter as the text in the label.
        setLayout(new GridLayout(1,1));
        JLabel tileLabel = new JLabel(getTile().getLetter().toString());
        tileLabel.setHorizontalAlignment(JLabel.CENTER);
        add(tileLabel);
        // Set visuals for the tile
        styleTile();
    }

    /**
     * Set visuals for the tile. This includes a border, and a background.
     */
    private void styleTile() {
        // Make a "raised" border, with margins outside (acts as padding instead it looks like?)
//        Border innerBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
//        Border outerBorder = BorderFactory.createEmptyBorder(TILE_MARGINS, TILE_MARGINS, TILE_MARGINS,TILE_MARGINS);

        // Make a "raised" border
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        // Set beige background
        setBackground(TILE_BEIGE);

    }

    /**
     * Flips the selection of the tile in the model.
     */
    @Override
    protected void flipSelection() {
        // TODO
    }

    /**
     * Highlights the tile in the view (lower prio)
     */
    @Override
    protected void highlight() {
        // TODO (low prio)
    }

    /**
     * Undo highlighting for the tile in the view (lower prio)
     */
    @Override
    protected void undoHighlight() {
        // TODO (low prio)
    }
}
