package Views;

import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.ModelEvent;
import ScrabbleEvents.ModelEvents.PlayerChangeEvent;
import Model.Player;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static Model.ScrabbleModel.SIDE_BACKGROUND_COLOR;

/**
 * Score view is responsible for displaying the Players scores during the game
 *
 * @author Vladimir Kovacina
 * @version NOV-9
 */
public class ScoreView extends JPanel implements ModelListener {

    final private ScrabbleModel model;

    private JLabel title;
    private JPanel scorePanel;

    private JLabel[] playerLabels;

    private final int width = 300;
    private final int height = 300;


    public ScoreView(ScrabbleModel model){
        super();
        //initialize model, JPanel and JLabels
        this.model = model;
        playerLabels = new JLabel[model.getPlayers().size()];
        scorePanel = new JPanel();

        //Initialize how the JPanel will look
        scorePanel.setPreferredSize(new Dimension(width, height));
        scorePanel.setBackground(SIDE_BACKGROUND_COLOR);
        scorePanel.setLayout(new GridLayout(5,1));

        //Create the title of the JPanel
        Font font = new Font("Courier", Font.BOLD, 14);
        title = new JLabel("Player Scores:", JLabel.CENTER);

        //Making font underlined
        Font underLinedFont = title.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(font.getAttributes());
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);

        title.setForeground(Color.white);
        title.setFont(font.deriveFont(attributes));

        scorePanel.add(title);

        //Initially set the JLabels as empty and white font
        for (int i =0; i < playerLabels.length; i++){
            playerLabels[i] = new JLabel("",JLabel.CENTER);
            playerLabels[i].setForeground(Color.white);
            scorePanel.add(playerLabels[i]);
        }

        //Add this view to the model listeners
        model.addModelListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(scorePanel);
        setSize(width, height);

    }

    /**
     * Used to update the scoreboard
     * @author: Vladimir Kovacina
     * @param updatedPlayers list of players to update the score from
     */
    // TODO: if players are a map, only need the changed player's score (less updates needed most of the time)
    //  Will also allow us to eliminate an event
    public void updateScores(List<Player> updatedPlayers){
        List<Player> players = updatedPlayers;
        for (int i =0; i < players.size();i++ ){
            //Change the JLabels to display the players names and scores
            playerLabels[i] .setText(players.get(i).getName()+ ":\t "+ players.get(i).getScore());
        }
    }

    /**
     * Handles receiving a ModelEvent
     * @author: Vladimir Kovacina
     * @param e the ModelEvent
     *
     */

    @Override
    public void handleModelEvent(ModelEvent e) {
        if(e instanceof PlayerChangeEvent newPlayers) updateScores(newPlayers.players());
    }
}
