package utilities;

import java.util.ArrayList;

public class GameInstance {
	ArrayList<String> players = new ArrayList<String>(2);
	
	public GameInstance(String p1, String p2) {
		players.add(p1);
		players.add(p2);
	}
	
	public ArrayList<String> getPlayers() {
		return this.players;
	}
}
