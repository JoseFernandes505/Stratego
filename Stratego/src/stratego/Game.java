//Created by Jose Fernandes
package stratego;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

//Main Game Running class
public class Game implements ActionListener{

	//Frame and Menu Bar
	private JFrame f;
	private JMenuBar mBar;
	private JMenu saveGameOption;
	private JMenu loadGameOption;
	private JMenu quitGameOption;
	private JMenuItem saveGameItem;
	private JMenuItem loadGameItem;
	private JMenuItem quitGameItem;
	
	//Different possible panels and board for switching
	private BoardData board;
	private GamePanel bp;
	private MenuPanel mp;
	private SettingsPanel sp;
	
	//Board settings
	private boolean reversePieces = false;
	private Dimension frameSize = new Dimension(1100,1000);
	
	
	public void Begin(){
		//Creates a default frame for the operations to take place in
		//(Main Menu, Settings, Game, Win/Lose Screens, etc)
		f = new JFrame("Stratego");	
		
		//Creates the initial panel to populate the frame, the menu panel
		mp = new MenuPanel( this );
		
		//Initializes the menu bar items
		mBar = new JMenuBar();
		saveGameOption = new JMenu("Save");
		saveGameItem = new JMenuItem("Save Game");
		quitGameOption = new JMenu("Quit");
		quitGameItem = new JMenuItem("Quit Game");
		loadGameOption = new JMenu("Load");
		loadGameItem = new JMenuItem("Load Game");
		
		//Adds action listeners to the menu bar items
		saveGameItem.addActionListener(this);
		quitGameItem.addActionListener(this);
		loadGameItem.addActionListener(this);
		
		//Adds the items to their menus and then to the bar
		saveGameOption.add(saveGameItem);
		quitGameOption.add(quitGameItem);
		loadGameOption.add(loadGameItem);
		mBar.add(saveGameOption);
		mBar.add(loadGameOption);
		mBar.add(quitGameOption);
					
		//Sets Frame up		
		f.setSize( frameSize );
		f.getContentPane().add( mp );
		
		//Sets frame properties
		f.setDefaultCloseOperation( JFrame.DO_NOTHING_ON_CLOSE);
		f.setResizable( false );
	    f.setVisible( true );
	    f.setLocationRelativeTo(null); 
		
	}

	
	//Action Performed Function:
	//Handles the Menu and Settings panels, and the 
	//menubar in the GamePanel
	@Override
	public void actionPerformed(ActionEvent ae) {
		
		String source = ae.getActionCommand();
		
		//If from Main Menu -> Start Game
		if( source.equals( "Start Game" ) ){
			//Creates a new board data and panel objects
			board = new BoardData( reversePieces );
			bp = new GamePanel(board);
			
			//Clears the frame, sets the jmenubar, and adds the board
			f.getContentPane().removeAll();
			f.setJMenuBar(mBar);
			f.getContentPane().add( bp );
			
			//Repaints the frame with the board on it
			f.repaint();
			f.revalidate();
		//If from Main Menu -> Settings
		} else if( source.equals( "Settings" ) ){
			//Creates new settings panel
			sp = new SettingsPanel( this );
			
			//Clears the frame, removes the jmenubar, and adds the panel
			f.getContentPane().removeAll();
			f.setJMenuBar( null );
			f.getContentPane().add( sp );
			
			//Repaints the frame with the panel on it
			f.repaint();
			f.revalidate();
		//Exits the game
		}  else if( source.equals( "Quit" ) ){
			System.exit(0);
		//Keeps the ranks in standard formation within settings
		} else if( source.equals( "Standard Ranks" ) ){
			reversePieces = false;
		//Reverses ranks within settings
		} else if( source.equals( "Reversed Ranks" ) ){
			reversePieces = true;
		} else if( source.equals( "770 x 700" ) ){
			//Changes the dimension variable and sets the size to it
			frameSize = new Dimension(770,700);
			f.setSize( frameSize );
			
			//Repaints and revalidates the frame
			f.repaint();
			f.revalidate();
		} else if( source.equals( "1430 x 1300" ) ){
			//Changes the dimension variable and sets the size to it
			frameSize = new Dimension(1430,1300);
			f.setSize( frameSize );
			
			//Repaints and revalidates the frame
			f.repaint();
			f.revalidate();
		} else if( source.equals( "1100 x 1000" ) ){
			//Changes the dimension variable and sets the size to it
			frameSize = new Dimension(1100,1000);
			f.setSize( frameSize );
			
			//Repaints and revalidates the frame
			f.repaint();
			f.revalidate();
		//Returns to the main menu
		//Settings -> Main Menu
		//GamePanel -> Main Menu
		}else if( source.equals( "Back To Main Menu" ) || source.equals( "Quit Game" ) ){
			//Creates new main menu panel
			mp = new MenuPanel( this );
			
			//Clears the frame, removes the jmenubar, and adds the panel
			f.getContentPane().removeAll();
			f.setJMenuBar( null );
			f.getContentPane().add( mp );
			
			//Repaints the frame with the panel on it
			f.repaint();
			f.validate();
		//Returns the save game function
		} else if( source.equals( "Save Game" ) ){
			saveGamePopup();
		//Returns the load game function
		} else if( source.equals( "Load Game" ) ){
			loadGamePopup();
		}
		
	}
	
