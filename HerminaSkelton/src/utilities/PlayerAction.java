package utilities;

import java.io.Serializable;

import AllMoves.AttackMove;

public class PlayerAction implements Serializable{
	private static final long serialVersionUID = -9091937529471936091L;

	// Is this from player 1 or player 2?
	private Integer player;
	
	// 1 is for attack.
	// 2 is for switch.
	private Integer type;
	
	// In case type is 1.
	private AttackMove move;
	
	// The player's CP.
	private Integer activeCP;
	
	// If type 1, this is the enemy CP.
	// If type 2, this is the CP being switched in.
	private Integer target;
	
	// Constructor.
	public PlayerAction(Integer player, Integer type, AttackMove move,
						Integer activeCP, Integer target){
		
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
	public Integer getActive(){
		return activeCP;
	}
	public Integer getTarget(){
		return target;
	}
}
