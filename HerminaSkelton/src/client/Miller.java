package Client;

import java.util.Vector;

import javax.swing.ImageIcon;

import AllCPs.CP;

public class Miller {
	private String name;
	Vector<CP> CPs;
	ImageIcon sprite;
	
	//Constructor
	public Miller(int difficulty){
		name = "Jeffrey Miller, PhD";
		sprite = new ImageIcon(Constants.millerImage);
		CPs = new Vector<CP>();
		generateCPs(difficulty);
	}
	
	//Generates Miller's CP army
	private void generateCPs(int difficulty){
		double numCPs = 3 + Constants.numberMultiplier*(double)difficulty;
		int level = Constants.levelMultiplier*difficulty;
		for(int i = 0; i<numCPs; i++){
			int CPSeed = Constants.rand.nextInt(Constants.numCPs);
			CPs.add(Constants.generateCP(CPSeed));
			int levelSeed = Constants.rand.nextInt(3)-3;
			CPs.get(i).setLevel(level+levelSeed);
		}
	}
	
	//Getter Functions
	public Vector<CP> getCP(){
		return CPs;
	}
	public String getName(){
		return name;
	}
	public ImageIcon getSprite(){
		return sprite;
	}
	
}
