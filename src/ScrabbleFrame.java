import javax.swing.*;
import java.awt.*;

public class ScrabbleFrame extends JFrame{

    private JPanel centerContent;
    private JPanel leftContent;
    private JPanel rightContent;
    private JPanel northContent;
    private JPanel southContent;

    private JPanel centerContentContainer;
    private JPanel leftContentContainer;
    private JPanel rightContentContainer;
    private JPanel northContentContainer;
    private JPanel southContentContainer;

    private final int WIDTH = 1280;
    private final int HEIGHT = 720;


    public ScrabbleFrame() throws HeadlessException {
        super("Scrabble");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());


        centerContent = new JPanel();
        leftContent = new JPanel();
        rightContent = new JPanel();
        southContent = new JPanel();
        northContent = new JPanel();


        rightContentContainer = new JPanel();
        rightContentContainer.setMaximumSize(new Dimension(300, HEIGHT));


        centerContentContainer = new JPanel();
        centerContentContainer.add(centerContent);
        centerContentContainer.setMaximumSize(new Dimension(WIDTH, HEIGHT));

        leftContentContainer = new JPanel();
        leftContentContainer.setMaximumSize(new Dimension(200,HEIGHT));

        northContentContainer = new JPanel();
        northContentContainer.setMaximumSize(new Dimension(WIDTH, 50));

        southContentContainer = new JPanel();
        southContentContainer.setMaximumSize(new Dimension(WIDTH, 200));
        southContentContainer.setLayout(new FlowLayout());

        // Intialize Default Content
        JPanel tempCenterContent = new JPanel();
        tempCenterContent.setBackground(Color.WHITE);
        tempCenterContent.add(new JLabel("Board"));
        setCenterContent(tempCenterContent);

        JPanel tempLeftContent = new JPanel();
        tempLeftContent.setBackground(Color.green);
        tempLeftContent.add(new JLabel("Scores"));
        setLeftContent(tempLeftContent);

        JPanel tempNorthContent = new JPanel();
        tempNorthContent.setBackground(Color.blue);
        tempNorthContent.add(new JLabel("Menu"));
        setNorthContent(tempNorthContent);

        JPanel tempSouthContent = new JPanel();
        tempSouthContent.setBackground(Color.YELLOW);
        tempSouthContent.add(new JLabel("Hand"));
        setSouthContent(tempSouthContent);

        JPanel tempRightContent = new JPanel();
        tempRightContent.setBackground(Color.CYAN);
        tempRightContent.add(new JLabel("Turns"));
        setRightContent(tempRightContent);



        this.add(centerContentContainer, BorderLayout.CENTER);
        this.add(leftContentContainer, BorderLayout.WEST);
        this.add(northContentContainer, BorderLayout.NORTH);
        this.add(southContentContainer, BorderLayout.SOUTH);
        this.add(rightContentContainer, BorderLayout.EAST);

        this.setSize(WIDTH, HEIGHT);
        this.setVisible(true);

    }

    public void setCenterContent(JPanel centerContent) {
        centerContent.setPreferredSize(centerContentContainer.getMaximumSize());

        this.centerContent = centerContent;
        centerContentContainer.add(this.centerContent);
    }

    public void setLeftContent(JPanel leftContent) {
        leftContent.setPreferredSize(leftContentContainer.getMaximumSize());
        this.leftContent = leftContent;
        leftContentContainer.add(this.leftContent);

        repaint();
        revalidate();
    }

    public void setRightContent(JPanel rightContent) {
        rightContent.setPreferredSize(rightContentContainer.getMaximumSize());

        this.rightContent = rightContent;
        rightContentContainer.add(this.rightContent);
        repaint();
        revalidate();
    }

    public void setNorthContent(JPanel northContent) {
        northContent.setPreferredSize(northContentContainer.getMaximumSize());

        this.northContent = northContent;
        northContentContainer.add(this.northContent);
        repaint();
        revalidate();
    }

    public void setSouthContent(JPanel southContent) {
        southContent.setPreferredSize(southContentContainer.getMaximumSize());

        this.southContent = southContent;
        southContentContainer.add(this.southContent);
        repaint();
        revalidate();
    }



    public static void main(String[] args) {
        ScrabbleFrame frame = new ScrabbleFrame();

    }
}
