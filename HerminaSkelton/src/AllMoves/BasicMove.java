package AllMoves;

import client.Constants;

public class BasicMove extends AttackMove{
	public BasicMove(){
		int nameIndex = Constants.rand.nextInt(Constants.basicMoveNames.length-1);
		name = Constants.basicMoveNames[nameIndex];
		type = 0;
	}
}
