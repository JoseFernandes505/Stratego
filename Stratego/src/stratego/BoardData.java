//Created by Jose Fernandes
package stratego;

import java.io.Serializable;

public class BoardData implements Serializable{
	
	/*
	 * 
	 * 		VARIABLES
	 * 
	 */
	
	//Board array
	private Tile[][] board;
	
	//Width and height of board
	private int width = 10;
	private int height = 10;
	
	//boolean to decribe turn
	private boolean turn = false;
	private boolean currentlySettingUp = true;
	private boolean gameWon = false;
	private boolean blueWon = false, redWon = false, totalDraw = false;
	private boolean inBattle = false, betweenTurns = false;
	
	//Game Settings
	private boolean reversedPieces = false;

	/*
	 * 	Array that holds the number of initial pieces of each rank.
	 * 	They are organized by the rank so that it equals the index.
	 *  Translates to 
	 * 	
	 *  flagNum = 1
	 *  marshalNum = 1
	 *  generalNum = 1
	 *  colonelNum = 2
	 *  majorNum = 3
	 *  captainNum = 4
	 *  lieutenantNum = 4
	 *  sergeantNum = 4
	 *  minerNum = 5
	 *  scoutNum = 8
	 *  spyNum = 1
	 *  bombNum = 6
	 * 
	 */
	private int[] initialNums = {  1, 1, 8, 5,
								   4, 4, 4, 3,
								   2, 1, 1, 6  };
	/*
	 * 
	 * 		CONSTRUCTOR & INITIALIZER FUNCTION
	 * 
	 */
	
	public BoardData( boolean reverse ){
		
		reversedPieces = reverse;
		board = new Tile[width][height];
		buildBoard();
		
	}
	
	
	public void buildBoard(){
		
		//Initializes all the tiles in the board ass passable and empty
		for(int i = 0; i < width; i++){
			
			for(int j = 0; j < height; j++){
				board[i][j] = new Tile(true, null);
			}
			
		}
		
		//Sets the two lake positions as impassable
		for(int i = 4; i < 6; i++){
			
			for (int j = 2; j < 4; j++){
				board[i][j] = new Tile(false, null);
			}
			
			for (int j = 6; j < 8; j++){
				board[i][j] = new Tile(false, null);
			}			
			
		}
		
	}
	
	/*
	 * 
	 * 		BOARD DATA MODIFICATION
	 * 
	 */
	
	//Moves the token on the board
	public boolean moveToken(int toX, int toY){
		//Holder for the x,y coordinates of the token
		int fromX = -1, fromY = -1;
				
		//Forloop to see which piece is the one that's currently moving
		for(int i = 0; i < width; i++){
			for(int j = 0; j< height; j++){
				//If the piece has a token, and it is currently moving, then it is set to be the move token
				if(getTile(i, j).getToken() != null && getTile(i, j).getToken().isMoving()){
					fromX = i;
					fromY = j;
				}
					
			}
		}
		
		//Checks that the from values were changed
		if(fromX == -1 && fromY == -1) {
			return false;
		}
		
		//Sets the tile to the token which is moving and removes the token from the original tile
		getTile(toX, toY).addToken( getTile(fromX, fromY).getToken() );
		getTile(fromX, fromY).removeToken();
		
		//Clears the board's passable/moving data
		clearBoardData();
		
		return true;
	}
	
	//Highlights the tiles in moveable range of a token
	public void setTilesPassable(Token tok, int x, int y){
		
		clearBoardData();
		
		tok.startMoving();
		
		//Gets the range of the token
		int range = tok.getRange();
		boolean leftMoveBlocked = false, rightMoveBlocked = false, upMoveBlocked = false, downMoveBlocked = false;
		
		//Checks for available moves 
		for(int i = 0; i < range; i++){
			//Checks for available moves on the right
			if( (x + i + 1) < width ){
				if(!rightMoveBlocked)
					rightMoveBlocked = checkDirection(rightMoveBlocked, x + i + 1, y, tok.getTeam());				
			}
			//Checks for available moves on the left
			if(x - i - 1 >= 0){
				if(!leftMoveBlocked)
					leftMoveBlocked = checkDirection(leftMoveBlocked, x - i - 1, y, tok.getTeam());
			}
			//Checks for available moves above
			if(y + i + 1 < height ){
				if(!upMoveBlocked)
					upMoveBlocked = checkDirection(upMoveBlocked, x , y + i + 1, tok.getTeam());	
			}
			//Checks for available moves below
			if(y - i - 1 >= 0 ){
				if(!downMoveBlocked)
					downMoveBlocked = checkDirection(downMoveBlocked, x , y - i - 1, tok.getTeam());				
			}
		}
	}
	
