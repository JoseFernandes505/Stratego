//Created by Jose Fernandes

package stratego;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

//Main Game Running class
public class Game implements ActionListener{

	private JFrame f;
	private JMenuBar mBar;
	private JMenuItem saveGameOption;
	private JMenuItem loadGameOption;
	private JMenuItem quitGameOption;
	
	private BoardData board;
	private GamePanel bp;
	private MenuPanel mp;
	private SettingsPanel sp;
	
	
	public void Begin(){
		//Creates a default frame for the operations to take place in
		//(Main Menu, Settings, Game, Win/Lose Screens, etc)
		f = new JFrame("Prarie Dog");	
		
		//Creates the initial panel to populate the frame, the menu panel
		mp = new MenuPanel( this );
		
		//FIXME		
		f.setSize( 1200, 1000); 
		f.getContentPane().add( mp );
		
		//Sets frame properties
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		f.setResizable( false );
	    f.setVisible( true );
	    f.setLocationRelativeTo(null); 
	    
		
		
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		
		String source = ae.getActionCommand();
		
		//If from Main Menu -> Start Game
		if( source.equals( "Start Game" ) ){
			board = new BoardData();
			bp = new GamePanel(board);
			
			f.getContentPane().remove( mp );
			f.getContentPane().add( bp );
			
			f.repaint();
			f.revalidate();
		//If from Main Menu -> Settings
		} else if( source.equals( "Settings" ) ){
			sp = new SettingsPanel();
			
			f.getContentPane().remove( mp );
			f.getContentPane().add( sp );
			
			f.repaint();
			f.revalidate();
		}
		
	}
	

}