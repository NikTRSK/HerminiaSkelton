package map;

import libraries.ImageLibrary;

public class WallNode extends MapNode {
	private static final long serialVersionUID = -829763155413551664L;

	protected WallNode(int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage = ImageLibrary.getImage(MapConstants.WALL_IMAGE);
	}

}
