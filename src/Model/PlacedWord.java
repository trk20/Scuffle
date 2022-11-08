package Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Class to consolidate information pertaining to a placed word
 *
 * @author Timothy Kennedy
 * @version 2022-11-6
 */
public class PlacedWord {
    private ArrayList<BoardTile> wordTiles;
    //the tiles that make up the word and its placement

    /**
     * Constructor for a Model.PlacedWord object
     * @author Timothy Kennedy
     *
     * @param tiles the tiles that make up the placed word
     */
    public PlacedWord(List<BoardTile> tiles){
        wordTiles = new ArrayList<>();
        wordTiles.addAll(tiles);
    }

    /**
     * Returns the coordinates of the first tile of the word
     * @author Timothy Kennedy
     *
     * @return the coordinates of the first tile of the word
     */
    private int[] getLocation(){
        return new int[]{wordTiles.get(0).getX(), wordTiles.get(0).getY()};
    }

    /**
     * Returns the whether the word is placed left-to-right
     * @author Timothy Kennedy
     *
     * @return whether the word is placed left-to-right
     */
    private boolean getDirection(){
        return wordTiles.get(0).getX() > wordTiles.get(1).getX();
    }

    /**
     * Returns the list of tiles that make up the word
     * @author Timothy Kennedy
     *
     * @return the list of tiles that make up the word
     */
    public List<BoardTile> getTiles(){
        return wordTiles;
    }

    /**
     * Evaluates whether an object is equal to the placed word
     *
     * @param obj the object to be compared
     * @return whether the object is the same as the placed word
     */
    @Override
    public boolean equals(Object obj) {
        if(obj.getClass() != this.getClass()){
            return false;
        }
        PlacedWord word = (PlacedWord)obj;
        return word.toString().equals(this.toString()) && word.getDirection() == this.getDirection() && Arrays.equals(word.getLocation(),this.getLocation());
    }

    /**
     * Creates and returns a string representation of the placed word
     * @author Timothy Kennedy
     *
     * @return the word contained in the Model.PlacedWord object
     */
    public String toString(){
        String returnString = "";
        for (BoardTile tile: wordTiles) {
            returnString+=tile.getLetter().toString();
        }
        return returnString;
    }
}
