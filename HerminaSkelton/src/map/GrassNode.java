package map;

import java.awt.Dimension;
import java.awt.Rectangle;

import libraries.ImageLibrary;

public class GrassNode extends MapNode {

	protected GrassNode(int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage = ImageLibrary.getImage(MapConstants.GRASS_IMAGE);
	}

}
