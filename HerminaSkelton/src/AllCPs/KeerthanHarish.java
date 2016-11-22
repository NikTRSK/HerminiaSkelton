package AllCPs;

import javax.swing.ImageIcon;

import client.Constants;

public class KeerthanHarish extends CP{
	private static final long serialVersionUID = -7139733661302942938L;

	//Fire type
	public KeerthanHarish(int level) {
		super(level);
		name = "Keerthan Harish";
		type = 2;
		updateStats();
		sprite = Constants.CPImage[3];
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
