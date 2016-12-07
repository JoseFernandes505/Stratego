//Created by Jose Fernandes

package stratego;

import java.awt.BorderLayout;
import javax.swing.JFrame;

//Main Game Running class
public class Game {

	static JFrame f;
	static BoardData board;
	
	public static void main(String[] args) {
		//Creates a default frame for the operations to take place in
		//(Main Menu, Settings, Game, Win/Lose Screens, etc)
		f = new JFrame("Prarie Dog");	
		
		board = new BoardData();
		GamePanel bf = new GamePanel(board);
		
		//FIXME		
		f.setSize( 1200, 1000); 
		f.add( bf, BorderLayout.CENTER );
		
		//Sets frame properties
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		f.setResizable( false );
	    
	    f.setVisible( true );
	    f.setLocationRelativeTo(null);
	    

	}

}
