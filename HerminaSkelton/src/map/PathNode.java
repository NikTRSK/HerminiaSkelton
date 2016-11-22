package map;

import libraries.ImageLibrary;

public class PathNode extends MapNode {
	private static final long serialVersionUID = -8599017564203766223L;

	protected PathNode(int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage = ImageLibrary.getImage(MapConstants.PATH_IMAGE);
	}

}
