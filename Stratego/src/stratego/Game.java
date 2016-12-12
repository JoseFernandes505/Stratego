//Created by Jose Fernandes

package stratego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
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
	
	
	public void Begin(){
		//Creates a default frame for the operations to take place in
		//(Main Menu, Settings, Game, Win/Lose Screens, etc)
		f = new JFrame("Prarie Dog");	
		
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
					
		//FIXME		
		f.setSize( 1200, 1000); 
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
			board = new BoardData( reversePieces );
			bp = new GamePanel(board);
			
			f.getContentPane().removeAll();
			f.setJMenuBar(mBar);
			f.getContentPane().add( bp );
			
			f.repaint();
			f.revalidate();
		//If from Main Menu -> Settings
		} else if( source.equals( "Settings" ) ){
			sp = new SettingsPanel( this );
			
			f.getContentPane().removeAll();
			f.getContentPane().add( sp );
			
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
		//Returns to the main menu
		//Settings -> Main Menu
		//GamePanel -> Main Menu
		} else if( source.equals( "Back To Main Menu" ) || source.equals( "Quit Game" ) ){
			mp = new MenuPanel( this );
			
			f.getContentPane().removeAll();
			f.getContentPane().add( mp );
			
			f.repaint();
			f.validate();
		} else if( source.equals( "Save Game" ) ){
			
			saveGamePopup();
			
		}
		
	}
	
	//Returns whether the pieces are reversed
	public boolean reversedPieces(){
		return reversePieces;
	}
	
	//Makes a popup to allow the user to save their game
	public void saveGamePopup(){
		
		try{			
			ObjectOutputStream oos;
			File f = new File("./saves");
			boolean exists = false;
			boolean resultBool = true;
			
			String fileName = JOptionPane.showInputDialog(	null,
															"Please enter the name of your save file ",
															"Save Game",
															JOptionPane.INFORMATION_MESSAGE );
			
			File[] flist = f.listFiles();
			
			for( int i = 0; i < flist.length; i++ ){
				System.out.println(  );
				if( flist[i].getName().equals( fileName ) ){
					exists = true;
				}
			}
			
			if( exists ){
				int result = JOptionPane.showConfirmDialog(	null,
															"This filename already exists. Do you want to continue?",
															"File already exists!",
															JOptionPane.YES_NO_OPTION);
				resultBool = ( result == 0 ? true : false );
				
				System.out.println( result );
			}
			
			if( resultBool ){
				oos = new ObjectOutputStream( new BufferedOutputStream( new FileOutputStream( "./saves/" + fileName ) ) );
				
				oos.writeObject( board );
				
				oos.close();
				
				System.out.println("It did it lol");
			}
			
			
		}catch(FileNotFoundException ex){
			ex.printStackTrace();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		
		
	}

}