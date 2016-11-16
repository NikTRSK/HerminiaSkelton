package map;

import java.awt.Rectangle;

import libraries.ImageLibrary;

public class WallNode extends MapNode {

	protected WallNode(int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage = ImageLibrary.getImage(MapConstants.WALL_IMAGE);
	}

}