	//Checks if the current tile should be in range of a specific set of parameters
	public boolean checkDirection(boolean direction, int x, int y, boolean tokenTeam){
		//Checks if the tile is passable, if not, returns that it's blocked
		if( !getTile( x, y).isPassable() ){
			return true;
		}
						
		//If it still isn't blocked,checks if there is a token on the tile
		if(!direction && getTile( x, y).isPassable()){
			//If there's a token, sets that one token in range but then sets its motion
			//as blocked so it does not continue
			if( getTile( x, y).getToken() != null ){
				if( getTile(x, y).getToken().getTeam() == tokenTeam){
					getTile( x, y).setInRange( false );
				} else if( getTile(x, y).getToken().getTeam() != tokenTeam) {
					getTile( x, y).setInRange( true );
				}
				return true;
			
			//Otherwise the tile is simply set in range
			} else {
				getTile( x, y).setInRange( true );
			}
			
		}
		
		//If none of the things returned that it is blocked, return that it isn't
		return false;
	}
	
	public void doBattle( int defX, int defY , int attX, int attY){
		
		//Checks that the from values were changed
		if(attX == -1 && attY == -1) {
			clearBoardData();
			return;
		}
		
		
		//Does the actual battle
		int result = getTile(attX, attY).getToken().doBattle( getTile(defX, defY).getToken() );
		
		if( result > 0 ) {
			getTile(defX, defY).removeToken();
			getTile(defX, defY).addToken( getTile(attX, attY).getToken() );
			getTile(attX, attY).removeToken();
			
		} else if ( result < 0 ) {
			getTile(attX, attY).removeToken();
		} else if ( result == 0 ) {
			getTile(attX, attY).removeToken();
			getTile(defX, defY).removeToken();
		}
		
		clearBoardData();
	}
	
	//Switches the turn
	public void switchTurn(){
		
		boolean blueWin = false, redWin = false, draw = false;
		
		if( !settingUp() ){
			//Checks to see if anyone has won yet
			blueWin = checkWinConditions( true );
			redWin = checkWinConditions( false );
			draw = checkWinConditions();
		}
		
		//If so, sets the board as won
		if(draw){
			gameWon = true;
			totalDraw = true;
		} else if(blueWin){
			gameWon = true;
			blueWon = true;
		} else if(redWin){
			gameWon = true;
			redWon = true;
		}
		
		//If no win, keeps going
		if(!gameWon){
			turn = !turn;
		}
	}
	
	//Adds a new token to the board at the specified location
	public boolean setToken(int x, int y, Token tok){
		if( tok.getTeam() ){
			if( x < 4 ){
				board[x][y].addToken( tok );
				return true;
			}
			return false;
		} else {
			if( x > 5 ){
				board[x][y].addToken( tok );
				return true;
			}
			return false;
		}
		
		
	}

	
	/*
	 * 
	 * 		HELPER FUNCTIONS
	 * 
	 */
	
