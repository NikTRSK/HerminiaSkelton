package utilities;

import java.io.Serializable;

import server.ServerClientCommunicator;

public class PlayerInstance implements Serializable {
	private static final long serialVersionUID = 9073307226021162524L;
	String userName;
	Boolean madeAMove;
	Integer move;
	Integer gameType;
	ServerClientCommunicator playerThread;
	
	public PlayerInstance(String username, ServerClientCommunicator pt) {
		this.userName = username;
		this.madeAMove = false;
		this.gameType = -1;
		this.playerThread = pt;
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
	
	public void updateTimer(Integer time) {
		playerThread.sendData(new DataPacket<Integer>(utilities.Commands.TIME_UPDATE, time));
	}
	
	public void startFinalBattle() {
		playerThread.sendData(new DataPacket<Boolean>utilities.Commands.FINAL_BATTLE, true)
	}
	
	public void sendData(DataPacket<?> dp) {
		playerThread.sendData(dp);
	}
	
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
