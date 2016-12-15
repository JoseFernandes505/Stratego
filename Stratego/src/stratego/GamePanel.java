//Created by Jose Fernandes

package stratego;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/*
 * 
 * Panel class, holds the panel on which the board is drawn, and the
 * buttons which allow for player interaction with the board are placed
 * 
 */
public class GamePanel extends JPanel implements ActionListener{
	
	private final Color highlightGrassColor = new Color(0,204,0);
	private final Color grassColor = new Color(51,102,0);
	private final Color waterColor = new Color(0,0,204);
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
	//Token used when setting up board
	private Token chosenToken;
	//Stores the inital turn, to be used to know when setup is over
	private boolean initialTurn;

	
	
	/*
	 * 
	 * 		CONSTRUCTOR
	 * 
	 */
	
	public GamePanel(BoardData b){
		//sets the board as a passed in board
		board = b;
		//Sets the panel in the frame up
		boardPanel = new JPanel();
		//Creates a new gridlayout for the 10x10 grid
		gridLayout = new GridLayout(board.getWidth(),board.getHeight());
		//Sets the layout
		boardPanel.setLayout( gridLayout );
		//Sets the layout of the parent 
		setLayout(new BorderLayout());
		//Sets the initial turn
		initialTurn = board.getTurn();
		
		//Initializes the buttons to be the same size as the board
		tileButtons = new JButton[board.getWidth()][board.getHeight()];
		
		//Initializes the Tile Buttons Array
		for(int i = 0; i < tileButtons.length; i++){
			
			for(int j = 0; j < tileButtons[i].length; j++){
				//Sets the button's color to be based on the 
				tileButtons[i][j] = new JButton();
				if( board.getTile(i,j).isPassable() ){
					tileButtons[i][j].setBackground( grassColor );
				} else if ( !board.getTile(i,j).isPassable() ){
					tileButtons[i][j].setBackground( waterColor );
				}
				
				//Adds an action listener to the button
				tileButtons[i][j].addActionListener( this );
				
				//Adds the button to the panel
				boardPanel.add(tileButtons[i][j]);
			}
		
		}
		
		boardPanel.setSize( (int) (getSize().getWidth() * 0.2), (int) getSize().getHeight() );
		
		add( boardPanel, BorderLayout.CENTER );
		//Sets the icons for all the tiles
		setAllIcons();
		
		//Makes a Dashboard to hold different piece category views
		dashboard = new JTabbedPane();
		currentPieces = new JPanel();
		offBoardPieces = new JPanel();
		
		//Sets the layouts for the two different panels
		offBoardPieces.setLayout( new GridLayout(6, 2) );
		currentPieces.setLayout( new GridLayout(6, 2) );
		
		//Initializes the buttons for each panel
		offBoardTokens = new JButton[ 12 ];
		currentTokens = new JButton[ 12 ];
		
		//Initializes the buttons and adds them to the panels 
		for(int i = 0; i < offBoardTokens.length; i++){
			offBoardTokens[i] = new JButton("" + i);
			currentTokens[i] = new JButton("" + i);
			
			offBoardTokens[i].setMargin(new Insets(5,5,5,5));
			currentTokens[i].setMargin(new Insets(5,5,5,5));
			
			offBoardTokens[i].addActionListener(this);
			currentTokens[i].addActionListener(this);
			
			offBoardPieces.add( offBoardTokens[i] );
			currentPieces.add( currentTokens[i] );
		}
		
		//Adds the panels to the dashboard
		//dashboard.add( currentPieces, "Board" );
		dashboard.add( offBoardPieces, "Available Pieces" );

		dashboard.setSize( (int) (getSize().getWidth() * 0.2), (int) getSize().getHeight() );
		
		//Adds the dashboard to the east side of the frame
		add( dashboard, BorderLayout.EAST );
		
		updateDashboardButtons();
		
	}

