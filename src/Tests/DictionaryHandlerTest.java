package Tests;

import Model.DictionaryHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DictionaryHandlerTest {
    private DictionaryHandler dictionary;

    @BeforeEach
    void setUp() {
        dictionary = new DictionaryHandler();
    }

    @AfterEach
    void tearDown() {
    }

    /**
     * Test some words starting with the letter A
     * in the collins 2019 scrabble dictionary (only valid dictionary in M2).
     *
     * @author Alex
     */
    @Test
    void isValidAWord() {
        assert(dictionary.isValidWord("AA"));
        assert(dictionary.isValidWord("AAH"));
        assert(dictionary.isValidWord("AAHING"));
        assert(dictionary.isValidWord("ABOULIA"));
        assert(dictionary.isValidWord("AARDVARK"));
        assert(dictionary.isValidWord("ABOLITION"));
        assert(dictionary.isValidWord("ABACTERIAL"));
        assert(dictionary.isValidWord("ABRACADABRA"));
        assert(dictionary.isValidWord("ABRACADABRAS"));
        assert(dictionary.isValidWord("ABNORMALITIES"));
        assert(dictionary.isValidWord("ABDOMINOPLASTY"));
        assert(dictionary.isValidWord("ABOMINABLENESS"));
        assert(dictionary.isValidWord("ABIOGENETICALLY"));
    }

    /**
     * Test some words starting with the letter A
     * in the collins 2019 scrabble dictionary (only valid dictionary in M2).
     *
     * @author Alex
     */
    @Test
    void isValidZWord() {
        assert(dictionary.isValidWord("ZZZ"));
        assert(dictionary.isValidWord("ZZZS"));
        assert(dictionary.isValidWord("ZYZZYVAS"));
        assert(dictionary.isValidWord("ZYMOMETERS"));
        assert(dictionary.isValidWord("ZYMOLOGIC"));
        assert(dictionary.isValidWord("ZYGOPHYTES"));
        assert(dictionary.isValidWord("ZYGANTRUMS"));
        assert(dictionary.isValidWord("ZUCHETTAS"));
        assert(dictionary.isValidWord("ZOOPHOBOUS"));
        assert(dictionary.isValidWord("ZOONOMIST"));
        assert(dictionary.isValidWord("ZOOLATER"));
        assert(dictionary.isValidWord("ZOOGAMOUS"));
        assert(dictionary.isValidWord("ZONER"));
    }

    /**
     * Test some fake words to get invalid returns
     * in the collins 2019 scrabble dictionary (only valid dictionary in M2).
     *
     * @author Alex
     */
    @Test
    void isNotValidFakeWord() {
        assert(!dictionary.isValidWord("1"));
        assert(!dictionary.isValidWord("5612345"));
        assert(!dictionary.isValidWord("!@#%#"));
        assert(!dictionary.isValidWord("1134erfre32"));
        assert(!dictionary.isValidWord("KENDRICK"));
        assert(!dictionary.isValidWord("FATALITOUS"));
        assert(!dictionary.isValidWord("TNHMW"));
    }

    /**
     * Test case sensitivity (should not matter)
     * @author Alex
     */
    @Test
    void validWordIsNotCaseSensitive(){
        // Previously valid words
        assert(dictionary.isValidWord("ABoLITiON"));
        assert(dictionary.isValidWord("ABACtERIAL"));

        assert(dictionary.isValidWord("zuchettas"));
        assert(dictionary.isValidWord("zooPHOBouS"));

        // Previously invalid words
        assert(!dictionary.isValidWord("kenDrIcK"));
        assert(!dictionary.isValidWord("FaTalitous"));
    }

    /**
     * Test effect of spaces in the string, will invalidate words with extra spaces.
     * Does not trim ends, nor does it ignore spaces between two strings of letters.
     * @author Alex
     */
    @Test
    void spaceInvalidatesWord(){
        assert(!dictionary.isValidWord("                       ABoLITiON"));
        assert(!dictionary.isValidWord("ABACtERIAL                    "));
        assert(!dictionary.isValidWord("   zuchettas   "));

        assert(!dictionary.isValidWord("z o o P H O B o u S"));
    }
}