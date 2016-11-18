package utilities;

import AllCPs.CP;
import AllMoves.AttackMove;

public class PlayerAction {
	
	// Is this from player 1 or player 2?
	private Integer player;
	
	// 1 is for attack.
	// 2 is for switch.
	private Integer type;
	
	// In case type is 1.
	private AttackMove move;
	
	// The player's CP.
	private CP activeCP;
	
	// If type 1, this is the enemy CP.
	// If type 2, this is the CP being swithced in.
	private CP target;
	
	// Constructor.
	public PlayerAction(Integer player, Integer type, AttackMove move,
						CP activeCP, CP target){
		
		this.player   = player;
		this.type     = type;
		this.move     = move;
		this.activeCP = activeCP;
		this.target   = target;
		
	}
	
	// Getter methods.
	public Integer getPlayerNum(){
		return player;
	}
	public Integer getType(){
		return type;
	}
	public AttackMove getMove(){
		return move;
	}
	public CP getActive(){
		return activeCP;
	}
	public CP getTarget(){
		return target;
	}
}
