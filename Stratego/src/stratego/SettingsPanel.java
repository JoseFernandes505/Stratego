package stratego;

import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SettingsPanel extends JPanel{
	
	//Panel to hold the options for the settings
	private JPanel buttonsP;
	
	//Settings buttons
	private JButton menuB;
	private ButtonGroup reverseGroup;
	private JRadioButton reverseYB;
	private JRadioButton reverseNB;
	
	//The background image
	private Image bg;
	
	public SettingsPanel(Game g){
		//Initializes the button panel and sets it to be a gridbag layout
		buttonsP = new JPanel();
		buttonsP.setLayout( new GridBagLayout() );
		
		//Creates the Main Menu Buttons
		menuB = new JButton("Back To Main Menu");
		reverseGroup = new ButtonGroup();
		reverseYB = new JRadioButton("Standard Ranks");
		reverseNB = new JRadioButton("Reversed Ranks");
		
		//Adds action listeners to the buttons
		reverseYB.addActionListener(g);
		reverseNB.addActionListener(g);
		menuB.addActionListener(g);
		
		//Adds the buttons to the group and sets the default
		reverseGroup.add( reverseYB );
		reverseGroup.add( reverseNB );
		reverseGroup.setSelected(reverseYB.getModel(), !g.reversedPieces());
		reverseGroup.setSelected(reverseNB.getModel(), g.reversedPieces());
		
		//Initializes the BG image
		bg = new ImageIcon( getClass().getResource( "/res/menu_bg.jpg" ) ).getImage();
		
		//Initializes the constraint
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Adds the two radio buttons for reversed or standard pieces
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.insets = new Insets(15,15,15,15);
		buttonsP.add(reverseYB, gbc);
		gbc.gridx = 1;
		buttonsP.add(reverseNB, gbc);
		
		//Adds the next elements
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 2;
		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.insets = new Insets(0,15,15,15);
		buttonsP.add(menuB, gbc);
		
		//Sets the border and adds the buttons to the panel
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
