package Views;

import Model.ScrabbleModel;

import javax.swing.*;
import java.awt.*;


public class ScrabbleFrame extends JFrame{

    private JPanel centerContent;
    private JPanel leftContent;
    private JPanel rightContent;
    private JPanel southContent;

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;

    private final ScrabbleModel model;


    public ScrabbleFrame(ScrabbleModel model) throws HeadlessException {
        super("Scrabble");
        this.model = model;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        //Current idea is to have containers wrap around the content to ensure sizing
        centerContent = new JPanel();
        leftContent = new JPanel();
        rightContent = new JPanel();
        southContent = new JPanel();
//        menuBar = new JPanel();


        setDefaultContent();

        this.add(centerContent, BorderLayout.CENTER);
        this.add(leftContent, BorderLayout.WEST);
        this.add(southContent, BorderLayout.SOUTH);
        this.add(rightContent, BorderLayout.EAST);



        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);

    }

    public void setDefaultContent(){
        JPanel tempCenterContent = new JPanel();
        tempCenterContent.setBackground(Color.WHITE);
        tempCenterContent.add(new JLabel("Model.Board"));
        setCenterContent(tempCenterContent);

        setMenu(new MenuView(model));
        setRightContent(new TurnActionPanel(model));
        setLeftContent(new ScoreView(model));
        setSouthContent(new HandView(model));
        setCenterContent(new BoardView(model));
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

    public void setMenu(JMenuBar menu) {
        this.add(menu, BorderLayout.NORTH);

        repaint();
        revalidate();
    }

    public void setSouthContent(JPanel southContent) {
        southContent.setPreferredSize(new Dimension(WIDTH, HEIGHT/4));
        this.southContent = southContent;

        repaint();
        revalidate();
    }



}
