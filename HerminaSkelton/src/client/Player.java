package client;

import java.util.Random;
import java.util.Vector;

import javax.swing.ImageIcon;

import AllCPs.CP;
import libraries.ImageLibrary;
import map.MapConstants;
import map.MapNode;

public class Player extends MapNode {
	private String username;
	private int assignmentsLeft;
	long randomSeed;
	Vector<CP> CPs;
	int x;
	int y;
	ImageIcon avatar;
	Random rand;
	
	public Player(String name, long randomSeed, int avatarNum){
		super(0, 0, 0, 0);
		
		username = name;
		assignmentsLeft = Constants.StartingAssignments;
		avatar = new ImageIcon(Constants.avatar[avatarNum]);
		rand = new Random(randomSeed);
		CPs = new Vector<CP>();
		x = 5;
		y = 5;
		
		mImage = ImageLibrary.getImage(MapConstants.AVATAR);
	}
	
	public void setSize(int inWidth, int inHeight) {
		width = inWidth;
		height = inHeight;
		trueX = x * width;
		trueY = y * height;
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
		calculatePosition();
	}
	public void setY(int y){
		this.y = y;
		calculatePosition();
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
	
	//movement stuff
	private void calculatePosition() {
		trueX = x * width;
		trueY = y * height;
	}
	
	public void moveUp() {
		y--;
		calculatePosition();
	}
	
	public void moveDown() {
		y++;
		calculatePosition();
	}
	
	public void moveLeft() {
		x--;
		calculatePosition();
	}
	
	public void moveRight() {
		x++;
		calculatePosition();
	}
}
