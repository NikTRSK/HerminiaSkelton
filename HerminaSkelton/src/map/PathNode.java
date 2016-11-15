package map;

import java.awt.Rectangle;

import libraries.ImageLibrary;

public class PathNode extends MapNode {

	protected PathNode(int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage = ImageLibrary.getImage(MapConstants.PATH_IMAGE);
	}

}
