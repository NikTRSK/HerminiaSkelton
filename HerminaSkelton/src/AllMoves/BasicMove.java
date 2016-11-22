package AllMoves;

import client.Constants;

public class BasicMove extends AttackMove{
	private static final long serialVersionUID = -1735019329169269732L;

	public BasicMove(){
		int nameIndex = Constants.rand.nextInt(Constants.basicMoveNames.length-1);
		name = Constants.basicMoveNames[nameIndex];
		type = 0;
	}
}
