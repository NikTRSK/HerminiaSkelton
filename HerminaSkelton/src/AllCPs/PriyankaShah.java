package AllCPs;

import javax.swing.ImageIcon;

import Client.Constants;

public class PriyankaShah extends CP{
	
	//Fire type
	public PriyankaShah(int level) {
		super(level);
		name = "Priyanka Shah";
		type = 2;
		updateStats();
		moves = new int[2];
		moves[0] = 0;
		moves[1] = Constants.rand.nextInt(Constants.attackMoves.length+1)-1;
		sprite = new ImageIcon(getClass().getResource("/images/priyanka_shah.jpg"));
	}

	// low health, high attack, high speed
	@Override
	protected void updateStats() {
		healthPoints = 100 + (Constants.low * (level - 1));
		attack = 100 + (Constants.high * (level - 1));
		speed = 100 + (Constants.high * (level - 1));
	}
}
