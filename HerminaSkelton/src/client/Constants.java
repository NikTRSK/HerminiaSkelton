package Client;

import java.util.Random;

import AllCPs.CP;
import AllCPs.EdgarLugo;
import AllCPs.EmmaLautz;
import AllCPs.JimmyChen;
import AllCPs.KeerthanHarish;
import AllCPs.PriyankaShah;
import AllMoves.AttackMove;
import AllMoves.FireMove;
import AllMoves.GrassMove;
import AllMoves.WaterMove;

public class Constants {
	//Gamewide random number generator
	public static Random rand = new Random(System.currentTimeMillis());
	
	//Image Files
	public static String[] avatar;
	public static String millerImage;
	
	//Moves and types
	public static AttackMove[] attackMoves = {
			new FireMove(), new GrassMove(), new WaterMove()
	};
	public static String[] type = {
			"Normal", "Water", "Fire", "Grass"
	};
	public static String[] basicMoveNames = {
			"Pound", "Smash", "Beat Up", "Scratch", "Slash"
	};

	//Constants for CP growth rate
	public static int levelThresholds[] = {1,3,6,10,15,21,28,36,45,55};
	public static int min = 5;
	public static int low = 12;
	public static int medium = 25;
	public static int high = 40;
	public static int max = 60;
	
	//CP generator
	public static int numCPs = 5;
	public static CP generateCP(int cp){
		if(cp==0)return new EdgarLugo(1);
		else if(cp==1)return new EmmaLautz(1);
		else if(cp==2)return new PriyankaShah(1);
		else if(cp==3)return new KeerthanHarish(1);
		else return new JimmyChen(1);
	}

	//Difficulty multipliers
	public static int levelMultiplier = 5;
	public static double numberMultiplier = 0.75;
	
	//Miscellaneous Constants
	public static int StartingAssignments = 5;
	public static double attackMultiplier = 0.5;
	public static String guestName = "Friend";
}
