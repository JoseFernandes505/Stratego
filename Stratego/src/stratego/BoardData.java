//Created by Jose Fernandes

package stratego;

import java.awt.Color;
import java.util.List;

public class BoardData{
	//Board array
	private Tile[][] board;
	
	//A list of all the inital token values
	public List<Token> initialTokens;
	
	//Width and height of board
	private int width = 10;
	private int height = 10;
	
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
	
	public BoardData(){
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
	
	//Populates the board with a lot of test values
	public void populateTestBoard(){
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
				
				if(i < 4 ){
					board[i][j].addToken( new Token("lieutenant", 5, false) );
				}
				
				if(i > 5 ){
					board[i][j].addToken( new Token("lieutenant", 5, true) );
				}
				
			}
		}
		
		board[4][4].addToken( new Token("scout", 2, true, 10) );
		board[4][5].addToken( new Token("scout", 2, false, 10) );
		
	}
	
	//Initializes the list of initial tokens, to be placed on the board
	public void initializeList(boolean color){
		
		for(int i = 0; i < flagNum; i++ ){
			initialTokens.add( new Token("flag", 0, color, 0) );
		}
		
		for(int i = 0; i < bombNum; i++ ){
			initialTokens.add( new Token("bomb", 11, color, 0) );
		}
		
		for(int i = 0; i < marshalNum; i++ ){
			initialTokens.add( new Token("marshal", 10, color) );
		}
		
		for(int i = 0; i < generalNum; i++ ){
			initialTokens.add( new Token("general", 9, color) );
		}
		
		for(int i = 0; i < colonelNum; i++ ){
			initialTokens.add( new Token("colonel", 8, color) );
		}
		
		for(int i = 0; i < majorNum; i++ ){
			initialTokens.add( new Token("major", 7, color) );
		}
		
		for(int i = 0; i < captainNum; i++ ){
			initialTokens.add( new Token("captain", 6, color) );
		}
		
		for(int i = 0; i < lieutenantNum; i++ ){
			initialTokens.add( new Token("lieutenant", 5, color) );
		}
		
		for(int i = 0; i < sergeantNum; i++ ){
			initialTokens.add( new Token("sergeant", 4, color) );
		}
		
		for(int i = 0; i < minerNum; i++ ){
			initialTokens.add( new Token("miner", 3, color) );
		}
		
		for(int i = 0; i < scoutNum; i++ ){
			initialTokens.add( new Token("scout", 2, color, (width > height ? width : height)) );
		}
		
		for(int i = 0; i < spyNum; i++ ){
			initialTokens.add( new Token("spy", 1, color) );
		}
	}
	
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

}
