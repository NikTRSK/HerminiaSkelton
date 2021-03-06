package AllCPs;

import client.Constants;

public class PriyankaShah extends CP{
	private static final long serialVersionUID = 3789579948275631658L;

	//Fire type
	public PriyankaShah(int level) {
		super(level);
		name = "Priyanka Shah";
		type = 2;
		updateStats();
		sprite = Constants.CPImage[2];
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
