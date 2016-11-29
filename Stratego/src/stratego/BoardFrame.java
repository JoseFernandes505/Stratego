//Created by Jose Fernandes

package stratego;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/*
 * 
 * Panel class, holds the panel on which the board is drawn, and the
 * buttons which allow for player interaction with the board are placed
 * 
 */
public class BoardFrame extends JFrame implements ActionListener{
	
	//An array of buttons which correspond to the tiles within the BoardData object
	private JButton[][] tileButtons;
	//An array of buttons for the representations of the living tokens
	private JButton[] currentTokens;
	//An array of buttons for the representations of the off board tokens
	private JButton[] offBoardTokens;
	//Gridlayout within which all the buttons are placed
	private GridLayout gridLayout; 
	//board object, contains all the board data
	private BoardData board;
	//Panel for the board to be displayed on
	private JPanel boardPanel;
	//Tabbed panel for the dashboard
	private JTabbedPane dashboard;
	//Shows the player's current pieces
	private JPanel currentPieces;
	//Shows the player's pieces at the beginning of the game
	private JPanel offBoardPieces;

	
	
	/*
	 * 
	 * 		CONSTRUCTOR
	 * 
	 */
	
	public BoardFrame(BoardData b){
		//sets the board as a passed in board
		board = b;
		//Sets the panel in the frame up
		boardPanel = new JPanel();
		//Creates a new gridlayout for the 10x10 grid
		gridLayout = new GridLayout(board.getWidth(),board.getHeight());
		//Sets the layout
		boardPanel.setLayout( gridLayout );
		
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
				boardPanel.add(tileButtons[i][j]);
			}
		
		}
		
		add( boardPanel, BorderLayout.CENTER );
		//Sets the icons for all the tiles
		setAllIcons();
		
		dashboard = new JTabbedPane();
		currentPieces = new JPanel();
		offBoardPieces = new JPanel();
		
		offBoardPieces.setLayout( new GridLayout(6, 2) );
		offBoardTokens = new JButton[ 12 ];
		
		
		for(int i = 0; i < offBoardTokens.length; i++){
			offBoardTokens[i] = new JButton("" + i);
			offBoardPieces.add( offBoardTokens[i] );
		}
		
		dashboard.add( offBoardPieces, "Not On Board" );
		dashboard.add( currentPieces, "On Board" );
		
		
		
		add( dashboard, BorderLayout.EAST );
		
		System.out.println( getNumPieces(5, true) );
		System.out.println( getNumPieces(5, false) );
		
	}



	/*
	 * 
	 * 		INHERITED ACTION LISTENER FUNCTION
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		
		//Sets X and Y to be the source's X and Y
		int x = findButtonsX( (JButton) ae.getSource() );
		int y = findButtonsY( (JButton) ae.getSource(), x );
		
		
		if( board.getTile(x, y).getToken() != null && board.getTile(x, y).getToken().isMoving() ){
			clearBoardBackground();
			board.clearBoardData();
		} else if( 	board.getTile(x, y).getToken() != null && !board.getTile(x, y).getToken().isMoving() &&	board.getTile(x, y).inRange()){
			doBattle(x, y);
			switchTurn();
		} else if(	board.getTile(x, y).getToken() != null && board.getTile(x, y).getToken().getTeam() == board.getTurn()){
			HighlightTiles( board.getTile(x, y).getToken(), x, y );
		} else if(	board.getTile(x, y).inRange()){
			MoveToken(x, y);
			switchTurn();
		} else if( !board.getTile(x, y).inRange() && board.getTile(x, y).getToken() == null ){
			clearBoardBackground();
			board.clearBoardData();
		}
	}
	
	/*
	 * 
	 * 		ACTION LISTENER FUNCTIONS
	 * 
	 */
	
	//Highlights the tiles in movable range of a token
	public void HighlightTiles(Token tok, int x, int y){
		//Clears the background of any selected item
		clearBoardBackground();
			
		//Sets the tiles that are passable as passable
		board.setTilesPassable(tok, x, y);
		
		//Changes the background of all the tiles within range
		for(int i = 0; i < tileButtons.length; i++){
			for(int j = 0; j < tileButtons[i].length; j++){
				
				if( board.getTile(i,j).isPassable() && board.getTile(i, j).inRange() ){
					tileButtons[i][j].setBackground( Color.CYAN );
				} 				
			}
		}
	}	
	
	//Moves the token to an inputted X and Y
	public void MoveToken(int toX, int toY){
		//Moves the token on the board
		board.moveToken(toX, toY);
		
		//Clears the board's backgrounds from the highlighted tiles and updates the icons
		clearBoardBackground();
		setAllIcons();
	}
	
	public void doBattle(int defX, int defY){
		board.doBattle(defX, defY);
		
		clearBoardBackground();
		setAllIcons();
	}
	
	public void switchTurn(){
		board.switchTurn();
		board.clearBoardData();
		clearBoardBackground();
		setAllIcons();
	}
		
	
	/*
	 * 
	 * 		HELPER FUNCTIONS
	 * 
	 */
	
	//Sets the icons of the board to be the appropriate icons
	public void setAllIcons(){
		
		//Forloop to draw the piece's icons
		for(int i = 0; i < tileButtons.length; i++){
			for(int j = 0; j < tileButtons[i].length; j++){
				
				//Sets the holder X and Y to be the button's X and Y
				int x = findButtonsX(tileButtons[i][j]);
				int y = findButtonsY(tileButtons[i][j], x);
				
				//Icon for the token icon
				ImageIcon icon = null;
				
				//If the corresponding tile has a token
				if( board.getTile(x, y).getToken() != null ){
					if( board.getTile(x, y).getToken().getTeam() == board.getTurn() ){
						//Creates an imageicon of the tile's pathname
						icon = new ImageIcon( this.getClass().getResource( "/icons/" + board.getTile(x, y).getToken().getPathname() + ".png" ) );
					} else if( board.getTile(x, y).getToken().getTeam() != board.getTurn() ){
						//Creates an imageicon of the tile's team bg
						icon = new ImageIcon( this.getClass().getResource( "/icons/" + board.getTile(x, y).getToken().getBgPathname() + ".png" ) );
					}
					
				}
				
				tileButtons[i][j].setIcon( icon );
												
			}
		}
		
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
				
			}
		}
			
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
	
	//Gets the number of any specific piece there is on the board
	public int getNumPieces(int rank, boolean team){		
		//Temp value to hold the number of the pieces
		int temp = 0;
		//Loops through the board
		for(int i = 0; i < board.getWidth(); i++){
			for(int j = 0; j < board.getHeight(); j++){
				
				//If the tile is not empty, checks if the token is the target token, then increments if it is
				if( board.getTile(i, j).getToken() != null ){
					
					if( board.getTile(i, j).getToken().getRank() == rank &&
						board.getTile(i, j).getToken().getTeam() == team){

							temp++;
						
					}			
					
				}
				
			}
		}
		
		//Returns how many there are
		return temp;
	}
}