	//Returns whether the pieces are reversed
	public boolean reversedPieces(){
		return reversePieces;
	}
	
	//Returns the dimension of the frame
	public Dimension getSize(){
		return frameSize;
	}
	
	//Makes a popup to allow the user to save their game
	public void saveGamePopup(){
		
		try{			
			//Makes an outputstream to save the board with
			ObjectOutputStream oos;
			File f = new File("./saves");
			boolean exists = false;
			boolean resultBool = true;
			
			//Asks for the file name
			String fileName = JOptionPane.showInputDialog(	null,
															"Please enter the name of your save file ",
															"Save Game",
															JOptionPane.INFORMATION_MESSAGE );
			
			//Gets the files in the directory and checks for conflict
			File[] flist = f.listFiles();
			for( int i = 0; i < flist.length; i++ ){
				if( flist[i].getName().equals( fileName ) ){
					exists = true;
				}
			}
			
			//If there is a conflict, asks confirmation for an overwrite
			if( exists ){
				int result = JOptionPane.showConfirmDialog(	null,
															"This filename already exists. Do you want to continue?",
															"File already exists!",
															JOptionPane.YES_NO_OPTION);
				resultBool = ( result == 0 ? true : false );
			}
			
			//If the confirmation message is accepted or there is no conflict
			if( resultBool ){
				oos = new ObjectOutputStream( new BufferedOutputStream( new FileOutputStream( "./saves/" + fileName ) ) );
				
				oos.writeObject( board );
				
				oos.close();
			}
			
		} catch(FileNotFoundException ex){
			ex.printStackTrace();
		} catch(IOException ex){
			ex.printStackTrace();
		}		
	}
	
	//A popup that allows the user to load from a saved game
	public void loadGamePopup(){
		try{
			//Asks for an input stream to take the serialized object
			ObjectInputStream ois;
			String fileName = null;
			File saveDir = new File( "./saves" );
			
			//An array of all the files within the save directory
			File[] saveFiles = saveDir.listFiles();
			
			//The names of those files
			String[] saveFilesNames = new String[ saveFiles.length ];
			
			for(int i = 0; i < saveFilesNames.length; i++){
				saveFilesNames[i] = saveFiles[i].getName();
			}
			
			//If the user tries to load with no saves in the folder
			if( saveFilesNames.length == 0 ){
				JOptionPane.showConfirmDialog(	null,
												"Uh oh! There are no current save files! Play a game and save!",
												"No Save Files",
												JOptionPane.DEFAULT_OPTION,
												JOptionPane.PLAIN_MESSAGE);
			//If there are files within the folder
			} else {
				fileName = (String) JOptionPane.showInputDialog(	null,
																	"Choose which file to load from: ",
																	"Loading File",
																	JOptionPane.INFORMATION_MESSAGE,
																	null,
																	saveFilesNames,
																	null);
			}
			
			
			if(fileName != null){
				//Gets an inputstream from the filename
				ois = new ObjectInputStream( new BufferedInputStream( new FileInputStream( "./saves/" + fileName) ) );
				
				//Makes a new board from the serialized object
				BoardData tempBoard = (BoardData) ois.readObject();
				//Sets up a panel with this board
				bp = new GamePanel( tempBoard );
				
				//Sets the JMenubar and clears the frame to put the board on it
				f.getContentPane().removeAll();
				f.setJMenuBar(mBar);
				f.getContentPane().add( bp );
				
				//Repaints and revalidates for the new board
				f.repaint();
				f.revalidate();
			}
			
		} catch(FileNotFoundException ex){
			//JOptionPane.showConfirmDialog(null, "Messed Up Case 1", "pls", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			ex.printStackTrace();
		} catch(IOException ex){
			//JOptionPane.showConfirmDialog(null, "Messed Up Case 2", "pls", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			//If there is a mismatch between the serialized object and the current board type
			JOptionPane.showConfirmDialog(null, "The Save and the Board seem to have mismatched versions!", "Mismatched Versions!", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE);
			e.printStackTrace();
		}
	}

}
