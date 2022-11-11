package Views;

import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;


public class ScrabbleFrame extends JFrame{

    private JPanel centerContent;
    private JPanel leftContent;
    private JPanel rightContent;
    private JPanel northContent;
    private JPanel southContent;

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;
    private final int menuHeight = 50;

    private final ScrabbleModel model;


    public ScrabbleFrame(ScrabbleModel model) throws HeadlessException {
        super("Scrabble");
        this.model = model;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        centerContent = new JPanel();
        leftContent = new JPanel();
        rightContent = new JPanel();
        southContent = new JPanel();
        northContent = new JPanel();


        setDefaultContent();

        this.add(centerContent, BorderLayout.CENTER);
        this.add(leftContent, BorderLayout.WEST);
        this.add(northContent, BorderLayout.NORTH);
        this.add(southContent, BorderLayout.SOUTH);
        this.add(rightContent, BorderLayout.EAST);



        this.setSize(WIDTH, HEIGHT);
    }

    public void setDefaultContent(){
        JPanel tempCenterContent = new JPanel();
        tempCenterContent.setBackground(Color.WHITE);
        tempCenterContent.add(new JLabel("Model.Board"));
        setCenterContent(tempCenterContent);

//        JPanel tempLeftContent = new JPanel();
//        tempLeftContent.setBackground(Color.green);
//        tempLeftContent.add(new JLabel("Scores"));
        setLeftContent(new ScoreView(model));

//        JPanel tempNorthContent = new JPanel();
//        tempNorthContent.setBackground(Color.blue);
//        tempNorthContent.add(new JLabel("Menu"));
        setNorthContent(new MenuView(model));

        setSouthContent(new HandView(model));

        JPanel tempRightContent = new JPanel();
        tempRightContent.setBackground(Color.CYAN);
        tempRightContent.add(new JLabel("Turns"));

        setRightContent(new TurnActionPanel(model));
        setVisible(true);
    }

    public void setCenterContent(JPanel centerContent) {
        this.centerContent = centerContent;

        repaint();
        revalidate();
    }

    public void setLeftContent(JPanel leftContent) {
        leftContent.setPreferredSize(new Dimension(WIDTH/8, HEIGHT));
        this.leftContent = leftContent;

        repaint();
        revalidate();
    }

    public void setRightContent(JPanel rightContent) {
        rightContent.setPreferredSize(new Dimension(WIDTH/8, HEIGHT));
        this.rightContent = rightContent;

        repaint();
        revalidate();
    }

    public void setNorthContent(JPanel northContent) {
        northContent.setPreferredSize(new Dimension(WIDTH, menuHeight));
        this.northContent = northContent;

        repaint();
        revalidate();
    }

    public void setSouthContent(JPanel southContent) {
        southContent.setPreferredSize(new Dimension(WIDTH, HEIGHT/4));
        this.southContent = southContent;

        repaint();
        revalidate();
    }



    public static void main(String[] args){
    // TODO: may change model placement, here for testing atm
    ScrabbleModel model = new ScrabbleModel();
    model.startGame();
    }
}
