package Views;

import javax.swing.*;
import java.awt.*;

public class TurnActionPanel extends JPanel{

        private JPanel turnPanel;
        private JPanel actionPanel;
        private JPanel undoPanel;

        private JLabel turnLabel;
        private JButton placeButton;
        private JButton discardButton;
        private JButton undoButton;
        private final int width = 300;
        private final int height = 300;

        private String currentPlayer;

        public TurnActionPanel() {
            turnPanel = new JPanel();
            actionPanel = new JPanel();
            undoPanel = new JPanel();

            turnPanel.setPreferredSize(new Dimension(width, height/4));
            turnPanel.setBackground(Color.BLACK);

            actionPanel.setPreferredSize(new Dimension(width, height/2));
            actionPanel.setBackground(Color.green);

            undoPanel.setPreferredSize(new Dimension(width, height/4));
            undoPanel.setBackground(Color.blue);

            currentPlayer = "Kieran";

            setUpTurnLabel();
            setUpActionButtons();
            setUpUndoButton();

            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            add(turnPanel);
            add(actionPanel);
            add(undoPanel);

            setSize(width, height);

        }

        private void setUpTurnLabel(){
            turnLabel = new JLabel("Turn: " + currentPlayer);
            turnLabel.setFont(new Font("Serif", Font.BOLD, 15));
            turnLabel.setForeground(Color.WHITE);

            turnPanel.setLayout(new GridBagLayout());
            turnPanel.add(turnLabel);
        }

        private void setUpActionButtons(){
            placeButton = new JButton("Place");
            discardButton = new JButton("Discard");
            GridBagConstraints c = new GridBagConstraints();

            c.weightx = 0.5;
            c.weighty = 0.5;

            actionPanel.setLayout(new GridBagLayout());
            actionPanel.add(placeButton, c);
            actionPanel.add(discardButton, c);
        }

        private void setUpUndoButton(){
            undoButton = new JButton("Undo");

            undoPanel.setLayout(new GridBagLayout());
            undoPanel.add(undoButton);
        }


        public static void main(String[] args) {
            TurnActionPanel panel = new TurnActionPanel();

        }


    }
