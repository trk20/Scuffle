import java.util.*;


/**
 * A class handling the board tiles
 *
 * @author Vladimir Kovacina
 * @version 1.0
 */


public class BoardTile {

    private char letter;
    private boolean taken;
    private final String BLANK = "B";
    private final String LETTER_MULTI = "LM";
    private final String WORD_MULTI = "WM";
    private String type;

    public BoardTile(String type){
        taken = false;
        if(type.equals(BLANK)){
            this.type = BLANK;
        }else if (type.equals(LETTER_MULTI)){
            this.type = LETTER_MULTI;
        }else{
            this.type = WORD_MULTI;
        }
    }

    public void setLetter(char letter){
        this.letter = letter;
    }
    public char getLetter(){
        return letter;
    }
    public String getType(){
        return type;
    }

}
