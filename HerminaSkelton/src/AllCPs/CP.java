package AllCPs;

import java.io.Serializable;

import javax.swing.ImageIcon;

import client.Constants;

public abstract class CP implements Serializable{
	private static final long serialVersionUID = 3562524123400148447L;
	protected String name;
	protected ImageIcon sprite;
	protected Integer type;
	protected Integer level;
	protected Integer experience;
	protected Integer healthPoints;
	protected Integer attack;
	protected Integer speed;
	protected Integer currHealth;	
	protected Boolean real;
	protected int[] moves;
	
	//Initializes the level and experience of the CP
	public CP(int level){
		this.experience = Constants.levelThresholds[level];
		this.level = level;
		this.moves = new int[2];
		this.moves[0] = 0;
		this.moves[1] = Constants.rand.nextInt(Constants.NUM_MOVES)+1;
		this.real = true;
	}
	
	//Getter Functions
	public String getName(){
		return name;
	}
	public int getXP(){
		return experience;
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
//		System.out.println(spiete == null);
		return sprite;
	}
	
	//Health Modifiers
	public void changeHealth(int change){
		currHealth+=new Integer(change);
		if(currHealth<0)currHealth = 0;
	}
	public void heal(){
		currHealth = healthPoints;
	}
	
	//XP, level and stat Modifiers
	public void addXP(int exp){
		experience+=exp;
	}
	public boolean levelUp(){
		//Max level is 10
		if(level==10)return false;
		
		//If current xp is higher than the threshold for that level, level up
		if(experience>=Constants.levelThresholds[level+1]){
			level++;
			this.updateStats();
			return true;
		}else{
			return false;
		}
	}
	public void setLevel(int level){
		this.level = level;
		experience = Constants.levelThresholds[level];
	}
	public boolean isReal(){
		return real;
	}
	protected abstract void updateStats(); //Stat growth rate varies by CP
}
