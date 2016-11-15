package client;

import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;

import AllCPs.CP;

public class Player {
	private String username;
	private int assignmentsLeft;
	long randomSeed;
	Vector<CP> CPs;
	int x;
	int y;
	ImageIcon avatar;
	Random rand;
	
	public Player(String name, long randomSeed, int avatarNum){
		username = name;
		assignmentsLeft = Constants.StartingAssignments;
		avatar = new ImageIcon(Constants.avatar[avatarNum]);
		rand = new Random(randomSeed);
		CPs = new Vector<CP>();
		x = 10;
		y = 10;
	}
	
	public String getName(){
		return username;
	}
	public int assignments(){
		return assignmentsLeft;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public Vector<CP> getCP(){
		return CPs;
	}
	
	public void setX(int x){
		this.x = x;
	}
	public void setY(int y){
		this.y = y;
	}
	public void deductAssignment(){
		assignmentsLeft--;
	}
	public void addCP(CP newCP){
		CPs.add(newCP);
	}
	public void draw(){
		//TODO
	}
}
