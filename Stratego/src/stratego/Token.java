//Created by Jose Fernandes

package stratego;
import java.lang.String;
import java.awt.Color;

public class Token{
	private String iconPath;
	private String bgPath;
	private int rank;
	private boolean teamBool;
	private boolean moving = false;
	private int movementRange = 1;
	
	//Default constructor, sets name and rank
	public Token(int num, boolean team){
		rank = num;
		teamBool = team;			//True = blue, False = red
		iconPath = rank + "_" + getRankName().toLowerCase() + "_" + (team ? "red" : "blue");
		bgPath = "bg_" + (team ? "red" : "blue");
		
		if(rank == 11 || rank == 0){
			movementRange = 0;
		}
		
		if( rank == 2 ){
			movementRange = 10;
		}
	}
	
	//Constructor for bombs and scouts, changes the movement range
	public Token(int num, boolean team, int range){
		this(num, team);
		movementRange = range;
	}
	
	//Returns the rank of the token
	public int getRank(){
		return rank;
	}
	
	//Returns the range of the token
	public int getRange(){
		return movementRange;
	}
	
	//Returns a boolean based on the team color (Blue == true, Red == false)
	public boolean getTeam(){
		return teamBool;
	}
	
	public String getPathname(){
		return iconPath;
	}
	
	public String getBgPathname(){
		return bgPath;
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

		//If a Miner battles a mine, the Miner wins
		if(rank == 3 && t.getRank() == 11){
			return 1;
		}
		//If a Spy battles a marshal, the spy wins
		if(rank == 1 && t.getRank() == 10){
			return 1;
		}
		
		return rank - t.getRank();

	}
	
	//Returns the title of the piece based on its rank
	public String getRankName(){
		//Checks the rank and returns the correct name
		switch(rank){
			case 0:
				return "flag";
			case 1:
				return "spy";
			case 2:
				return "scout";
			case 3:
				return "miner";
			case 4:
				return "sergeant";
			case 5:
				return "lieutenant";
			case 6:
				return "captain";
			case 7:
				return "major";
			case 8:
				return "colonel";
			case 9:
				return "general";
			case 10:
				return "marshal";
			case 11:
				return "bomb";
		}
		
		return "";
	}
}
