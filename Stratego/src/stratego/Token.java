package stratego;
import java.lang.String;
import java.awt.Color;

public class Token{
	private String tokenName;
	private String iconPath;
	private int tokenNum;
	private Color teamColor;
	private boolean teamBool;
	int movementRange = 1;
	
	//Default constructor, sets name and rank
	public Token(String name, int num, boolean team){
		tokenName = name;
		tokenNum = num;
		teamBool = team;
		teamColor = (team ? Color.RED : Color.BLUE);
		iconPath = tokenNum + "_" + tokenName + "_" + (team ? "red" : "blue");
	}
	
	//Constructor for bombs and scouts, changes the movement range
	public Token(String name, int num, boolean team, int range){
		this(name, num, team);
		movementRange = range;
	}
	
	//Returns the tile's name
	public String getName(){
		return tokenName;
	}
	
	//Returns the rank of the token
	public int getNum(){
		return tokenNum;
	}
	
	//Returns the range of the token
	public int getRange(){
		return movementRange;
	}
	
	//Returns the color of the tile
	public Color getColor(){
		return teamColor;
	}
	
	//Returns a boolean based on the team color (Red == true, Blue == false)
	public boolean getTeam(){
		return teamBool;
	}
	
	public String getPathname(){
		return iconPath;
	}
}
