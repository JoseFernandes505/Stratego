//Created by Jose Fernandes

package stratego;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class Game {

	public static void main(String[] args) {
		JMenu menu;
		BoardData b = new BoardData();
		//TODO remove
		b.populateTestBoard();
		BoardPanel bp = new BoardPanel(b);
		JMenuBar menuBar = new JMenuBar();
		
		menu = new JMenu("New");
		menuBar.add(menu);
		menu.add(new JMenu("my asssss"));
		menu.add(new JMenu("I am a dragon"));
		
		menu = new JMenu("Options");
		menuBar.add(menu);
		
		menu = new JMenu("Save");
		menuBar.add(menu);
		
		
		
		JFrame f = new JFrame("I am a Dragon, you are now Asian");	
		
		f.setLayout(new BorderLayout());
		
		f.add(bp, BorderLayout.CENTER);
		f.add(menuBar, BorderLayout.NORTH);
		
		f.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		f.setResizable(false);
	    f.setSize( 1000, 1000); // set frame size
	    f.setVisible( true ); // display frame
	    f.setLocationRelativeTo(null);
	}

}