	//Clears the board's range and movement data
	public void clearBoardData(){
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
								
				//Sets all tiles to out of range
				getTile(i, j).setInRange(false);
				
				//Sets all tokens to stop moving
				if(getTile(i, j).getToken() != null){
					getTile(i, j).getToken().stopMoving();
				}
			}
		}
	}
	
	//Checks if one specific team has won
	public boolean checkWinConditions(boolean team){
		
		boolean moveableLeft = false, flagLeft = false;
		
		for(int i = 0; i < getWidth(); i++){	
			for(int j = 0; j < getHeight(); j++){
				
				if(getTile(i,j).getToken() != null && getTile(i, j).getToken().getTeam() == team ){					
					//Checks if any pieces are able to move
					if( getTile(i, j).getToken().getRange() > 0 && checkIfMobile( i , j ) ){
						moveableLeft = true;						
					}
					//Checks if the flag is still there
					if( getTile(i, j).getToken().getRank() == 0 ){
						flagLeft = true;						
					}
				}
				
			}
		}
		//If either isn't true, then the game is over
		if( !moveableLeft || !flagLeft ){
			return true;
		}
		
		return false;
	}
	
	//Checks to see if there has been a draw based on immobility
	public boolean checkWinConditions(){
		
		boolean moveableLeft = false;
		
		for(int i = 0; i < getWidth(); i++){	
			for(int j = 0; j < getHeight(); j++){
				//Checks if any pieces are able to move
				if(getTile(i,j).getToken() != null ){					
					if( getTile(i, j).getToken().getRange() > 0 && checkIfMobile( i , j ) ){
						moveableLeft = true;						
					}
				}
				
			}
		}
		
		if( !moveableLeft ){
			return true;
		}
		
		return false;
	}
	
	//Checks if the token at this position is able to move (not just by range, but whether there's stuff in the way)
	public boolean checkIfMobile(int x, int y){
		
		boolean canMove = false;
		
		//Checks tile to the right
		if(x < (width - 1) ){
			if( getTile(x + 1, y).isPassable() && getTile(x + 1, y).getToken() == null) {
				canMove = true;
			}
		}
		
		//Checks tile to the left
		if(x > 0 ){
			if( getTile(x - 1, y).isPassable() && getTile(x - 1, y).getToken() == null) {
				canMove = true;
			}
		}
		
		//Checks tile below
		if(y < (height - 1) ){
			if( getTile(x, y + 1).isPassable() && getTile(x, y + 1).getToken() == null) {
				canMove = true;
			}
		}
		
		//Checks tile above
		if(y < 0 ){
			if( getTile(x, y - 1).isPassable() && getTile(x, y - 1).getToken() == null) {
				canMove = true;
			}
		}
		
		return canMove;
	}
	
	/*
	 * 
	 * 		GETTERS/SETTERS
	 * 
	 */
	
	//Returns the width of the board
	public int getWidth(){
		return width;
	}
	
	//Returns the height of the board
	public int getHeight(){
		return height;
	}
	
	//Returns any one specific tile
	public Tile getTile(int x, int y){
		return board[x][y];
	}
	
	//Returns the current turn
	public boolean getTurn(){
		return turn;
	}
	
	//Checks if the board is in the setup state
	public boolean settingUp(){
		return currentlySettingUp;
	}
	
	//Switches the setup state
	public void switchSetup(){
		currentlySettingUp = !currentlySettingUp;
	}
	
	//Checks if the board is in the won state
	public boolean gameWon(){
		return gameWon;
	}
	
	//Checks if the blue win state
	public boolean blueWin(){
		return blueWon;
	}
	
	//Checks if the red win state
	public boolean redWin(){
		return redWon;
	}
	
	//Checks if there is a draw state
	public boolean draw(){
		return totalDraw;
	}
	
	//Checks if there board is reversed
	public boolean reversedPieces(){
		return reversedPieces;
	}
	
	//Starts the battle state
	public void startBattling(){
		inBattle = true;
	}
	
	//Ends the battle state
	public void stopBattling(){
		inBattle = false;
	}
	
	//Checks the battle state
	public boolean battling(){
		return inBattle;
	}
	
	//Sets between turns
	public void betweenTurns(boolean b){
		betweenTurns = b;
	}
	
	//Checks if between turns
	public boolean betweenTurns(){
		return betweenTurns;
	}
	
	//Checks the initial tokens array to see how many of each there
	//were initially
	public int getInitialRankTokens(int rank){
		return initialNums[rank];
	}
	

}
