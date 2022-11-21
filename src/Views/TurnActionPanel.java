package Views;

import Controllers.BoardTileController;
import Controllers.TurnActionController;
import Model.ScrabbleModel;
import ScrabbleEvents.ControllerEvents.C_DirectionChangeEvent;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.ModelEvent;
import ScrabbleEvents.ModelEvents.NewPlayerEvent;


import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.List;

import static Model.ScrabbleModel.SIDE_BACKGROUND_COLOR;
import static Views.DebugView.DEBUG_VIEW;

public class TurnActionPanel extends JPanel implements ModelListener {

    private JPanel turnPanel;
    private JPanel actionPanel;
    private JPanel directionPanel;
    private JPanel skipPanel;

    private JLabel turnLabel;
    private JLabel actionLabel;
    private JButton placeButton;
    private JButton discardButton;
    private JButton skipButton;
    private JButton directionButton;
    private final int width = 300;
    private final int height = 300;

    TurnActionController controller;

    private String currentPlayerName;

    // FIXME: coupling with board controller, look for ways to decouple
    public TurnActionPanel(ScrabbleModel model, List<BoardTileController> board) {
        turnPanel = new JPanel();
        actionPanel = new JPanel();
        skipPanel = new JPanel();
        directionPanel = new JPanel();
        Border blackline = BorderFactory.createMatteBorder(0,0,2,0, Color.BLACK);


        turnPanel.setPreferredSize(new Dimension(width, height/5));
        turnPanel.setBackground(SIDE_BACKGROUND_COLOR);
        turnPanel.setBorder(blackline);


        actionPanel.setPreferredSize(new Dimension(width, height/5 ));
        actionPanel.setBackground(SIDE_BACKGROUND_COLOR);
        actionPanel.setBorder(blackline);

        directionPanel.setPreferredSize(new Dimension(width, height/5));
        directionPanel.setBackground(SIDE_BACKGROUND_COLOR);
        directionPanel.setBorder(blackline);

        skipPanel.setPreferredSize(new Dimension(width, height/5*2));
        skipPanel.setBackground(SIDE_BACKGROUND_COLOR);



        currentPlayerName = model.getCurPlayer().getName();

        setUpTurnPanel();
        setUpDirectionPanel(model);
        setUpActionButtons(model, board);
        setUpSkipButton(model);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(turnPanel);
        add(actionPanel);
        add(directionPanel);
        add(skipPanel);

        model.addModelListener(this);
        setSize(width, height);

    }

    private void setUpTurnPanel(){
        turnLabel = new JLabel("Turn: ");
        turnLabel.setFont(new Font("Serif", Font.BOLD, 15));
        turnLabel.setForeground(Color.WHITE);

        JLabel playerLabel = new JLabel(currentPlayerName);
        playerLabel.setFont(new Font("Serif", Font.BOLD, 15));
        playerLabel.setForeground(Color.WHITE);

        turnPanel.setLayout(new GridBagLayout());

        turnPanel.add(turnLabel);
        turnPanel.add(playerLabel);
    }

    private void setUpActionButtons(ScrabbleModel model, List<BoardTileController> board){
        placeButton = new JButton("Place");
        discardButton = new JButton("Discard");

        placeButton.addActionListener(new TurnActionController(model, board));
        discardButton.addActionListener(new TurnActionController(model, TurnActionController.ActionState.DISCARD));


        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;

        actionPanel.setLayout(new GridBagLayout());
        actionPanel.add(placeButton, c);
        actionPanel.add(discardButton, c);

    }
    private void setUpDirectionPanel(ScrabbleModel model)
    {
        JLabel directionLabel = new JLabel("Direction:");
        directionLabel.setForeground(Color.WHITE);
        directionButton = new JButton();
        // Set direction controller, and listeners
        TurnActionController directionControl = new TurnActionController(model, TurnActionController.ActionState.FLIP_DIR);
        directionButton.addActionListener(directionControl);
        directionControl.addControllerListener(e -> setDirectionViewText(e, directionButton));
        if(DEBUG_VIEW) model.addDebugController(directionControl);

        directionPanel.setLayout(new GridBagLayout());


        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0.5;
        c.weighty = 0.5;

        directionPanel.add(directionLabel, c);
        directionPanel.add(directionButton, c);
    }

    /**
     * Set the direction view's text based on the controller's state,
     * Will only act on DirectionChangeEvent (which contain the direction state)
     *
     * @param e The controller event which triggered the call
     * @param button The button to modify if applicable
     */
    private void setDirectionViewText(ControllerEvent e, JButton button) {
        if(e instanceof C_DirectionChangeEvent de) button.setText(de.dir().toString());
    }

    private void setUpSkipButton(ScrabbleModel model){
        skipButton = new JButton("Skip");
        skipButton.addActionListener(new TurnActionController(model, TurnActionController.ActionState.SKIP));

        skipPanel.setLayout(new GridBagLayout());
        skipPanel.add(skipButton);
    }


    @Override
    public void handleModelEvent(ModelEvent e) {
        if(e instanceof NewPlayerEvent newPlayer){
            if(!newPlayer.player().getName().equals(currentPlayerName)){
                currentPlayerName = newPlayer.player().getName(); // FIXME: redundant?
                turnLabel.setText("Turn: "+currentPlayerName);

            }
        }

    }
}
