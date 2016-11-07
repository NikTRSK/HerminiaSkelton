package AllCPs;

import javax.swing.ImageIcon;

import Client.Constants;

public class KeerthanHarish extends CP{
	
	//Fire type
	public KeerthanHarish(int level) {
		super(level);
		name = "Keerthan Harish";
		type = 2;
		updateStats();
		moves = new int[2];
		moves[0] = 0;
		moves[1] = Constants.rand.nextInt(Constants.attackMoves.length+1)-1;
		sprite = new ImageIcon(getClass().getResource("/images/keerthan_harish.jpg"));
	}

	// min health, max attack, high speed
	@Override
	protected void updateStats() {
		healthPoints = 100 + (Constants.min * (level - 1));
		attack = 100 + (Constants.max * (level - 1));
		speed = 100 + (Constants.high * (level - 1));
	}

}
