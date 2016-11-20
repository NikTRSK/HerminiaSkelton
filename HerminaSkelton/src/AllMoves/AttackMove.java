package AllMoves;

import java.io.Serializable;

import javax.swing.JTextArea;

import AllCPs.CP;
import client.Constants;

public abstract class AttackMove implements Serializable{
	private static final long serialVersionUID = 2597384860635477608L;
	protected String name;
	protected Integer type;
	
	//Executes an attack on the enemy pokemon
	public void move(CP attacker, CP defender, JTextArea text){
		double damage = attacker.getAttack()*Constants.attackMultiplier;
		damage*=effectiveness(defender.getType());
		if(type==attacker.getType())damage*=1.5;
		
		defender.changeHealth((int)damage*(-1));
		text.append("\n"+attacker.getName()+" used "+this.name+" for "+damage+" damage");
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
