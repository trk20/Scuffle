package Views;

import Controllers.HandTileController;
import Model.Tile;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

/**
 * HandView is responsible for displaying information about individual Tile objects.
 * Displays Tile's letters. Also implements a mouse based controller to allow selecting the tiles.
 *
 * @author Alex
 * @version NOV-9
 */
public class TileView extends HandTileController {
    public static final Color TILE_BEIGE = new Color(245, 245, 220);
//    public static final int TILE_MARGINS = 5;
    /**
     * TileView constructor, initializes fields.
     *
     * @param tile The tile to view
     */
    public TileView(Tile tile){
        super(tile);

        // Set visuals for the tile
        styleTile();
    }

    /**
     * Set visuals for the tile. This includes a border, and a background color,
     * There is also text indicating the letter it represents.
     */
    private void styleTile() {
        // Make a "raised" border, with margins outside (acts as padding instead it looks like?)
//        Border innerBorder = BorderFactory.createBevelBorder(BevelBorder.RAISED);
//        Border outerBorder = BorderFactory.createEmptyBorder(TILE_MARGINS, TILE_MARGINS, TILE_MARGINS,TILE_MARGINS);

        // Empirically found good size.
        setPreferredSize(new Dimension(80,80));

        // Sets the tile labels
        labelTile();

        // Make a "raised" border
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        // Set beige background
        setBackground(TILE_BEIGE);

    }

    /**
     * Set the tile labels, includes the tile's letter, and score.
     */
    private void labelTile(){
        // Make 3x3 layout, add Letter in the center and Score in the bottom right.
        final int size = 3;
        setLayout(new GridLayout(size,size));
        JLabel[][] labelHolder = new JLabel[size][size];

        // Letter label
        JLabel letterLabel = new JLabel(getTile().letter().toString());
        letterLabel.setHorizontalAlignment(JLabel.CENTER);
        labelHolder[1][1] = letterLabel; // Add text at grid center.
        // Score label
        JLabel scoreLabel = new JLabel(getTile().getScore()+"");
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        labelHolder[size-1][size-1] = scoreLabel; // Add score at bottom right corner.


        // Add labels to grid, providing empty labels if they are not set yet.
        for(int m = 0; m < size; m++) {
            for(int n = 0; n < size; n++) {
                // Create new label if not set already.
                if (labelHolder[m][n] == null) {
                    labelHolder[m][n] = new JLabel();
                }
                add(labelHolder[m][n]);
            }
        }
    }

//    /**
//     * Flips the selection of the tile in the model.
//     */
//    @Override
//    protected void flipSelection() {
//        super.flipSelection();
//        // View changes on flip... for now handled by hand View?
//    }

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
