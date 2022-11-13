package Views;

import Controllers.BoardController;
import Events.*;
import Events.Listeners.ModelListener;
import Model.Board;
import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel implements ModelListener {

    private JPanel boardPanel;
    private JButton[][] gridButtons;
    private JButton selectRightToLeft;
    private JButton selectTopToBottom;
    private int boardSize;
    private BoardController controller;
    private ScrabbleModel model;
    public BoardView(ScrabbleModel model) {
        boardSize = 15;
        this.model = model;
        model.addModelListener(this);
        setLayout(new BoxLayout(this,BoxLayout.LINE_AXIS));
        add(Box.createHorizontalGlue());
        setSize(800,500);
        setAlignmentX(RIGHT_ALIGNMENT);
        boardPanel = new JPanel();
        Font buttonFont = new Font("Consolas",Font.PLAIN,10);
        selectRightToLeft = new JButton();
        selectRightToLeft.setText("→");
        selectRightToLeft.setFont(buttonFont);
        selectRightToLeft.addActionListener(e->model.setPlacementDirection(Board.Direction.RIGHT));
        selectTopToBottom = new JButton();
        selectTopToBottom.setAlignmentX(CENTER_ALIGNMENT);
        selectTopToBottom.setText("↓");
        selectTopToBottom.setFont(buttonFont);
        selectTopToBottom.addActionListener(e->model.setPlacementDirection(Board.Direction.DOWN));
        add(selectRightToLeft);
        add(selectTopToBottom);
        add(boardPanel);
        add(Box.createHorizontalGlue());
        boardPanel.setAlignmentX(Component.RIGHT_ALIGNMENT);
        boardPanel.setLayout(new GridLayout(boardSize,boardSize));
        boardPanel.setMaximumSize(new Dimension(boardSize*30,boardSize*30));

        gridButtons = new JButton[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                controller = new BoardController(model,new Point(row, col));
                gridButtons[row][col] = new JButton();
                gridButtons[row][col].addActionListener(e->controller.handleBoardClick());
                gridButtons[row][col].setText(model.getBoardTileText(row,col));
                gridButtons[row][col].setMaximumSize(new Dimension(30,30));
                boardPanel.add(gridButtons[row][col]);
            }
        }
        setVisible(true);
    }
    public void update(){
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                gridButtons[row][col].setText(model.getBoardTileText(row,col));
            }
        }
    }
    @Override
    public void handleModelEvent(ModelEvent e) {
        if(e instanceof BoardPlaceEvent) update();
    }

}
