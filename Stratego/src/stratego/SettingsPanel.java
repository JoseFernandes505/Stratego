package stratego;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SettingsPanel extends JPanel{
	
	//Panel to hold the options for the settings
	private JPanel buttonsP;
	
	//Settings buttons
	private JButton menuB;
	private JLabel reverseTitle;
	private ButtonGroup reverseGroup;
	private JRadioButton reverseYB;
	private JRadioButton reverseNB;
	private JLabel sizeTitle;
	private ButtonGroup sizeGroup;
	private JRadioButton size1100x1000;
	private JRadioButton size1430x1300;
	private JRadioButton size770x700;
	
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
		sizeGroup = new ButtonGroup();
		size1100x1000 = new JRadioButton("1100 x 1000");
		size1430x1300 = new JRadioButton("1430 x 1300");
		size770x700 = new JRadioButton("770 x 700");
		reverseTitle = new JLabel();
		sizeTitle = new JLabel();
		
		//Adds action listeners to the buttons
		reverseYB.addActionListener(g);
		reverseNB.addActionListener(g);
		size1100x1000.addActionListener(g);
		size1430x1300.addActionListener(g);
		size770x700.addActionListener(g);
		menuB.addActionListener(g);
		
		//Adds text to the labels
		reverseTitle.setText("Reversing Rank");
		sizeTitle.setText("Setting Frame Size");
		
		//Adds the buttons to the group and sets the default
		reverseGroup.add( reverseYB );
		reverseGroup.add( reverseNB );
		reverseGroup.setSelected(reverseYB.getModel(), !g.reversedPieces());
		reverseGroup.setSelected(reverseNB.getModel(), g.reversedPieces());
		
		//Adds the buttons to the size group and sets default selected
		sizeGroup.add( size1100x1000 );
		sizeGroup.add( size1430x1300 );
		sizeGroup.add( size770x700 );
		sizeGroup.setSelected( size1100x1000.getModel() , (g.getSize().equals( new Dimension(1100,1000) ) ? true : false) );
		sizeGroup.setSelected( size1430x1300.getModel() , (g.getSize().equals( new Dimension(1430,1300) ) ? true : false) );
		sizeGroup.setSelected( size770x700.getModel() , (g.getSize().equals( new Dimension(770,700) ) ? true : false) );
		
		//Initializes the BG image
		bg = new ImageIcon( getClass().getResource( "/res/menu_bg.jpg" ) ).getImage();
		
		//Initializes the constraint
		GridBagConstraints gbc = new GridBagConstraints();
		
		//Adds the Rank title
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 0;
		buttonsP.add( reverseTitle, gbc );
		
		//Adds the two radio buttons for reversed or standard pieces
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridy = 1;
		gbc.insets = new Insets(0,15,15,15);
		buttonsP.add(reverseYB, gbc);
		gbc.gridx = 1;
		buttonsP.add(reverseNB, gbc);
		
		//Adds the Size title
		gbc.fill = GridBagConstraints.CENTER;
		gbc.gridwidth = 3;
		gbc.gridx = 0;
		gbc.gridy = 2;
		buttonsP.add( sizeTitle, gbc );
		
		//Adds the resizing buttons
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridwidth = 1;
		gbc.gridy = 3;
		gbc.gridx = 0;
		buttonsP.add(size770x700, gbc);
		gbc.gridx = 1;
		buttonsP.add(size1100x1000, gbc);
		gbc.gridx = 2;
		buttonsP.add(size1430x1300, gbc);
		
		//Adds the next elements
		gbc.gridwidth = 3;
		gbc.gridy = 4;
		gbc.gridx = 0;

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
