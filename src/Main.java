import Model.ScrabbleModel;
import Views.ScrabbleFrame;

/**
 * The main executable for the scrabble project.
 * Takes care of setting up the views and model initially.
 *
 * @author Alex
 * @version NOV-11
 */
public class Main {
    public static void main(String[] args){
        // TODO: may change model placement, here for testing atm
        ScrabbleModel model = new ScrabbleModel();

        ScrabbleFrame frame = new ScrabbleFrame(model);

        model.startGame();
    }
}
