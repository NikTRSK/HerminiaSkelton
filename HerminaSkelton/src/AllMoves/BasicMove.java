package AllMoves;

import Client.Constants;

public class BasicMove extends AttackMove{
	public BasicMove(){
		int nameIndex = Constants.rand.nextInt(Constants.basicMoveNames.length);
		name = Constants.basicMoveNames[nameIndex];
		type = 0;
	}
}
