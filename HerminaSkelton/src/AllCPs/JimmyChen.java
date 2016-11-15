package AllCPs;

import javax.swing.ImageIcon;

import client.Constants;

public class JimmyChen extends CP{
	
	//Grass type
	public JimmyChen(int level) {
		super(level);
		name = "Jimmy Chen";
		type = 2;
		updateStats();
		sprite = new ImageIcon(Constants.CPImage[4]);
	}

	// high health, high attack, min speed
	@Override
	protected void updateStats() {
		healthPoints = 100 + (Constants.low * (level - 1));
		attack = 100 + (Constants.high * (level - 1));
		speed = 100 + (Constants.high * (level - 1));
		currHealth = healthPoints;
	}
}
