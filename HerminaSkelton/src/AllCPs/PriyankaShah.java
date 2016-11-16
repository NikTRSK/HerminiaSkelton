package AllCPs;

import javax.swing.ImageIcon;

import client.Constants;

public class PriyankaShah extends CP{
	
	//Fire type
	public PriyankaShah(int level) {
		super(level);
		name = "Priyanka Shah";
		type = 2;
		updateStats();
		sprite = new ImageIcon(Constants.CPImage[2]);
	}

	// low health, high attack, high speed
	@Override
	protected void updateStats() {
		healthPoints = 100 + (Constants.low * (level - 1));
		attack = 100 + (Constants.high * (level - 1));
		speed = 100 + (Constants.high * (level - 1));
		currHealth = healthPoints;
	}
}
