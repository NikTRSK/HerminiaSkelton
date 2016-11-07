package AllCPs;

import javax.swing.ImageIcon;

import Client.Constants;

public class JimmyChen extends CP{
	
	//Grass type
	public JimmyChen(int level) {
		super(level);
		name = "Jimmy Chen";
		type = 2;
		updateStats();
		moves = new int[2];
		moves[0] = 0;
		moves[1] = Constants.rand.nextInt(Constants.attackMoves.length+1)-1;
		sprite = new ImageIcon(getClass().getResource("/images/jimmy_chen.jpg"));
	}

	// high health, high attack, min speed
	@Override
	protected void updateStats() {
		healthPoints = 100 + (Constants.low * (level - 1));
		attack = 100 + (Constants.high * (level - 1));
		speed = 100 + (Constants.high * (level - 1));
	}
}
