//Created by Jose Fernandes

package stratego;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/*
 * 
 * Panel class, holds the panel on which the board is drawn, and the
 * buttons which allow for player interaction with the board are placed
 * 
 */
public class BoardPanel extends JPanel implements ActionListener{
	
	//An array of buttons which correspond to the tiles within the BoardData object
	private JButton[][] tileButtons;
	//Gridlayout within which all the buttons are placed
	private GridLayout gridLayout; 
	//board object, contains all the board data
	private BoardData board;
	//simple enum to say who's turn it is
	private turnState turn = turnState.NONE;
	
	public BoardPanel(BoardData b){
		//sets the board as a passed in board
		board = b;
		//Creates a new gridlayout for the 10x10 grid
		gridLayout = new GridLayout(10,10);
		//Sets the layout
		setLayout( gridLayout );
		
		//Initializes the buttons to be the same size as the board
		tileButtons = new JButton[board.getWidth()][board.getHeight()];
		
		//Initializes the Tile Buttons Array
		for(int i = 0; i < tileButtons.length; i++){
			
			for(int j = 0; j < tileButtons[i].length; j++){
				//Sets the button's color to be based on the 
				tileButtons[i][j] = new JButton();
				if( board.getTile(i,j).isPassable() ){
					tileButtons[i][j].setBackground( Color.GREEN );
				} else if ( !board.getTile(i,j).isPassable() ){
					tileButtons[i][j].setBackground( Color.BLUE );
				}
				
				//Adds an action listener to the button
				tileButtons[i][j].addActionListener( this );
				
				//Adds the button to the panel
				add(tileButtons[i][j]);
			}
		
		}
		
		//Sets the icons for all the tiles
		setAllIcons();
		
	}
	
	
	//Function to find the position of a button in the buttonarray in terms of X and Y
	public int findButtonsX(JButton button){
		
		//Searches the array until the button is found, then sets the holder variables as the current i and j
		for(int i = 0; i < tileButtons.length; i++){	
			for(int j = 0; j < tileButtons[i].length; j++){
				if( tileButtons[i][j] == button ){
					return i;
				}
			}
		}
		//If not available
		return -1;
	}
	
	//Takes an inputted X from the previous findButtonsX function, and finds the Y from that
	public int findButtonsY(JButton button, int x){	
		
		//Searches the array until the button is found, then sets the holder variable as the current i
		for(int i = 0; i < tileButtons[x].length; i++){
			if( tileButtons[x][i] == button ){
					return i;
			}
		}
		//If not available
		return -1;
	}
	
	
	
	//Sets the icons of the board to be the appropriate icons
	public void setAllIcons(){
		
		//Forloop to draw the piece's icons
		for(int i = 0; i < tileButtons.length; i++){
			for(int j = 0; j < tileButtons[i].length; j++){
				
				//Sets the holder X and Y to be the button's X and Y
				int x = findButtonsX(tileButtons[i][j]);
				int y = findButtonsY(tileButtons[i][j], x);
				
				//If the corresponding tile has a token
				if( board.getTile(x, y).getToken() != null ){
					
					//Creates an imageicon of the tile's pathname
					ImageIcon icon = new ImageIcon( this.getClass().getResource( "/icons/" + board.getTile(x, y).getToken().getPathname() + ".png" ) );

					//Sets the button's icon to be that imageicon
					tileButtons[i][j].setIcon(icon);
					
				} else {
					tileButtons[i][j].setIcon(null);
				}
												
			}
		}
		
	}



	//Inherited ActionListener function
	@Override
	public void actionPerformed(ActionEvent ae) {
			//Sets X and Y to be the source's X and Y
			int x = findButtonsX( (JButton) ae.getSource() );
			int y = findButtonsY( (JButton) ae.getSource(), x );
			
			//If there's a token at the location, calls the 
			if(board.getTile(x, y).getToken() != null){
				HighlightTiles( board.getTile(x, y).getToken(), x, y );
			}
			
			if(board.getTile(x, y).inRange()){
				MoveToken();
			}
	}
	
	//Highlights the tiles in moveable range of a token
	public void HighlightTiles(Token tok, int x, int y){
		int range = tok.getRange();
		boolean leftMoveBlocked = false, rightMoveBlocked = false, upMoveBlocked = false, downMoveBlocked = false;
		
		clearBoardBackground();	
		
		for(int i = 0; i < range; i++){
			
			//Checks for available moves on the right
			if( (x + i + 1) < board.getWidth() ){
				if(!rightMoveBlocked)
				rightMoveBlocked = checkDirection(rightMoveBlocked, x + i + 1, y, tok.getTeam());				
			}
			
			//Checks for available moves on the left
			if(x - i - 1 >= 0){
				if(!leftMoveBlocked)
					leftMoveBlocked = checkDirection(leftMoveBlocked, x - i - 1, y, tok.getTeam());
			}
			
			//Checks for available moves above
			if(y + i + 1 < board.getHeight() ){
				if(!upMoveBlocked)
					upMoveBlocked = checkDirection(upMoveBlocked, x , y + i + 1, tok.getTeam());	
			}
			
			//Checks for available moves below
			if(y - i - 1 >= 0 ){
				if(!downMoveBlocked)
					downMoveBlocked = checkDirection(downMoveBlocked, x , y - i - 1, tok.getTeam());				
			}
			
		}
		
		//Changes the background of all the tiles within range
		for(int i = 0; i < tileButtons.length; i++){
			for(int j = 0; j < tileButtons[i].length; j++){
				
				if( board.getTile(i,j).isPassable() && board.getTile(i, j).inRange() ){
					tileButtons[i][j].setBackground( Color.CYAN );
				} 				
			}
		}
	}
	
	//Function to be used within the HighlightTiles function
	public boolean checkDirection(boolean direction, int x, int y, boolean tokenTeam){
		//Checks if the tile is passable, if not, returns that it's blocked
		if( !board.getTile( x, y).isPassable() ){
			return true;
		}
						
		//If it still isn't blocked,checks if there is a token on the tile
		if(!direction && board.getTile( x, y).isPassable()){
			//If there's a token, sets that one token in range but then sets its motion
			//as blocked so it does not continue
			if( board.getTile( x, y).getToken() != null ){
				if( board.getTile(x, y).getToken().getTeam() == tokenTeam){
					board.getTile( x, y).setInRange( false );
				} else if( board.getTile(x, y).getToken().getTeam() != tokenTeam) {
					board.getTile( x, y).setInRange( true );
				}
				
				return true;
			//Otherwise the tile is simply set in range
			} else {
				board.getTile( x, y).setInRange( true );
			}
			
		}
		
		//If none of the things returned that it is blocked, return that it isn't
		return false;
	}
	
	
	//TODO
	public void MoveToken(){
		
	}
	
	
	//Clears the panel backgrounds, and makes everything not in range
	public void clearBoardBackground(){
		
		for(int i = 0; i < tileButtons.length; i++){
			for(int j = 0; j < tileButtons[i].length; j++){
				
				//Sets all buttons to their default values
				if( board.getTile(i, j).isPassable() ){
					tileButtons[i][j].setBackground( Color.GREEN );
				} else if( !board.getTile(i, j).isPassable() ){
					tileButtons[i][j].setBackground( Color.BLUE );
				}
				
				//Sets all tiles to out of range
				board.getTile(i, j).setInRange(false);
				
			}
		}
		
	}
	
	
	private enum turnState{
		RED,
		BLUE,
		NONE
	}
	
}