	/*
	 * 
	 * 		INHERITED ACTION LISTENER FUNCTION
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		//Checks if the clicked button is on the game board
		if( onBoardTiles((JButton)ae.getSource()) ){
			//Sets X and Y to be the source's X and Y
			int x = findButtonsX( (JButton) ae.getSource() );
			int y = findButtonsY( (JButton) ae.getSource(), x );
			
			//Checks if Board isn't being set up
			if( !board.settingUp() && !board.gameWon() ){
				//If the board has a token, and that token is currently moving. Resets the background and makes it not moving
				if( board.getTile(x, y).getToken() != null && board.getTile(x, y).getToken().isMoving() ){
					clearBoardBackground();
					board.clearBoardData();
				//If the board has a token, the tile is not moving(so as to make sure it isn't selected), and the tile is in range. This does battle
				} else if( 	board.getTile(x, y).getToken() != null && !board.getTile(x, y).getToken().isMoving() &&	board.getTile(x, y).inRange()){
					doBattle(x, y);
					switchTurn();
				//If the board has a token, and the tile is of the current turn
				} else if(	board.getTile(x, y).getToken() != null && board.getTile(x, y).getToken().getTeam() == board.getTurn()){
					HighlightTiles( board.getTile(x, y).getToken(), x, y );
				//If the board is in range it moves the token to it
				} else if(	board.getTile(x, y).inRange()){
					MoveToken(x, y);
					switchTurn();
				}
			//Checks that board is being set up
			} else if( board.settingUp() ){
				//If board has no token and is not in range, clears the highlights
				if ( (board.getTile(x, y).getToken() == null || board.getTile(x, y).getToken().getTeam() == board.getTurn()) && chosenToken != null ){
					if(board.getTile(x, y).isPassable()){
						//Checks that a tile was actually set before making the chosen token null
						if( setupInitialTokens(x, y, chosenToken) ){
							chosenToken = null;
						}
					}						
				}
			}
			
			
			
		} 
		
		//Gets the token from the dashboard and sets the chosen token 
		if( onOffBoardDashboard( (JButton)ae.getSource() ) ){
			
			int rank = findRank( (JButton)ae.getSource(), offBoardTokens );
			
			chosenToken = new Token(rank, board.getTurn());
			
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
					tileButtons[i][j].setBackground( highlightGrassColor );
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
	
	//Compares the two pieces 
	public void doBattle(int defX, int defY){
		int attX = -1, attY = -1;
		//Forloop to see which piece is the one that's currently moving
		for(int i = 0; i < board.getWidth(); i++){
			for(int j = 0; j< board.getHeight(); j++){
				//If the piece has a token, and it is currently moving, then it is set to be the move token
				if(board.getTile(i, j).getToken() != null && board.getTile(i, j).getToken().isMoving()){
					attX = i;
					attY = j;
				}
					
			}
		}
		
		board.getTile(defX, defY).getToken().startMoving();
		
		board.startBattling();
		
		setAllIcons();
		
		battlePopup(attX, attY, defX, defY);		
		
		board.doBattle(defX, defY, attX, attY);
		
		board.stopBattling();
		
		clearBoardBackground();
		setAllIcons();
	}
	
	public void switchTurn(){
		board.switchTurn();
		board.clearBoardData();
		clearBoardBackground();
		
		
		if( board.gameWon() ){
			if( board.draw() ){
				showDrawConfirmation();
			} else if( board.blueWin() ){
				showWinConfirmation(true);
			} else if( board.redWin() ){
				showWinConfirmation(false);
			}
		} else {
			board.betweenTurns(true);
			setAllIcons();
			nextTurnPopup();
			board.betweenTurns(false);
		}
		
		setAllIcons();
		updateDashboardButtons();
		
		if( board.settingUp() ){
			if(board.getTurn() == initialTurn){
				
				for(int i = 0; i < currentTokens.length; i++){
					currentTokens[i].setBackground(Color.WHITE);
					offBoardTokens[i].setBackground(Color.WHITE);
					
					currentPieces.add( currentTokens[i] );
					offBoardPieces.add( offBoardTokens[i] );
				}
				
				//Sets up the new board tabs
				dashboard.add(currentPieces, "Battlefield");
				dashboard.add(offBoardPieces, "Graveyard");				
				
				//Starts the actual game
				board.switchSetup();
			}
		}
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
					if( 	!board.betweenTurns() && !board.battling() && board.getTile(x, y).getToken().getTeam() == board.getTurn() ||
							!board.betweenTurns() && board.battling() && board.getTile(x, y).getToken().isMoving() ){
						
						//Gets the path for the imageicon
						String path = "/icons/" + board.getTile(x, y).getToken().getPathname();
						
						//Edits the path to the reverse should that setting be in place
						if(board.reversedPieces()){
							path += "_r";
						}
						
						path +=  ".jpg";
						
						icon = new ImageIcon( getClass().getResource( path ) );
					} else if( 	!board.betweenTurns() && !board.battling() && board.getTile(x, y).getToken().getTeam() != board.getTurn() ||
								!board.betweenTurns() && board.battling() && !board.getTile(x, y).getToken().isMoving() ||
								board.betweenTurns() ){
						
						//Creates an imageicon of the tile's team bg
						icon = new ImageIcon( getClass().getResource( "/icons/" + board.getTile(x, y).getToken().getBgPathname() + ".jpg" ) );
					
					}
					
				}
				
				tileButtons[i][j].setIcon( icon );
												
			}
		}
		
		
		
	}
	
	public void updateDashboardButtons(){
		
		//Updates Current Tokens
		for(int i = 0; i < currentTokens.length; i++){
			Token temp = new Token(i, board.getTurn());
			
			currentTokens[i].setText( "<html>" + temp.getRankName() + "<br> x " + getNumTokens(i, board.getTurn()) + "</html>" );
			
			String path = "/icons/" + temp.getPathname();
			
			if(board.reversedPieces()){
				path += "_r";
			}
			
			path += ".jpg";
			
			currentTokens[i].setIcon( new ImageIcon( getClass().getResource( path ) ) );
		}
		
		//Updates Offboard Tokens
		for(int i = 0; i < offBoardTokens.length; i++){
			Token temp = new Token(i, board.getTurn());
			
			offBoardTokens[i].setText( "<html>" + temp.getRankName() + "<br> x " + (board.getInitialRankTokens(i) - getNumTokens(i, board.getTurn())) + "</html>");
			
			String path = "/icons/" + temp.getPathname();
			
			if(board.reversedPieces()){
				path += "_r";
			}
			
			path += ".jpg";
			
			offBoardTokens[i].setIcon( new ImageIcon( getClass().getResource( path ) ) );
		
		}
		
	}
	
	//Clears the panel backgrounds, and makes everything not in range
	public void clearBoardBackground(){
			
		for(int i = 0; i < tileButtons.length; i++){
			for(int j = 0; j < tileButtons[i].length; j++){
				
				//Sets all buttons to their default values
				if( board.getTile(i, j).isPassable() ){
					tileButtons[i][j].setBackground( grassColor );
				} else if( !board.getTile(i, j).isPassable() ){
					tileButtons[i][j].setBackground( waterColor );
				}
				
			}
		}
			
	}
	
	public boolean setupInitialTokens(int x, int y, Token tok){
		
		if( board.getInitialRankTokens( tok.getRank() ) - getNumTokens( tok.getRank(), board.getTurn() ) > 0){
			//Sets the token
			boolean success = board.setToken(x, y, tok);
			
			setAllIcons();
			updateDashboardButtons();
			
			boolean empty = true;
			for(int i = 0; i < offBoardTokens.length; i++){
				if( board.getInitialRankTokens(i) - getNumTokens(i, board.getTurn() ) != 0){
					empty = false;
				}
			}
			
			if(empty){
				switchTurn();
			}
			
			return success;
		}
		
		return false;
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
	
	//Finds the rank of an button's token
	public int findRank( JButton b, JButton[] array ){
		
		for(int i = 0; i < array.length; i++){
			
			if( array[i] == b ){
				return i;
			}
			
		}
		
		return -1;
		
	}
	
	//Checks that the button is on the board
	public boolean onBoardTiles( JButton b ){
		
		for(int i = 0; i < tileButtons.length; i++){	
			for(int j = 0; j < tileButtons[i].length; j++){
				if( tileButtons[i][j] == b ){
					return true;
				}
			}
		}
		return false;
	}
	
	//Checks that the button is on the offboard dashboard
	public boolean onOffBoardDashboard( JButton b ){
		
		for(int i = 0; i < offBoardTokens.length; i++){	
			if( offBoardTokens[i] == b ){
				return true;
			}
		}
		return false;
	}
	
	
	//Gets the number of any specific piece there is on the board
	public int getNumTokens(int rank, boolean team){		
		//Temp value to hold the number of the pieces
		int temp = 0;
		//Loops through the board
		for(int i = 0; i < board.getWidth(); i++){
			for(int j = 0; j < board.getHeight(); j++){
				
				//If the tile is not empty, checks if the token is the target token, then increments if it is
				if( board.getTile(i, j).getToken() != null ){
					
					if( board.getTile(i, j).getToken().getRank() == rank &&
						board.getTile(i, j).getToken().getTeam() == team){
							//Increments the number of that piece
							temp++;
					}			
					
				}
				
			}
		}
		
		//Returns how many there are
		return temp;
	}
	
	public void showWinConfirmation(boolean winner){
		
		String teamName = (winner ? "Blue" : "Red");
		
		JOptionPane.showMessageDialog(this, "Congratulations " + teamName + " team! You have won!", teamName + " team Wins!", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public void showDrawConfirmation(){
		
		JOptionPane.showMessageDialog(this, "There has been a tie! Neither team wins!", "No team Wins!", JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public void battlePopup(int attX, int attY, int defX, int defY){
		
		Token att = board.getTile(attX, attY).getToken();
		Token def = board.getTile(defX, defY).getToken();
		
		String resultString = "";
		String attTeam = ( att.getTeam() ? "Red" : "Blue");
		String defTeam = ( def.getTeam() ? "Red" : "Blue");
		int battleResult = att.doBattle(def);
		
		resultString += ( battleResult > 0 ? "The " + defTeam + " " + def.getRankName() + " was captured!" : "" );
		resultString += ( battleResult < 0 ? "The " + attTeam + " " + att.getRankName() + " was captured!" : "" );
		resultString += ( battleResult == 0 ? "Both pieces were captured!" : "" );
		
		JOptionPane.showMessageDialog(	this, 
										"Battle!\n" + 
										"The " + attTeam + " " + att.getRankName() + " ( " + att.getRank() + " ) " + 
										"has attacked the " + defTeam + " " + def.getRankName() + " ( " + def.getRank() + " ) \n" +
										resultString,
										"Battle!",
										JOptionPane.PLAIN_MESSAGE);
		
	}
	
	public void nextTurnPopup(){
		String turn = (board.getTurn() ? "Red" : "Blue");
		JOptionPane.showMessageDialog(this, "It is now " + turn + "'s turn. Please pass the computer." , "Switch turn", JOptionPane.PLAIN_MESSAGE);
	}
	
}
