package stratego;

public class BoardData{
	private Tile[][] board;
	
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
}
