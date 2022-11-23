# 3110 Group Project: Scrabble
Version: Milestone 3
## Table of contents
### [Rule set](#rule-set)
### [Design](#design)
### [Missing Features and bugs](#missing-features-and-bugs)

# Rule set 
Rules adapted from [hasbro](https://scrabble.hasbro.com/en-us/rules)

One change in our ruleset compared to the hasbro version is that the game will automatically end once there are no more letters
left inside the draw pile.
### Setup:
- There are 100 tiles in the letter bag (Unfinished until [M3](#future-milestones))
- One game board
- Four racks (up to four players can play)
- Choose a dictionary ([Choice not implemented](#future-milestones))
  - Dictionary used: Collins Scrabble Words (2019)
- Choose starting player, by drawing letters. Closest letter to A (alphabetically) starts. Blanks beat all other letters.
    - [Work in progress](#milestone-1): currently, the order is determined by the name input order.
## Game Loop:
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
9. The game ends when all letters have been drawn.

## Scoring:
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

# Design
## Controllers
### Interface, SController:
Identifies controller, allows to attach/notify controller listeners.
All controllers are SControllers (exception: MenuController).
### Class, BoardTileController:
BoardTileController is a TurnController that enables clicking on a tile in the board to select them.
Each tile in the board has its own controller. 
Raises a C_BoardClickEvent when the tile is clicked on.
### Class, HandTileController:
HandTileController is a TurnController that enables clicking on tiles in hand to select them. 
Raises a TileClickEvent when that happens.
### Class, MenuController:
This class is used to handle the action that is selected from the Menu.
This controller class implements the ActionListener and therefore overrides the actionPerformed method to determine what to do when a MenuItem is selected.
This class is used to implement the MVC design pattern for the Menu in the Scrabble game.
### Class Model.TurnActionController:
This class handles the button presses from the panel itself. Currently, this class also listens for board click events in order
to save the origin point. Therefore, sending complete context for placing a word to the model.
When sending actions to the model
### Abstract Class, TurnController:
Extended by any controller that is used to control the flow of a turn.
These controllers are disabled on AI turns to prevent humans from playing for them.
### Class, OptionPaneHandler:
This class focuses on prompting and parsing the user input and ensuring that the input is in the desired format.
This class follows a pattern of prompting/scanning method and a verifying method. The prompter runs in a loop
and everytime a user's input is entered, the verifier is called to verify the input and if it is valid,
the prompter returns the value.
Can also act as a model listener to invalid placement errors.

## Model
### Interface, SModel:
Identify models, allows to attach/notify model listeners.
All models which can be listened to are SModels. 
Currently: Player, ScrabbleModel are SModels; 
possibly more in the future for looser coupling.
### Class, AIPlayer:
This class handles the choice and placement of words by AI players.
It contains methods to find possible words, and to "play" - selecting what tiles it will use and placing them at the correct location.
### Class, Board:
This class handles the internal representation of the board's current state. 
It contains methods to place on the board, and get the resulting score.
Validation is delegated to the BoardValidator class.
Placing assumes there was a successful validation for the placement.
### Class, BoardTile
This class is used to handle the tiles in the Board.
The BoardTile Class has a Type (internal enum), a Letter, and a position.
The Type enum is used to assign types to the BoardTiles (premium tile types, or no type). 
Since the types are constant we decided to put them in an enum to avoid accidentally assigning a wrong type.
The BoardTile also keeps track of its location on the board,
using integers x and y to store its grid coordinates.
### Class, BoardValidator:
Handles tile placement validation for Board models, 
Indicates results of a validation through its Status enum. 
Should not be able to modify the board!
### Record, BoardWord
Record storing the tiles forming a word on the board.
Only used to store information about identified words, 
and to test if its components are equal when looking for new words, record is ideal.
The only thing overridden is the toString method (for debugging purposes only).
### Class, Dictionary Handler
Simple class that contains a list of dictionary words, and has a method to verify if a word is a dictionary word.
### Class, DrawPile
- Uses a list to contain a group of Tiles, can be shuffled to simulate a random draw order
- Cannot use a set, there has to be multiple copy to have fluctuating odds for each tile
- Tiles can be added back in (for discarding) and drawn from the pile (to remove them)
### Class, Grid2DArray
Utility class, Handles accesses to a 2D (grid shaped) collection of type T.
Allows to pass Point objects for 2D indexing.
Internally, it's represented with array lists, within an array list.
### Class, Hand
- Uses a list of tiles to hold up to 7 tiles
    - If possible, always fills up to the max (as long as there are tiles to draw), otherwise alerts model
- Allows to use tiles, or see tiles
- When tiles are used, they disappear from the hand. 
Other calling objects have to take care of putting them somewhere else.
### Enum, Letter
- Has information on frequency of appearance, a display character, and a point value.
### Class, Player
- Can add to their own points, or display them
- Can play letters, or discard them
- Cna display their info (name, score, hand information)
### Class, ScrabbleModel
This class handles running the game and delegating tasks to other classes. 
This class follows the model pattern from the MVC design pattern.
### Class, Tile 
Represents a tile in the scrabble game.
Stores the score of its initial letter.
The letter can be changed if the tile was originally a blank (0 score),
but never the score.

## ScrabbleEvents
### Event Interfaces
Event interfaces typically have nothing in them, 
they serve as labels for what the event represents.
### Event Records
Event Records are concrete events that can be raised.
They contain information that event listeners can grab 
(as part of their record parameters). 
Implements at least one event interface to label their type.
### ModelEvents vs ControllerEvents
The two main categories of events are events sent by models (classes implementing SModel),
or events sent by controllers (events implementing SController).
### Listeners
Listeners are interfaces that listen for a specific event type.
Main ones are Model, and SController listeners. 
Other listeners have default code that ignores all events they would typically receive 
except for the events they listen to.

## Views
### Class, BoardTileView:
View to display tiles on the board, occupied or not.
Has multiple style "states":
State 0 (on creation),
State 1 (empty styled tile),
State 2 (occupied tile).
### Class, BoardView:
View containing the main board where words will be played.
Updates on BoardChangeEvents to display played words.
### Class, DebugView:
View that can be partially, or entirely disabled. 
Handles both model and controller events to detect when they happen, and what info they pass.
Can be extended as needed when debugging.
### Class, HandTileView:
HandView is responsible for displaying information about individual Tile objects in the hand.
Displays Tile's letters. 
Also implements a mouse based controller to allow selecting the tiles.
### Class, HandView:
HandView is responsible for displaying information about modeled Hand objects. 
Displays held tiles, shows which ones are selected, and in what order.
Uses 2 JPanels, one on the top (for selected tiles), and one on the bottom (for unselected tiles).
Handles TileSelectEvents from model to identify which tiles get selected/unselected.
### Class, ScrabbleFrame:
TODO Kieran
### Class, MenuView:
This class is used to create the GUI for the Menu, this is done by extending JMenuBar. The menu name and menu items are created in this class and all the actionListeners are initialized.  
This class is the view part of the MVC design pattern for the GUI of the Menu in the Scrabble game.
We chose to use the MVC pattern since it is easy to implement and makes it much easier to refactor our code.
### Class, ScoreView:
This class is used to create the GUI for the Score panel (left side of the Scrabble game GUI). This class extends JPanel (since it will be a JPanel when it is added to the main JFrame) and implements ModelListener (so that it gets notified when a player plays his turn).
This class also uses the MVC design pattern, it is the view part of the pattern, it also uses ScrabbleEvents, which are passed once a change has occured in the Model.
These events contain enough information to update the View with the required information (in this case the new score of the players).
### Abstract Class, TileView:
Declares shared methods that TileViews re-use. 
Mostly for styling tiles representing a letter.
Letter tiles use a 3x3 grid of JLabels, 
and set the middle one with the letter, 
the bottom right one with a score.
### Class, TurnActionPanel:
This class handles the view for the right content of the game. It contains a button for signalling a place action,
discard action, the direction for the place action(Horizontal or Vertical) and a skip turn button. The main design choice
is to have a seperate panel for each sections of button. This allows for easy position manipulation should it be needed in the future  .

# Missing Features and bugs
## Milestone 1
- End game score adjustments
  - Remove remaining letters' point value from score at the end of the game
  - Determine winner -> Have an end "screen"
    - Tie breaking
- Have a way to end the game
- Determine player order by drawing from the letter bag
- Special scoring -> BINGO
## Milestone 2
- GUI user feedback: indicate why the user cannot place, a tile.
  - Have instructions / a tutorial on how to use the GUI
- Some GUI features not implemented, Skip button not working, menu save/loard not working (M4 feature)
  - (low priority because discarding 0 tiles can be used to skip your turn already)
- Class diagrams, sequence diagrams not fully up to date
- Some model tests missing for the newer classes
  - Some existing tests are outdated due to evolving class interfaces

## Future Milestones 
Note: these do not include all future features yet
- Add blank tiles (Milestone 3)
- Premium tile scoring (Milestone 3)
- Choose from a set of dictionaries before starting (Low priority)
- Machine learning A.I.!!! (After Milestone 4)
