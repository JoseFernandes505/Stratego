//Created by Jose Fernandes

package stratego;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//Main Game Running class
public class Game {

	public static void main(String[] args) {
		//Creates a default frame for the operations to take place in
		//(Main Menu, Settings, Game, Win/Lose Screens, etc)
		JFrame f = new JFrame("Prarie Dog");	
		
		//Creates a game within the current frame
		createGameFrame(f);
		
		//Sets frame properties
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		f.setResizable(false);
	    f.setSize( 1000, 1000); 
	    f.setVisible( true );
	    f.setLocationRelativeTo(null);
		

	}
	
	public static void createGameFrame( JFrame f){
		//Creates a board and a board panel with the board
		BoardData b = new BoardData();
		b.populateTestBoard();					//TODO Only for testing, should be removed
		BoardPanel bp = new BoardPanel(b);
		
		//Creates a menu for a "New" option and adds it
		JMenu newMenu = new JMenu("New");
		JMenuBar menuBar = new JMenuBar();

		//Adds 2 sub menus to the new option 
		newMenu.add(new JMenuItem("Test"));
		newMenu.add(new JMenuItem("Butter"));
		menuBar.add(newMenu);
		
		//Creates the option menu
		JMenu optionsMenu = new JMenu("Options");
		menuBar.add(optionsMenu);
		
		//Creates the save menu
		JMenu saveMenu = new JMenu("Save");
		menuBar.add(saveMenu);
		
		//Sets the menu bar as the frame's menubar
		f.setJMenuBar( menuBar );
		
		//Sets a new layout and adds the boardpanel in the frame
		f.setLayout(new BorderLayout());
		f.add(bp, BorderLayout.CENTER);
	}

}
