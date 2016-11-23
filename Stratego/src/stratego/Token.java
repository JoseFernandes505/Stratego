//Created by Jose Fernandes

package stratego;
import java.lang.String;
import java.awt.Color;

public class Token{
	private String tokenName;
	private String iconPath;
	private int rank;
	private boolean teamBool;
	private boolean moving = false;
	private int movementRange = 1;
	
	//Default constructor, sets name and rank
	public Token(String name, int num, boolean team){
		tokenName = name;
		rank = num;
		teamBool = team;			//True = blue, False = red
		iconPath = rank + "_" + tokenName + "_" + (team ? "red" : "blue");
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
	public int getRank(){
		return rank;
	}
	
	//Returns the range of the token
	public int getRange(){
		return movementRange;
	}
	
	//Returns a boolean based on the team color (Red == true, Blue == false)
	public boolean getTeam(){
		return teamBool;
	}
	
	public String getPathname(){
		return iconPath;
	}
	
	public boolean isMoving(){
		return moving;
	}
	
	public void startMoving(){
		moving = true;
	}
	
	public void stopMoving(){
		moving = false;
	}
	
	public int doBattle(Token t){
		
		if(rank == 3 && t.getRank() == 11){
			return 1;
		}
		
		//if(rank == 1 && )
		
		
		
		return -1;
	}
}
