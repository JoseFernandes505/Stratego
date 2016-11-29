//Created by Jose Fernandes

package stratego;

import java.util.ArrayList;

public class BoardData{
	
	/*
	 * 
	 * 		VARIABLES
	 * 
	 */
	
	//Board array
	private Tile[][] board;
	
	
	//A list of all the inital token values
	public ArrayList<Token> offBoardTokensRed;
	public ArrayList<Token> offBoardTokensBlue;
	
	public ArrayList<Token> currTokensRed;
	public ArrayList<Token> currTokensBlue;
	
	
	//Width and height of board
	private int width = 10;
	private int height = 10;
	
	//boolean to decribe turn
	private boolean turn = true;
	
	//Number of each token	
	private int bombNum = 6;
	private int flagNum = 1;
	private int marshalNum = 1;
	private int generalNum = 1;
	private int colonelNum = 2;
	private int majorNum = 3;
	private int captainNum = 4;
	private int lieutenantNum = 4;
	private int sergeantNum = 4;
	private int minerNum = 5;
	private int scoutNum = 8;
	private int spyNum = 1;
	
	/*
	 * 
	 * 		CONSTRUCTOR & INITIALIZER FUNCTION
	 * 
	 */
	
	public BoardData(){
		board = new Tile[width][height];
		buildBoard();
		
		//TODO Instantiate the lists then keep workin on that shit lol
		
		offBoardTokensRed = new ArrayList<Token>();
		offBoardTokensBlue = new ArrayList<Token>();
		
		currTokensRed = new ArrayList<Token>();
		currTokensBlue = new ArrayList<Token>();
		
		initializeList();
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
	
	public void doBattle( int defX, int defY ){
		
		int attX = -1, attY = -1;
		
		//Forloop to see which piece is the one that's currently moving
		for(int i = 0; i < width; i++){
			for(int j = 0; j< height; j++){
				//If the piece has a token, and it is currently moving, then it is set to be the move token
				if(getTile(i, j).getToken() != null && getTile(i, j).getToken().isMoving()){
					attX = i;
					attY = j;
				}
					
			}
		}
		
		//Checks that the from values were changed
		if(attX == -1 && attY == -1) {
			clearBoardData();
			return;
		}
		
		
		
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
		turn = !turn;		
	}
	
	
	//Initializes the list of initial tokens, to be placed on the board
	public void initializeList(){
		
		for(int i = 0; i < flagNum; i++ ){
			offBoardTokensRed.add( new Token(0, false, 0) );
			offBoardTokensBlue.add( new Token(0, true, 0) );
		}
		
		for(int i = 0; i < bombNum; i++ ){
			offBoardTokensRed.add( new Token(11, false, 0) );
			offBoardTokensBlue.add( new Token(11, true, 0) );
		}
		
		for(int i = 0; i < marshalNum; i++ ){
			offBoardTokensRed.add( new Token(10, false) );
			offBoardTokensBlue.add( new Token(10, true) );
		}
		
		for(int i = 0; i < generalNum; i++ ){
			offBoardTokensRed.add( new Token(9, false) );
			offBoardTokensBlue.add( new Token(9, true) );
		}
		
		for(int i = 0; i < colonelNum; i++ ){
			offBoardTokensRed.add( new Token(8, false) );
			offBoardTokensBlue.add( new Token(8, true) );
		}
		
		for(int i = 0; i < majorNum; i++ ){
			offBoardTokensRed.add( new Token(7, false) );
			offBoardTokensBlue.add( new Token(7, true) );
		}
		
		for(int i = 0; i < captainNum; i++ ){
			offBoardTokensRed.add( new Token(6, false) );
			offBoardTokensBlue.add( new Token(6, true) );
		}
		
		for(int i = 0; i < lieutenantNum; i++ ){
			offBoardTokensRed.add( new Token(5, false) );
			offBoardTokensBlue.add( new Token(5, true) );
		}
		
		for(int i = 0; i < sergeantNum; i++ ){
			offBoardTokensRed.add( new Token(4, false) );
			offBoardTokensBlue.add( new Token(4, true) );
		}
		
		for(int i = 0; i < minerNum; i++ ){
			offBoardTokensRed.add( new Token(3, false) );
			offBoardTokensBlue.add( new Token(3, true) );
		}
		
		for(int i = 0; i < scoutNum; i++ ){
			offBoardTokensRed.add( new Token(2, true, (width > height ? width : height)) );
			offBoardTokensBlue.add( new Token(2, false, (width > height ? width : height)) );
		}
		
		for(int i = 0; i < spyNum; i++ ){
			offBoardTokensRed.add( new Token(1, false) );
			offBoardTokensBlue.add( new Token(1, true) );
		}
	}

	
	/*
	 * 
	 * 		HELPER FUNCTIONS
	 * 
	 */
	
	//Populates the board with a lot of test values
	public void populateTestBoard(){
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				
				if(i < 4 ){
					board[i][j].addToken( new Token(5, false) );
				}
				
				if(i > 5 ){
					board[i][j].addToken( new Token(5, true) );
				}
				
			}
		}
		
		board[4][4].addToken( new Token(11, true, 0) );
		board[4][5].addToken( new Token(3, false) );
		
		board[5][4].addToken( new Token(1, true) );
		board[5][5].addToken( new Token(10, false) );
		
	}
	
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
	
	public ArrayList<Token> getCurrTokens(boolean team){
		if(team){
			return currTokensBlue;
		}
		return currTokensRed;
	}
	
	public ArrayList<Token> getOffBoardTokens(boolean team){
		if(team){
			return offBoardTokensBlue;
		}
		return offBoardTokensRed;
	}
	
	
	/*
	//Sets a token of an inputted rank and team at an X and Y position
	public boolean setToken(int rank, boolean color, int xpos, int ypos){
		
		//Makes sure that the x and y inputs are both within range
		if(xpos >= width || xpos < 0){
			return false;
		}
		if(ypos >= height || ypos < 0){
			return false;
		}
		
		//Makes sure that the tile does not already have a token
		if(board[xpos][ypos] != null){
			return false;
		} 
		
		//Switch statement to set the position as a token of the inputted rank/color
		switch(rank){
			case 0:
				board[xpos][ypos].addToken( new Token("flag", rank, color) );
				return true;
			case 1:
				board[xpos][ypos].addToken( new Token("spy", rank, color) );
				return true;
			case 2:
				board[xpos][ypos].addToken( new Token("scout", rank, color) );
				return true;
			case 3:
				board[xpos][ypos].addToken( new Token("miner", rank, color) );
				return true;
			case 4:
				board[xpos][ypos].addToken( new Token("sergeant", rank, color) );
				return true;
			case 5:
				board[xpos][ypos].addToken( new Token("lieutenant", rank, color) );
				return true;
			case 6:
				board[xpos][ypos].addToken( new Token("captain", rank, color) );
				return true;
			case 7:
				board[xpos][ypos].addToken( new Token("major", rank, color) );
				return true;
			case 8:
				board[xpos][ypos].addToken( new Token("colonel", rank, color) );
				return true;
			case 9:
				board[xpos][ypos].addToken( new Token("general", rank, color) );
				return true;
			case 10:
				board[xpos][ypos].addToken( new Token("marshal", rank, color) );
				return true;
			case 11:
				board[xpos][ypos].addToken( new Token("bomb", rank, color) );
				return true;
			default:
				return false;			
		}
		
	}
	
	*/
	
	
	
	

}
