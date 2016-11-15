package AllCPs;

import javax.swing.ImageIcon;

import Client.Constants;

public abstract class CP {
	protected String name;
	protected ImageIcon sprite;
	protected int type;
	protected int level;
	protected int experience;
	protected int healthPoints;
	protected int attack;
	protected int speed;
	protected int currHealth;	
	protected int[] moves;
	
	//Initializes the level and experience of the CP
	public CP(int level){
		this.experience = Constants.levelThresholds[level];
		this.level = level;
	}
	
	//Getter Functions
	public String getName(){
		return name;
	}
	public int getType(){
		return type;
	}
	public int getAttack(){
		return attack;
	}
	public int getSpeed(){
		return speed;
	}
	public int getMaxHealth(){
		return healthPoints;
	}
	public int getLevel(){
		return level;
	}
	public int[] getAttackMoves(){
		return moves;
	}
	public int getHealth(){
		return currHealth;
	}
	public ImageIcon getSprite(){
		return sprite;
	}
	
	//Health Modifiers
	public void changeHealth(int change){
		healthPoints+=change;
	}
	public void heal(){
		currHealth = healthPoints;
	}
	
	//XP, level and stat Modifiers
	public void addXP(int exp){
		experience=+exp;
	}
	public boolean levelUp(){
		//Max level is 10
		if(level==10)return false;
		
		//If current xp is higher than the threshold for that level, level up
		if(experience>=Constants.levelThresholds[level]){
			level++;
			return true;
		}else{
			return false;
		}
	}
	public void setLevel(int level){
		this.level = level;
		experience = Constants.levelThresholds[level];
	}
	protected abstract void updateStats(); //Stat growth rate varies by CP
}
