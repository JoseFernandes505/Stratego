//Created by Jose Fernandes

package stratego;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

//Main Game Running class
public class Game implements ActionListener{

	static JFrame f;
	static BoardData board;
	
	public static void main(String[] args) {
		//Creates a default frame for the operations to take place in
		//(Main Menu, Settings, Game, Win/Lose Screens, etc)
		f = new JFrame("Prarie Dog");	
		
		board = new BoardData();
		GamePanel bp = new GamePanel(board);
		MenuPanel mp = new MenuPanel( new Game() );
		
		//FIXME		
		f.setSize( 1200, 1000); 
		f.add( mp, BorderLayout.CENTER );
		
		//Sets frame properties
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		f.setResizable( true );
	    
	    f.setVisible( true );
	    f.setLocationRelativeTo(null);
	    

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		System.out.println("Button clicked!");
		
	}
	

}
