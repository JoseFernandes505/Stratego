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
	//Holder X and Y ints, get the position of a button in tileButtons[x][y] and allow 
	//for the tile in the boarddata object to be found
	private int x = 0, y = 0;
	
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
		
	}
	
	
	//Function to find the position of a button in the buttonarray in terms of X and Y
	public void findButtonsTile(JButton button){
		
		//Searches the array until the button is found, then sets the holder variables as the current i and j
		for(int i = 0; i < tileButtons.length; i++){	
			for(int j = 0; j < tileButtons[i].length; j++){
				if( tileButtons[i][j] == button ){
					x = i;
					y = j;
					return;
				}
			}
		
		}
	}
	
	
	//Paint component of the board
	@Override
	public void paintComponent(Graphics g){
		
		super.paintComponent( g );
		
		//Forloop to draw the piece's icons
		for(int i = 0; i < tileButtons.length; i++){
			for(int j = 0; j < tileButtons[i].length; j++){
				
				//Sets the holder X and Y to be the button's X and Y
				findButtonsTile(tileButtons[i][j]);
				
				//If the corresponding tile has a token
				if( board.getTile(x, y).getToken() != null ){
					
					//Creates an imageicon of the tile's pathname
					ImageIcon icon = new ImageIcon( this.getClass().getResource( "/icons/" + board.getTile(x, y).getToken().getPathname() + ".png" ) );

					//Sets the button's icon to be that imageicon
					tileButtons[i][j].setIcon(icon);
					
				}
												
			}
		}
		
		
	}



	//Inherited ActionListener function
	@Override
	public void actionPerformed(ActionEvent ae) {
			//Sets X and Y to be the source's X and Y
			findButtonsTile( (JButton) ae.getSource() );
			
			//If there's a token at the location, calls the 
			if(board.getTile(x, y).getToken() != null){
				HighlightTiles( board.getTile(x, y).getToken() );
			}
	}
	
	public void HighlightTiles(Token tok){
		int range = tok.getRange();
		boolean leftMoveBlocked = false, rightMoveBlocked = false, upMoveBlocked = false, downMoveBlocked = false;
		
		clearBoardBackground();	
		
		for(int i = 0; i < range; i++){
			
			//Checks for available moves on the right
			if( (x + i + 1) < board.getWidth() ){
				if(!rightMoveBlocked)
				rightMoveBlocked = checkDirection(rightMoveBlocked, x + i + 1, y);				
			}
			
			//Checks for available moves on the left
			if(x - i - 1 >= 0){
				if(!leftMoveBlocked)
					leftMoveBlocked = checkDirection(leftMoveBlocked, x - i - 1, y);
			}
			
			//Checks for available moves above
			if(y + i + 1 < board.getHeight() ){
				if(!upMoveBlocked)
					upMoveBlocked = checkDirection(upMoveBlocked, x , y + i + 1);	
			}
			
			//Checks for available moves below
			if(y - i - 1 >= 0 ){
				if(!downMoveBlocked)
					downMoveBlocked = checkDirection(downMoveBlocked, x , y - i - 1);				
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
	
	public boolean checkDirection(boolean direction, int x, int y){
		if( !board.getTile( x, y).isPassable() ){
			return true;
		}
						
		if(!direction){
			if( board.getTile( x, y).getToken() != null ){
				board.getTile( x, y).setInRange( true );
				return true;
			} else {
				board.getTile( x, y).setInRange( true );
			}
			
		}
		
		return false;
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
	
	
}
