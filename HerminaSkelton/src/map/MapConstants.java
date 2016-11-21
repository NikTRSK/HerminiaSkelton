package map;

import client.Constants;

public class MapConstants {
	
	public static int STARTZONE_X = 1;
	public static int STARTZONE_Y = 1;
	public static int START_X = 7;
	public static int START_Y = 6;
	
	public static String G = "Grass";
	public static String P = "Path";
	public static String W = "Wall";
	public static String CP = "CP Center";

	public static final String[][] A1 = {
			{W, W, W, W, W, W, W, W, W, W},
			{W, P, P, P, P, P, P, P, P, P},
			{W, P, W, G, W, G, W, G, W, P},
			{W, P, W, G, G, G, G, G, G, P},
			{W, P, W, G, G, G, W, G, W, P},
			{W, P, P, P, P, P, P, P, P, P},
			{W, P, W, W, W, G, G, W, W, P},
			{W, P, G, G, G, G, G, W, W, P},
			{W, P, G, G, G, G, G, W, W, P},
			{W, P, P, P, P, P, P, P, P, P},
	};
	
	public static final String[][] A2 = {
			{W, W, W, W, W, W, W, W, W, W},
			{P, P, P, P, P, P, P, P, P, P},
			{P, G, W, G, W, G, P, W, W, P},
			{P, G, W, G, G, G, P, W, G, P},
			{P, P, P, P, G, G, P, W, W, P},
			{P, W, W, P, W, W, P, P, P, P},
			{P, P, P, P, P, P, P, G, G, P},
			{P, W, W, W, G, G, G, P, G, P},
			{P, W, W, W, G, G, G, G, P, P},
			{P, P, P, P, P, P, P, P, P, P},
	};

	public static final String[][] A3 = {
			{W, W, W, W, W, W, W, W, W, W},
			{P, P, P, P, P, P, P, P, P, P},
			{P, W, P, G, G, P, W, W, W, P},
			{P, P, P, G, G, P, W, W, W, P},
			{P, W, P, P, P, P, P, P, P, P},
			{P, W, W, G, G, P, G, G, G, P},
			{P, P, P, G, G, P, G, G, G, P},
			{P, W, W, W, W, P, G, G, G, P},
			{P, W, W, W, W, P, G, G, G, P},
			{P, P, P, P, P, P, P, P, P, P},
	};
	
	public static final String[][] A4 = {
			{W, W, W, W, W, W, W, W, W, W},
			{P, P, P, P, P, P, P, P, P, W},
			{P, W, W, W, W, W, W, W, P, W},
			{P, W, P, W, G, W, G, W, P, W},
			{P, W, P, W, W, W, W, W, P, W},
			{P, P, P, P, P, P, P, G, G, W},
			{P, G, P, W, W, W, W, G, G, W},
			{P, G, P, W, W, W, W, G, G, W},
			{P, G, P, W, W, W, W, W, W, W},
			{P, P, P, P, P, P, P, P, P, W},
	};
	
	public static final String[][] B1 = {
			{W, P, P, P, P, P, P, P, P, P},
			{W, P, W, G, G, G, G, G, W, P},
			{W, P, W, G, G, G, G, G, W, P},
			{W, P, W, G, G, G, G, G, W, P},
			{W, P, W, W, W, W, W, W, W, P},
			{W, P, P, P, P, P, P, P, P, P},
			{W, P, W, W, P, W, W, W, W, P},
			{W, P, W, W, P, P, P, P, P, P},
			{W, P, W, W, P, P, P, P, W, P},
			{W, P, P, P, P, P, P, P, P, P},
	};
	
	public static final String[][] B2 = {
			{P, P, P, P, P, P, P, P, P, P},
			{P, W, W, W, G, G, W, W, G, P},
			{P, W, W, W, G, G, W, W, G, P},
			{P, W, W, W, G, G, W, W, G, P},
			{P, W, W, W, G, G, W, W, P, P},
			{P, P, P, P, P, P, P, P, P, P},
			{P, W, W, P, W, W, W, P, W, P},
			{P, W, W, P, W, W, P, CP, W, P},
			{P, W, W, P, W, W, P, W, W, P},
			{P, P, P, P, P, P, P, P, P, P},
	};
	
