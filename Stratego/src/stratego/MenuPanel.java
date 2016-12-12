package stratego;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
	
	private JPanel buttonsP;
	
	private JButton startGameB;
	private JButton loadGameB;
	private JButton settingsB;
	private JButton quitB;
	private ImageIcon stratego_icon;
	private JLabel stratego_title;
	
	private Image bg;
	
	public MenuPanel(ActionListener ae){
		
		
		buttonsP = new JPanel();

		buttonsP.setLayout( new GridBagLayout() );
		
		//Creates the Main Menu Buttons
		startGameB = new JButton("Start Game");
		loadGameB = new JButton("Load Game");
		settingsB = new JButton("Settings");
		quitB = new JButton("Quit");
		
		//Sets up the title
		stratego_icon = new ImageIcon(getClass().getResource( "/res/stratego_title.png" ));
		stratego_title = new JLabel();
		stratego_title.setIcon( stratego_icon );
		
		//Adds action listeners to the buttons
		startGameB.addActionListener(ae);
		loadGameB.addActionListener(ae);
		settingsB.addActionListener(ae);
		quitB.addActionListener(ae);

		//Initializes the BG image
		bg = new ImageIcon( getClass().getResource( "/res/menu_bg.jpg" ) ).getImage();
		
		GridBagConstraints gbc = new GridBagConstraints();
		
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(15,15,15,15);
		
		buttonsP.add(stratego_title, gbc);
		
		gbc.gridy = 1;
		gbc.insets = new Insets(0,15,15,15);
		buttonsP.add(startGameB, gbc);
		
		gbc.gridy = 2;
		buttonsP.add(loadGameB, gbc);
		
		gbc.gridy = 3;
		buttonsP.add(settingsB, gbc);
		
		gbc.gridy = 4;
		buttonsP.add(quitB, gbc);
		
		buttonsP.setBorder( BorderFactory.createRaisedBevelBorder() );
		setLayout(new GridBagLayout());
		add(buttonsP);
		
	}
	
	@Override
	protected void paintComponent(Graphics g){
		
		super.paintComponent(g);
		g.drawImage(bg, 0, 0, this.getWidth(), this.getHeight(), null);
		
	}
	
}
