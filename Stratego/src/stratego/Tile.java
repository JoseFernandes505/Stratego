//Created by Jose Fernandes

package stratego;
import java.lang.String;

public class Tile {
	private boolean passable;
	private boolean inRange = false;
	private Token token;
	
	
	//Default constructor, sets whether tile is passable or if it has a token
	public Tile(boolean pass, Token to){
		passable = pass;
		token = to;
	}
	
	//Returns whether the tile is passable (the lakes are not) 
	public boolean isPassable(){
		return passable;
	}
	
	//Returns the token on this tile
	public Token getToken(){
		return token;
	}
	
	//Sets the token on this tile to be some parameter
	public void addToken(Token tok){
		token = tok;
	}
	
	//Sets the token to null
	public void removeToken(){
		token = null;
	}
	
	//Checks tile's inrange boolean
	public boolean inRange(){
		return inRange;
	}
	
	//Sets the tile's inrange boolean to an inputted boolean
	public void setInRange(boolean range){
		inRange = range;
	}
	
	
}