	public static final String[][] B3 = {
			{P, P, P, P, P, P, P, P, P, P},
			{P, G, G, G, P, W, W, W, W, P},
			{P, G, G, G, P, W, W, P, W, P},
			{P, G, G, G, P, W, W, P, W, P},
			{P, P, G, G, P, W, W, W, W, P},
			{P, P, P, P, P, P, P, P, P, P},
			{P, P, W, W, W, P, W, P, W, P},
			{P, G, W, W, W, P, P, G, P, P},
			{P, G, W, P, W, P, W, P, W, P},
			{P, P, P, P, P, P, P, P, P, P},
	};
	
	public static final String[][] B4 = {
			{P, P, P, P, P, P, P, P, P, W},
			{P, G, W, W, W, W, W, P, W, W},
			{P, G, W, W, W, P, P, P, W, W},
			{P, G, G, G, W, P, W, G, G, W},
			{P, G, G, G, W, P, W, W, G, W},
			{P, P, P, P, P, P, P, P, G, W},
			{P, W, W, W, W, W, P, P, W, W},
			{P, W, G, G, G, W, P, P, W, W},
			{P, W, G, G, G, W, P, P, W, W},
			{P, P, P, P, P, P, P, P, W, W},
	};
	
	public static final String[][] C1 = {
			{W, P, P, P, P, P, P, P, P, P},
			{W, P, W, W, P, W, W, W, W, P},
			{W, P, W, W, P, G, G, G, W, P},
			{W, P, W, W, P, G, G, G, P, P},
			{W, P, W, W, P, W, W, W, W, P},
			{W, P, P, P, P, P, P, P, P, P},
			{W, P, W, W, P, W, W, W, W, P},
			{W, P, P, P, P, P, P, P, P, P},
			{W, P, P, P, P, W, W, P, G, P},
			{W, W, W, W, W, W, W, W, W, W},
	};
	
	public static final String[][] C2 = {
			{P, P, P, P, P, P, P, P, P, P},
			{P, W, W, P, W, W, P, W, W, P},
			{P, W, W, P, P, P, P, W, W, P},
			{P, P, P, P, P, W, P, W, W, P},
			{P, W, W, P, W, W, P, W, W, P},
			{P, P, P, P, P, P, P, P, P, P},
			{P, W, W, W, W, P, W, W, W, P},
			{P, G, G, G, G, P, W, G, G, P},
			{P, G, G, G, G, P, W, G, G, P},
			{W, W, W, W, W, W, W, W, W, W},
	};
	
	public static final String[][] C3 = {
			{P, P, P, P, P, P, P, P, P, P},
			{P, W, W, P, P, P, W, W, W, W},
			{P, W, W, P, P, P, W, W, W, W},
			{P, W, W, P, P, P, W, W, W, W},
			{P, P, P, P, W, P, G, G, G, G},
			{P, W, W, P, W, P, G, G, W, W},
			{P, W, W, P, W, P, G, G, W, W},
			{P, G, G, G, G, G, W, W, W, P},
			{P, G, G, W, W, W, W, W, P, P},
			{W, W, W, W, W, W, W, P, P, P},
	};
	
	public static final String[][] C4 = {
			{P, P, P, P, P, P, P, P, W, W},
			{W, G, G, W, W, W, W, W, W, W},
			{W, G, G, W, W, W, P, P, W, W},
			{W, G, G, W, W, W, P, P, W, W},
			{G, G, G, W, W, W, P, P, P, P},
			{W, W, W, W, W, P, P, P, P, P},
			{W, W, W, P, P, P, P, P, W, W},
			{P, P, P, P, P, P, P, W, W, P},
			{P, P, P, P, P, P, W, W, P, P},
			{W, W, W, W, W, W, W, P, P, P},
	};
	
	
	public static final String[][][][] ZONES = {
			{A1, A2, A3, A4},
			{B1, B2, B3, B4},
			{C1, C2, C3, C4},
	};
	
	// images
	public static final String IMAGES_FOLDER = "assets/";
	public static final String GRASS_IMAGE = IMAGES_FOLDER + "grass.jpg";
	public static final String PATH_IMAGE = IMAGES_FOLDER + "path.jpg";
	public static final String WALL_IMAGE = IMAGES_FOLDER + "wall.jpg";
	public static final String CPCENTER_IMAGE = IMAGES_FOLDER + "cpcenter1.png";
	public static final String AVATAR = IMAGES_FOLDER + "mario.png";
}
