package Views;

import Model.Letter;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

import static Views.HandTileView.TILE_BEIGE;

public abstract class TileView extends JPanel {
    /**
     * Set visuals for a letter tile. This includes a border, and a background color,
     * There is also text indicating the letter it represents.
     */
    protected void styleLetterTile(Letter letter) {
        // Make a "raised" border
        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        // Set beige background
        setBackground(TILE_BEIGE);
        // Sets the tile labels
        labelTile(letter);
    }

    /**
     * Set the tile labels, includes the tile's letter, and score.
     */
    protected void labelTile(Letter letter){
        // Make 3x3 layout, add Letter in the center and Score in the bottom right.
        final int size = 3;
        setLayout(new GridLayout(size,size));
        JLabel[][] labelHolder = new JLabel[size][size];

        // Letter label
        JLabel letterLabel = new JLabel(getTile().getLetter().toString());
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
}
