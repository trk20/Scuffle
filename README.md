# 3310 Group Project: Scrabble
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
3. Premium Letter Squares: (Scoring unaffected for now! [M3](#future-milestones))
   - Letter score: 2L is double letter score, 3L is triple letter score
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
- TODO
## Missing Features and bugs
### Milestone 1
- Documentation
    - Class UML
    - Important Sequences
    - Design section of README
- Backtracking if the word you chose cannot be placed
  - Currently, locks you into the choice until you manage to place it (potentially blocking your game)
- Scoring/announcing scores not implemented yet
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