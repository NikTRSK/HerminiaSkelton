package map;

import libraries.ImageLibrary;

public class CPCenter extends PathNode {

	protected CPCenter(int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage = ImageLibrary.getImage(MapConstants.CPCENTER_IMAGE);
	}

}
