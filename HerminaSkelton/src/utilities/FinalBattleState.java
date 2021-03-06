package utilities;

import java.io.Serializable;

import AllCPs.CP;
import client.Player;

public class FinalBattleState implements Serializable{
	private static final long serialVersionUID = 1664909770205358784L;
	
	// Renders the current state of each CP.
	public CP cp1; // Player 1.
	public CP cp2; // Player 2.
	public CP cp3; // Miller 1.
	public CP cp4; // Miller 2.
	public Integer[] CPHealth; // Health wasn't sending
	
	// In case the Player wants/needs to switch CP.
	public Player[] players;
	
	// 1 if the player is still alive
	// 2 if the player is dead but partner is alive
	// 3 if both players are dead (lose the game)
	// 4 if miller is dead (win the game)
	public Integer[] gameStates;
	
	
	
	// Constructor.
	public FinalBattleState(CP cp1, CP cp2, CP cp3, CP cp4, Player[] players, Integer[] states) {
		if (cp1 == null)
			System.out.println("cp1 null");
		this.cp1 = cp1;
		this.cp2 = cp2;
		this.cp3 = cp3;
		this.cp4 = cp4;
		
		this.CPHealth = new Integer[4];
		CPHealth[0] = cp1.getHealth();
		CPHealth[1] = cp2.getHealth();
		CPHealth[2] = cp3.getHealth();
		CPHealth[3] = cp4.getHealth();
		
		this.gameStates = states;
		
		this.players = players;
	}
}
