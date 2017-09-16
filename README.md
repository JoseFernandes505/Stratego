# Stratego

COP 3252 - Stratego - Jose Fernandes
This game was created as an assignment for "Internet Programming With Java", entirely in, as might be a little obvious, Java

## Important Run Information
For the Save/Load functionality to be able to run property, there must be an empty folder named "saves" in the same directory which the jar is being run from.

For setting up a test I would recommend setting up a random board then saving it so you don't have to set up a new board every time.


## General Description
This is an application that emulates the traditional board game, Stratego. In it you have  to try to capture either all your enemy's pieces, or their flag. Stronger pieces capture weaker pieces, and all of your opponents pieces are hidden from you. The game is over when one player has no more pieces that can move. 


## Setting Up a Game
During Setup, the Blue team sets up on the bottom 4 rows of the board, while the Red team sets up on the top 4 rows. The area in which you are not allowed to place pieces are the red tiles (and the blue lakes). To set up pieces, click the button on the panel on the right of the piece you want, then click the tile on the appropriate side of the board which you want your token to fill. If you place a new token over the old token, it will replace the previous token. You must keep placing tokens until you have no more. The number of tokens left is designated by the number on the buttons.



## General Use

### From the Main menu you can:
Begin a new game, which will start you on an empty board to fill up with all the preset tokens.

Go to the Settings panel, and from there choose either the Standard rankings or the reversed rankings, along with changing the Frame size (resizing is disabled) to one of the three options.

Load in a saved game, which will allow you to start a game from a position that was saved into the save folder from a previous save session (obviously impossible if no save exists)

Hit the Quit button to exit the application (This is the only way to exit, as the X on the frame is disabled).

### From the Game panel you can:
Use the toolbar, which in itself has 3 different options, Save, Load, and Quit

Save allows you to create a file which contains the board data of the current game that you're playing, allowing you to pick up from teh same place later.

Load allows you to load in a previously saved game. This will discard your currently loaded game. It will not be saved.

Quit allows you to exit the game panel and return back to the main menu. It is the only way to return from the main menu, and from the exit the application.

You can also view how many of your pieces are left on the board by using the tabs on the right side of the board to view your Board Pieces and your Graveyard (This feature is only available once you are done setting up your pieces)



## Ranking Types
There are two ranking types in Stratego that are commonly used, which in this application the one that is Standard is the one which I grew up with, that being, the rankings going from 2 being the weakest to 10 being the Strongest (i.e.: a 10 (Marshal) would defeat a 3 (Miner) ). There is however, another commonly used ranking system, which for these purposes, is refered to as the Reversed ranking, with 1 being the strongest and 9 being the weakest (i.e.: a 1 (Marshal) would defeat a 8 (Miner) ).



## Extra Features
Many of these have already been mentioned, either extensively or in passing, but here they are in list format:
	-Save/Load functionality
	-Ability to switch between Standard and Reversed rankings
	-Option to resize between 3 different frame sizes
	-Dashboard with current and captured pieces
	-Color coded setup area
