package utilities;

import java.util.ArrayList;

public class GameInstance {
	ArrayList<PlayerInstance> players = new ArrayList<PlayerInstance>();
	Integer gameID;
	GameTimer timer;
	Boolean timerExpired;
	
	public GameInstance(PlayerInstance p1, PlayerInstance p2, Integer id) {
		players.add(p1);
		players.add(p2);
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
		p.add(players.get(0).getUsername());
		p.add(players.get(1).getUsername());
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
