package Views;

import Controllers.BoardController;
import Controllers.TurnActionController;
import ScrabbleEvents.ControllerEvents.ControllerEvent;
import ScrabbleEvents.ControllerEvents.C_DirectionChangeEvent;
import ScrabbleEvents.Listeners.ModelListener;
import ScrabbleEvents.ModelEvents.ModelEvent;
import ScrabbleEvents.ModelEvents.NewPlayerEvent;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

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
    public TurnActionPanel(ScrabbleModel model, List<BoardController> board) {
        turnPanel = new JPanel();
        actionPanel = new JPanel();
        skipPanel = new JPanel();
        directionPanel = new JPanel();

        turnPanel.setPreferredSize(new Dimension(width, height/5));
        turnPanel.setBackground(Color.BLACK);

        actionPanel.setPreferredSize(new Dimension(width, height/5 ));
        actionPanel.setBackground(Color.green);

        directionPanel.setPreferredSize(new Dimension(width, height/5));
        directionPanel.setBackground(Color.WHITE);

        skipPanel.setPreferredSize(new Dimension(width, height/5*2));
        skipPanel.setBackground(Color.blue);


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
        turnLabel = new JLabel("Turn: " + currentPlayerName);

        turnLabel.setFont(new Font("Serif", Font.BOLD, 15));
        turnLabel.setForeground(Color.WHITE);

        turnPanel.setLayout(new GridBagLayout());

        turnPanel.add(turnLabel);
    }

    private void setUpActionButtons(ScrabbleModel model, List<BoardController> board){
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
        directionButton = new JButton();
        // Set direction controller, and listeners
        TurnActionController directionControl = new TurnActionController(model, TurnActionController.ActionState.FLIP_DIR);
        directionButton.addActionListener(directionControl);
        directionControl.addControllerListener(e -> setDirectionViewText(e, directionButton));

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
