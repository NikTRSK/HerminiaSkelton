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
	
	// In case the Player wants/needs to switch CP.
	public Player player;
	
	// Constructor.
	public FinalBattleState(CP cp1, CP cp2, CP cp3, CP cp4, Player player) {
		this.cp1 = cp1;
		this.cp2 = cp2;
		this.cp3 = cp3;
		this.cp4 = cp4;
		
		this.player = player;
	}
}
