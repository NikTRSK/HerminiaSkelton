package utilities;

import java.io.Serializable;

public class PlayerInstance implements Serializable {
	private static final long serialVersionUID = 9073307226021162524L;
	String userName;
	Boolean madeAMove;
	Integer move;
	Integer gameType;
//	GameInstance gameInstance;
	
	public PlayerInstance(String username) {
		this.userName = username;
		this.madeAMove = false;
		this.gameType = -1;
	}
	
	public String getUsername() {
		return this.userName;
	}
	
	public Boolean madeAMove() {
		return this.madeAMove;
	}
	
	public void setGameType(Integer type) {
		this.gameType = type;
	}
	
	public Integer getGameType() {
		return this.gameType;
	}
/*	public void setMadeAMove() {
		this.madeAMove = true;
	}*/
	
	public void clearMadeAMove() {
		this.madeAMove = false;
	}
	
	public void setMove(Integer move) {
		this.move = move;
		this.madeAMove = true;
	}
	
	public Integer getMove() {
		return this.move;
	}
}
