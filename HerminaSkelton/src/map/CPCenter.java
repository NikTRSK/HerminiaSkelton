package map;

import libraries.ImageLibrary;

public class CPCenter extends PathNode {
	private static final long serialVersionUID = -4628018231302949783L;

	protected CPCenter(int x, int y, int width, int height) {
		super(x, y, width, height);
		mImage = ImageLibrary.getImage(MapConstants.CPCENTER_IMAGE);
	}

}
