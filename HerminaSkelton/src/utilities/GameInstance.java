package utilities;

import java.io.Serializable;
import java.util.ArrayList;

import client.Player;
import server.FinalBattleManager;

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
	ArrayList<Player> FBPlayers;
	FinalBattleManager finalBattleManager;
	
	public GameInstance(PlayerInstance p1, PlayerInstance p2, Integer id) {
		players.add(p1);
		if (p2 != null) {
			players.add(p2);
			gameMode = 0;
		}
		FBPlayers = new ArrayList<Player>();
		gameID = id;
		timerExpired = false;
		timer = new GameTimer(this);
//		timer.start();
	}
	
	public ArrayList<PlayerInstance> getPlayers() {
		return this.players;
	}
	
	public void startTimer() {
		timer.start();
	}
	
	public GameTimer getTimer() {
		return timer;
	}
	
	public void addPlayerToFinalBattle(Player p) {
		FBPlayers.add(p);
		if (FBPlayers.size() == players.size())
			startFinalBattle();
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
	
	public void timerOut() {
		for (PlayerInstance player : players)
			player.sendData(new DataPacket<Boolean>(utilities.Commands.TIMER_OUT, true));
	}
	
	public void startFinalBattle() {
		if (gameMode == 0) {
			// TODO
		} else {
			finalBattleManager = new FinalBattleManager(FBPlayers.get(0), FBPlayers.get(1), this);
			for (PlayerInstance player : players)
				player.startFinalBattle();
		}
	}
	
	public void sendFinalBattleUpdate(FinalBattleState fbs) {
		for (PlayerInstance player : players)
			player.sendData(new DataPacket<FinalBattleState>(utilities.Commands.FINAL_BATTLE, fbs));
	}
	
	public void updatePlayerTimers(Integer time) {
		System.out.println("TIMER " + time);
		for (PlayerInstance player : players)
			player.updateTimer(time);
	}
	
	public void FBMReceiveAction(PlayerAction pa) {
		this.finalBattleManager.recieveAction(pa);
	}
	
	public void FBMReceiveDeadSwitch(DeadSwitch ds) {
		this.finalBattleManager.recieveDeadSwitch(ds);
	}
}
