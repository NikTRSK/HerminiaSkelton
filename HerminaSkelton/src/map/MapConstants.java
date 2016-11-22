package map;

import java.awt.Font;

public class MapConstants {
	
	public static int FIXED_HEIGHT = 110;
	
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
	
	// labels
	public static NodeLabel CPCENTER = new NodeLabel(STARTZONE_X, STARTZONE_Y, START_X, START_Y + 1, "CP Center");
	public static NodeLabel CINEMA1 = new NodeLabel(0, 0, 4, 3, "Cinema");
	public static NodeLabel CINEMA2 = new NodeLabel(0, 0, 5, 3, "School");
	public static NodeLabel BRITTINGHAM1 = new NodeLabel(0, 0, 3, 7, "Brittingham");
	public static NodeLabel BRITTINGHAM2 = new NodeLabel(0, 0, 4, 7, "Field");
	public static NodeLabel TROPHY1 = new NodeLabel(0, 0, 7, 7, "Trohpy");
	public static NodeLabel TROPHY2 = new NodeLabel(0, 0, 8, 7, "Room");
	
	public static NodeLabel BING1 = new NodeLabel(1, 0, 1, 5, "Bing");
	public static NodeLabel BING2 = new NodeLabel(1, 0, 2, 5, "Theatre");
	public static NodeLabel NORRIS1 = new NodeLabel(1, 0, 4, 5, "Norris");
	public static NodeLabel NORRIS2 = new NodeLabel(1, 0, 5, 5, "Cinema");
	public static NodeLabel CARSON1 = new NodeLabel(1, 0, 2, 2, "Carson");
	public static NodeLabel CARSON2 = new NodeLabel(1, 0, 2, 3, "Center");
	public static NodeLabel OLDANNEN1 = new NodeLabel(1, 0, 1, 7, "Old");
	public static NodeLabel OLDANNEN2 = new NodeLabel(1, 0, 2, 7, "Annenberg");
	public static NodeLabel TAPER = new NodeLabel(1, 0, 7, 2, "Taper");
	public static NodeLabel VKC = new NodeLabel(2, 0, 2, 7, "VKC");
	public static NodeLabel LEAVEY1 = new NodeLabel(2, 0, 6, 2, "Leavey");
	public static NodeLabel LEAVEY2 = new NodeLabel(2, 0, 7, 2, "Library");
	public static NodeLabel MCCARTHY1 = new NodeLabel(2, 0, 7, 6, "McCarthy");
	public static NodeLabel MCCARTHY2 = new NodeLabel(2, 0, 7, 7, "Quad");
	
	public static NodeLabel BIRNKRANT = new NodeLabel(3, 0, 1, 3, "Birnkrant");
	public static NodeLabel NEW = new NodeLabel(3, 0, 4, 3, "New");
	public static NodeLabel NORTH = new NodeLabel(3, 0, 6, 3, "North");
	public static NodeLabel PSX = new NodeLabel(3, 0, 4, 7, "PSX");
	
	public static NodeLabel CROMWELL1 = new NodeLabel(0, 1, 4, 2, "Cromwell");
	public static NodeLabel CROMWELL2 = new NodeLabel(0, 1, 5, 2, "Field");
	public static NodeLabel SGM = new NodeLabel(0, 1, 2, 7, "SGM");
	public static NodeLabel GFS = new NodeLabel(0, 1, 6, 6, "GFS");
	
	public static NodeLabel PED = new NodeLabel(1, 1, 2, 2, "PED");
	public static NodeLabel BOVARD = new NodeLabel(1, 1, 6, 2, "Bovard");
	public static NodeLabel ANNEN = new NodeLabel(1, 1, 1, 7, "Annenberg");
	public static NodeLabel BOOKSTORE1 = new NodeLabel(1, 1, 4, 6, "Book");
	public static NodeLabel BOOKSTORE2 = new NodeLabel(1, 1, 5, 6, "Store");
	
	public static NodeLabel[] LABELS = {CPCENTER, CINEMA1, CINEMA2, BRITTINGHAM1, BRITTINGHAM2, TROPHY1, TROPHY2, BING1, BING2, NORRIS1, NORRIS2, OLDANNEN1, OLDANNEN2, CARSON1, CARSON2, TAPER, VKC, LEAVEY1, LEAVEY2, MCCARTHY1, MCCARTHY2, BIRNKRANT, NEW, NORTH, PSX, CROMWELL1, CROMWELL2, SGM, GFS, PED, BOVARD, ANNEN, BOOKSTORE1, BOOKSTORE2};
	
	// images
	public static final String IMAGES_FOLDER = "assets/";
	public static final String GRASS_IMAGE = IMAGES_FOLDER + "grass.jpg";
	public static final String PATH_IMAGE = IMAGES_FOLDER + "path.jpg";
	public static final String WALL_IMAGE = IMAGES_FOLDER + "wall.jpg";
	public static final String CPCENTER_IMAGE = IMAGES_FOLDER + "cpcenter2.png";
	public static final String AVATAR = IMAGES_FOLDER + "mario.png";
	
	//fonts
	public static final Font NODEFONT = new Font("Courier", Font.BOLD, 18);
}
