package Model;

import Controllers.OptionPaneHandler;
import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileNotFoundException;

/**
 * Class that is used to read the XML configuration file for custom premium tile placement.
 * This is done using the DOM parser.
 *
 * The XML document needs to enclose the premium tiles in the following tag:
 *
 * <tile>
 *         <id>102</id>
 *         <row>2</row>
 *         <col>2</col>
 *         <type>2L</type>
 * </tile>
 *
 * the tag is "tile" and the tag consists of id, row, col and type (the premium square: 2L,3L,2W,3W) attributes
 *
 * @author Vladimir Kovacina
 */

public class ReadXMLFile {

    private ScrabbleModel model;
    private String filename;

    public ReadXMLFile(ScrabbleModel model, String filename){
        this.model = model;
        this.filename = filename;


    }

    /**
     * Method used to read the XML file and set the premium squares
     *
     * @author Vladimir Kovacina
     */
    public void read()  {

        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbFac.newDocumentBuilder();
            Document doc = db.parse(xmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("tile");

            model.getBoard().setDefaultBoard();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    int x  = Integer.parseInt(element.getElementsByTagName("col").item(0).getTextContent());
                    int y = Integer.parseInt(element.getElementsByTagName("row").item(0).getTextContent());
                    String type = element.getElementsByTagName("type").item(0).getTextContent();
                    //Check that the coordinates of the tiles are in the board bounds and not on the start tile
                    if(x < ScrabbleModel.BOARD_SIZE && y < ScrabbleModel.BOARD_SIZE && x >= 0 && y>= 0 &&
                            x != Board.START_TILE_POINT.x && y != Board.START_TILE_POINT.y) {
                        model.getBoard().setXMLPremiumTiles(x, y, type);
                    }

                }
            }
        } catch (Exception e) {
            //e.printStackTrace();
            OptionPaneHandler optionPaneHandler = new OptionPaneHandler();
            optionPaneHandler.displayError("Invalid File");
            throw new RuntimeException("INVALID FILE");
        }

    }

}
