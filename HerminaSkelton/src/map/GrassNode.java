package map;

import libraries.ImageLibrary;

public class GrassNode extends MapNode {
	private static final long serialVersionUID = 2217905476159519284L;

	protected GrassNode(int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage = ImageLibrary.getImage(MapConstants.GRASS_IMAGE);
	}

}
