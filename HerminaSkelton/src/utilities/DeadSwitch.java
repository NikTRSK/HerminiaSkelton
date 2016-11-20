package utilities;

import java.io.Serializable;

public class DeadSwitch implements Serializable{
	private static final long serialVersionUID = 7932533276611830461L;
	
	// Members.
	private Integer player;
	private Integer newCP;
	
	// Constructor.
	public DeadSwitch(Integer player, Integer newCP){
		this.player = player;
		this.newCP = newCP;
	}
	
	// Getters.
	public Integer getCP(){
		return newCP;
	}
	public Integer getPlayerNum(){
		return player;
	}
}
