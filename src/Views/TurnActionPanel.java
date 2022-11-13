package Views;

import Controllers.BoardController;
import Controllers.TurnActionController;
import Events.Listeners.ModelListener;
import Events.ModelEvent;
import Events.NewPlayerHandEvent;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TurnActionPanel extends JPanel implements ModelListener {

    private JPanel turnPanel;
    private JPanel actionPanel;
    private JPanel skipPanel;

    private JLabel turnLabel;
    private JLabel actionLabel;
    private JButton placeButton;
    private JButton discardButton;
    private JButton skipButton;
    private final int width = 300;
    private final int height = 300;

    TurnActionController controller;

    private String currentPlayerName;

    // FIXME: coupling with board controller, look for ways to decouple
    public TurnActionPanel(ScrabbleModel model, List<BoardController> board) {
        turnPanel = new JPanel();
        actionPanel = new JPanel();
        skipPanel = new JPanel();

        turnPanel.setPreferredSize(new Dimension(width, height/4));
        turnPanel.setBackground(Color.BLACK);

        actionPanel.setPreferredSize(new Dimension(width, height/2));
        actionPanel.setBackground(Color.green);

        skipPanel.setPreferredSize(new Dimension(width, height/4));
        skipPanel.setBackground(Color.blue);


        currentPlayerName = model.getCurPlayer().getName();

        setUpTurnLabel();
        setUpActionButtons(model, board);
        setUpSkipButton(model);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(turnPanel);
        add(actionPanel);
        add(skipPanel);

        model.addModelListener(this);
        setSize(width, height);

    }

    private void setUpTurnLabel(){
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

    private void setUpSkipButton(ScrabbleModel model){
        skipButton = new JButton("Skip");
        skipButton.addActionListener(new TurnActionController(model, TurnActionController.ActionState.SKIP));

        skipPanel.setLayout(new GridBagLayout());
        skipPanel.add(skipButton);
    }


    @Override
    public void handleModelEvent(ModelEvent e) {
        if(e instanceof NewPlayerHandEvent newHand){
            System.out.println(newHand.getPlayer().getName() + " " + currentPlayerName);
            if(!newHand.getPlayer().getName().equals(currentPlayerName)){
                currentPlayerName = newHand.getPlayer().getName();
                turnLabel.setText("Turn: "+currentPlayerName);

            }
        }

    }

    public static void main(String[] args) {

    }
}
