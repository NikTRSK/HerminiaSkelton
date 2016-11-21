package utilities;

import java.io.Serializable;
import java.util.ArrayList;

public class GameInstance implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1709672852475896413L;
	ArrayList<PlayerInstance> players = new ArrayList<PlayerInstance>();
	Integer gameID;
	GameTimer timer;
	Boolean timerExpired;
	Integer gameMode;
	
	public GameInstance(PlayerInstance p1, PlayerInstance p2, Integer id) {
		players.add(p1);
		if (p2 != null) {
			players.add(p2);
			gameMode = 0;
		}
		gameID = id;
		timerExpired = false;
		timer = new GameTimer(this);
		timer.start();
	}
	
	public ArrayList<PlayerInstance> getPlayers() {
		return this.players;
	}
	
	public ArrayList<String> getPlayerUsernames() {
		ArrayList<String> p = new ArrayList<String>();
		for (int i = 0; i < players.size(); i++)
			p.add(players.get(i).getUsername());
		return p;
	}
	
	public boolean allPlayersMadeAMove() {
		return (players.get(0).madeAMove && players.get(1).madeAMove());
	}
	
	public void setMoveForPlayer(String username, Integer move) {
		for (PlayerInstance p : players) {
			if (p.getUsername().equalsIgnoreCase(username)) {
				p.setMove(move);
				break;
			}
		}
	}
	
	public Integer getOtherPlayersMove(String username) {
		if (!players.get(0).getUsername().equalsIgnoreCase(username))
			return players.get(1).getMove();
		else
			return players.get(0).getMove();
	}
	
	public Integer getInstanceID() {
		return this.gameID;
	}
	
	public void startFinalBattle() {
		System.out.println("Starting final battle");
	}
}
