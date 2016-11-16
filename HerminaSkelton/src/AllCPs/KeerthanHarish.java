package AllCPs;

import javax.swing.ImageIcon;

import client.Constants;

public class KeerthanHarish extends CP{
	
	//Fire type
	public KeerthanHarish(int level) {
		super(level);
		name = "Keerthan Harish";
		type = 2;
		updateStats();
		sprite = new ImageIcon(Constants.CPImage[3]);
	}

	// min health, max attack, high speed
	@Override
	protected void updateStats() {
		healthPoints = 100 + (Constants.min * (level - 1));
		attack = 100 + (Constants.max * (level - 1));
		speed = 100 + (Constants.high * (level - 1));
		currHealth = healthPoints;
	}

}
