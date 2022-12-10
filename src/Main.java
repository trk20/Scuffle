import Model.ReadXMLFile;
import Model.ScrabbleModel;
import Views.DebugView;
import Controllers.OptionPaneHandler;
import Views.ScrabbleFrame;

import java.io.FileNotFoundException;

import static Views.DebugView.DEBUG_VIEW;

/**
 * The main executable for the scrabble project.
 * Takes care of setting up the views and model initially.
 *
 * @author Alex and Vladimir
 * @version Dec-09-2022
 */
public class Main {
    public static void main(String[] args)  {
        OptionPaneHandler optionPaneHandler = new OptionPaneHandler();
        // TODO: may change model placement, here for testing atm
        ScrabbleModel model = new ScrabbleModel(optionPaneHandler.getNewPlayers());
        model.addModelListener(optionPaneHandler);
        ScrabbleFrame frame = new ScrabbleFrame(model);
        //Verify the file entered is valid
        boolean valid = false;
        while(!valid){
            String xmlFile = optionPaneHandler.getFilename();
            if (!xmlFile.equals("")) {
                    ReadXMLFile xmlReader = new ReadXMLFile(model, xmlFile);
                    try{
                        xmlReader.read();
                        valid = true;
                    } catch (RuntimeException e){
                        valid  =false;
                    }
            }else{
                //User doesn't want to use their own configuration file, use the default board config file
                ReadXMLFile xmlReader = new ReadXMLFile(model, "boardConfig.xml");
                try{
                    xmlReader.read();
                    valid = true;
                } catch (RuntimeException e){
                    valid =false;
                }

            }

        }

        // Text view for debug outputs
        DebugView debug;
        if(DEBUG_VIEW) {
            debug = new DebugView(model);
            debug.listenToControllers(model.getDebugControllers());
        }

        model.startGame();
    }
}
