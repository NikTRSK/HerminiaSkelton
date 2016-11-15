package map;

public class MapConstants {
	
	public static String G = "Grass";
	public static String P = "Path";
	public static String W = "Wall";

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
	
	// images
	public static final String IMAGES_FOLDER = "assets/";
	public static final String GRASS_IMAGE = IMAGES_FOLDER + "grass.jpg";
	public static final String PATH_IMAGE = IMAGES_FOLDER + "path.jpg";
	public static final String WALL_IMAGE = IMAGES_FOLDER + "wall.jpg";
}
