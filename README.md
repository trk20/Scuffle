# 3110 Group Project: Scrabble
### Version: Milestone 1

## Rule set [adapted from hasbro](https://scrabble.hasbro.com/en-us/rules)
### Setup:
- There are 100 tiles in the letter bag (Unfinished until [M3](#future-milestones))
- One game board
- Four racks (up to four players can play)
- Choose a dictionary ([Choice not implemented](#future-milestones))
  - Dictionary used: Collins Scrabble Words (2019)
- Choose starting player, by drawing letters. Closest letter to A (alphabetically) starts. Blanks beat all other letters.
    - [Work in progress](#milestone-1): currently, the order is determined by the name input order.
### Game Loop:
1. The first player combines their letters to form a word and 
places it on the board to read either across or down with one letter on the center square. 
Diagonal words are not allowed.
2. Complete your turn by counting and [announcing your score](#milestone-1) for that turn. 
Then draw as many new letters as you played; 
always keep seven letters on your rack, as long as there are enough tiles left in the bag.
3. Play passes to the next player. The second player, and then each in turn, 
adds one or more letters to those already played to form new words. 
All letters played on a turn must be placed in one row across or down the board, to form at least one complete word. 
If, at the same time, they touch others letters in adjacent rows, 
those must also form complete words, crossword fashion, with all such letters. 
The player gets full credit for all words formed or modified on his or her turn.
   - The next player being determined by the person who gave their name after the previous player
4. New words may be formed by:
   - Adding one or more letters to a word or letters already on the board.
   - Placing a word at right angles to a word already on the board. 
    The new word must use one of the letters already on the board or must add a letter to it.
   - Placing a complete word parallel to a word already played so that adjacent letters also form complete words. 
5. No tile may be shifted or replaced after it has been played and scored.
6. Blanks [M3](#future-milestones): The two blank tiles may be used as any letters. 
When playing a blank, you must state which letter it represents. It remains that letter for the rest of the game.
7. You may use a turn to exchange all, some, or none of the letters. 
To do this, choose the discard option at the start of your turn. Choose the letters to discard.
You will add your letters to the bag, then draw the same amount. This ends your turn.
8. Cannot challenge words, as the game validates them for the players.
9. The game ends when all letters have been drawn and one player uses their last letter;
or when all possible plays have been made.
   - Note: second condition no legal moves is a [work in progress](#milestone-1)

### Scoring:
1. The game keeps a tally of each player's score, displaying it after each turn. 
The score value of each letter is indicated by a number next to its character. 
The score value of a blank ([M3](#future-milestones)) is zero.
2. The score for each turn is the sum of the letter values in each word(s) formed or modified on that turn, 
plus the additional points obtained from placing letters on _Premium Squares_ [M3](#future-milestones).
3. Premium Model.Letter Squares: (Scoring unaffected for now! [M3](#future-milestones))
   - Model.Letter score: 2L is double letter score, 3L is triple letter score
   - Word score: 2W is double word score, 3W is triple word score -> counted after premium letter tiles. 
   They can multiply each other
      - Center square counts as 2W
4. Premium tiles only count on the turn they are used the first time. 
On subsequent turns, letters on those tiles count at face values.
5. Blank tiles can still be used to activate premium word tiles.
6. When multiple words are formed in a turn, all are counted 
(with their full premium value if any) [M3](#future-milestones).
7. Bingo: if all 7 tiles are placed at once, score a premium of 50 points [Unimplemented](#milestone-1).
8. Unplaced Letters: At the end of the game, each player's score is reduced by the sum of his or her unplayed letters' values. 
In addition, if a player has used all of his or her letters, 
the sum of the other players' unplayed letters is added to that player's score. [Unimplemented](#milestone-1)
9. The player with the highest final score wins the game. 
In case of a tie, the player with the highest score before adding or deducting unplayed letters wins. 
[Unimplemented](#milestone-1)

## Design
### Draw Pile
- Uses a list to contain a group of letters, can be shuffled to simulate a random draw order
- Cannot use a set, there has to be multiple copy to have fluctuating odds for each letter
- Letters can be added back in (for discarding) and drawn from the pile (to remove them)
### Model.Hand
- Uses a list of letters to hold up to 7 letters
  - If possible, always fills up to the max (as long as there are letters to draw), otherwise alerts model
- Allow to check if the hand contains a subset of letters
- Allows to use letters, or see letters
### Model.Player
- Can add to their own points, or display them
- Can check if they have letters, play letters, or discard them
- Cna display their info (name, score, hand information)
### Model.Letter (enums)
- Has information on frequency of appearence, a display character, and a point value.
- Has static methods to return lists of Letters from strings or vice-versa
### Class Model.TextController:
This class focuses on prompting and parsing the user input and ensuring that the input is in the desired format.
This class follows a pattern of prompting/scanning method and a verifying method. The prompter runs in a loop 
and everytime a user's input is entered, the verifier is called to verify the input and if it is valid,
the prompter returns the value.
### Class Model.ScrabbleModel:
This class handles running the game and delegating tasks to other classes. This class follows the model pattern
from the MVC design pattern. This class is also the main class which starts the game and handles the user's turns as well as
it handles processing and parsing of the user input.  
### Class Model.Board :
This class handles the internal representation of the board's current state. It contains the methods to validate and place words, and to return the score given by a placement.
### Class Model.BoardTile:
This class is used to handle the squares in the Model.Board class.
The Model.BoardTile Class uses two enums, Type enum and Model.Letter enum.
The Type enum is created within the Model.BoardTile Class and it is used to assign types to the BoardTiles. Since the types are constant we decided to put them in an enum to avoid accidentally assigning a wrong type.
The Model.Letter enum is also used in the Model.BoardTile class, each tile wil be able to hold one Model.Letter. Letters were made to be an enum since they are constant and contain values and frequencies of each letter.
The Model.BoardTile also keeps track of its location on the board, using integers x and y to store the appropriate row and column values.

## Missing Features and bugs (see issues)
### Milestone 1
- BUG: checking if player has letters can have issues when using multiple copies of a letter
- End game score adjustments
  - Remove remaining letters' point value from score at the end of the game
  - Determine winner -> Have an end "screen" / print block
    - Tie breaking
- Have a way to end the game
    - Partially implemented. Does not end when there are no valid moves, only when a player empties their hand.
- Determine player order by drawing from the letter bag
- Special scoring -> BINGO
### Future Milestones 
Note: these do not include all future features yet
- Change view/controller to use a GUI (Milestone 2)
- Add blank tiles (Milestone 3)
- Premium tile scoring (Milestone 3)
- Choose from a set of dictionaries before starting (Low priority)
- Machine learning A.I.!!! (After Milestone 4)
