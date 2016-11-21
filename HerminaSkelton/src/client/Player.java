package client;

import java.io.Serializable;
import java.util.Vector;

import AllCPs.CP;
import libraries.ImageLibrary;
import map.MapConstants;
import map.MapNode;

public class Player extends MapNode implements Serializable{

	private static final long serialVersionUID = 1600475272113020639L;
	private String username;
	private Integer assignmentsLeft;
	private Vector<CP> CPs;
	private Integer x;
	private Integer y;
	private Integer steps;
	
	public Player(String name){
		super(0, 0, 0, 0);
		
		username = name;
		assignmentsLeft = Constants.StartingAssignments;

		CPs = new Vector<CP>();
		CPs.add(Constants.generateCP(Constants.rand.nextInt(Constants.numCPs)));
		
		x = MapConstants.START_X;
		y = MapConstants.START_Y;
		
		steps = new Integer(0);
		
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
	
	public void incrementSteps(){
		steps++;
	}
	
	//generates a score
	public int generateScore(){
		int score = 1;
		for(int i = 0; i<CPs.size(); i++)score*=(CPs.get(i).getLevel()*5);
		score*=(assignmentsLeft*3);
		score*=steps;
		return score;
	}
}
