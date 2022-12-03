package Model;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class ReadXMLFile {

    private ScrabbleModel model;
    private String filename;

    public ReadXMLFile(ScrabbleModel model, String filename){
        this.model = model;
        this.filename = filename;


    }
    public void read(){

        try {
            File fXmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("tile");

            model.getBoard().setDefaultBoard();

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                System.out.println("\nCurrent Element :" + nNode.getNodeName());
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    int x  = Integer.parseInt(eElement.getElementsByTagName("col").item(0).getTextContent());
                    int y = Integer.parseInt(eElement.getElementsByTagName("row").item(0).getTextContent());
                    String type = eElement.getElementsByTagName("type").item(0).getTextContent();
                    if(x < ScrabbleModel.BOARD_SIZE && y < ScrabbleModel.BOARD_SIZE && x >= 0 && y>= 0 &&
                            x != Board.START_TILE_POINT.x && y != Board.START_TILE_POINT.y) {
                        model.getBoard().setXMLPremiumTiles(x, y, type);
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
