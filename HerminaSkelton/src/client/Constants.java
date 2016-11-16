package client;

import java.awt.Color;
import java.awt.Font;
import java.util.Random;

import AllCPs.CP;
import AllCPs.EdgarLugo;
import AllCPs.EmmaLautz;
import AllCPs.JimmyChen;
import AllCPs.KeerthanHarish;
import AllCPs.PriyankaShah;
import AllMoves.AttackMove;
import AllMoves.BasicMove;
import AllMoves.FireMove;
import AllMoves.GrassMove;
import AllMoves.WaterMove;

public class Constants {
	//Gamewide random number generator
	public static Random rand = new Random(System.currentTimeMillis());
	
	//Image Files
	public static String[] avatar = {"resources/mario.svg"};
	public static String millerImage;
	public static String[] CPImage = {"resources/images/edgar_lugo.jpg", "resources/images/emma_lautz.jpg", 
									  "resources/images/priyanka_shah.jpg", "resources/images/keerthan_harish.jpg",
									  "resources/images/jimmy_chen.jpg"};
	public static String POKEBALL = "resources/images/pokeball.png";
		
	//Moves and types
	public static String[] basicMoveNames = {
			"Pound", "Smash", "Beat Up", "Scratch", "Slash"
	};
	public static int NUM_MOVES = 3;
	public static AttackMove[] attackMoves = {
			new BasicMove(), new FireMove(), new GrassMove(), new WaterMove()
	};
	public static String[] type = {
			"Normal", "Water", "Fire", "Grass"
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
	public static double attackMultiplier = 0.2;
	public static String guestName = "Friend";
	
	//Colors
	public static final Font GAMEFONT = new Font("Courier", Font.BOLD, 14);
	public static final Color BACKGROUND_COLOR = Color.MAGENTA;
	public static final Color FONT_COLOR = Color.WHITE;
	public static final Color BACKGROUND_COLOR2 = Color.DARK_GRAY;
	public static final Color[] TYPE_COLOR= {
			Color.GRAY, Color.BLUE, Color.RED, Color.GREEN
	};
}
