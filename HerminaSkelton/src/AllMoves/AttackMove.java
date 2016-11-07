package AllMoves;

import AllCPs.CP;
import Client.Constants;

public abstract class AttackMove {
	protected String name;
	protected int type;
	
	//Executes an attack on the enemy pokemon
	public void move(CP attacker, CP defender){
		double damage = attacker.getAttack()*Constants.attackMultiplier;
		damage*=effectiveness(defender.getType());
		if(type==attacker.getType())damage*=1.5;
		
		defender.changeHealth((int)damage*(-1));
	}
	
	//Determines the effectiveness of the move used
	public double effectiveness(int defenderType){
		if(type==0)return 1;
		
		// Moves are super effective to CPs one type ahead
		int weaknessType = type+1;
		if(type==3)weaknessType = 1;
		
		// Moves are resisted by CPs one type below
		int resistType = type-1;
		if(type==1)resistType = 3;
		
		if(defenderType==weaknessType)return 2;
		if(defenderType==resistType)return 0.5;
		
		return 1;
	}

	//Gets the move's name
	public String getName(){
		return name;
	}
}
