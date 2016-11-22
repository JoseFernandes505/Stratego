//Created by Jose Fernandes

package stratego;

import java.util.List;

public class BoardData{
	private Tile[][] board;
	public List<Token> initialTokens;
	
	/*private int bombNum = 6;
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
	*/
	public BoardData(){
		board = new Tile[10][10];
		buildBoard();
	}
	
	public void buildBoard(){
		
		//Initializes all the tiles in the board ass passable and empty
		for(int i = 0; i < board.length; i++){
			
			for(int j = 0; j < board[i].length; j++){
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
	
	public int getWidth(){
		return board.length;
	}
	
	public int getHeight(){
		return board[0].length;
	}
	
	public Tile getTile(int x, int y){
		return board[x][y];
	}
	
	public void populateTestBoard(){
		
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++){
				
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
	
	public void initializeList(){
		
		//for(int i = 0; i < )
		
	}
	
	/*
	 * 		CREATE TOKEN FUNCTIONS
	 */
	
	
	private boolean setToken(int rank, int xpos, int ypos){
		
		//Makes sure that the x and y inputs are both within range
		if(xpos >= board.length || xpos < 0){
			return false;
		}
		if(ypos >= board[0].length || ypos < 0){
			return false;
		}
		
		//Checks to make sure that 
		if(board[xpos][ypos] != null){
			return false;
		} 
			
		
		
		return false;
	}
	
	//Creates a Scout (2)
	private Token create2Scout(boolean team){
		return new Token("scout", 2, team);
	}
	
	//Creates a Miner (3)
	private Token create3Miner(boolean team){
		return new Token("miner", 3, team);
	}
	
	//Creates a Sergeant (4)
	private Token create4Sergeant(boolean team){
		return new Token("sergeant", 4, team);
	}
	
	//Creates a Lieutenant (5)
	private Token create5Lieutenant(boolean team){
		return new Token("lieutenant", 5, team);
	}
	
	//Creates a Captain (6)
	private Token create6Captain(boolean team){
		return new Token("captain", 6, team);
	}
	
	//Creates a Major (7)
	private Token create7Major(boolean team){
		return new Token("major", 7, team);
	}
	
	//Creates a Colonel (8)
	private Token create8Colonel(boolean team){
		return new Token("colonel", 8, team);
	}
	
	//Creates a General (9)
	private Token create9General(boolean team){
		return new Token("general", 9, team);
	}
	
	//Creates a Colonel (8)
	private Token create10Marshal(boolean team){
		return new Token("marshal", 10, team);
	}
}
