package AllCPs;

import javax.swing.ImageIcon;

import client.Constants;

public class EdgarLugo extends CP {
	private static final long serialVersionUID = 1123012868725216369L;

	// Water type
	public EdgarLugo(int level) {
		super(level);
		name = "Edgar Lugo";
		type = 1;
		updateStats();
		sprite = new ImageIcon(Constants.CPImage[0]);
	}

	// very high health, medium attack, low speed
	@Override
	protected void updateStats() {
		healthPoints = 100 + (Constants.max * (level - 1));
		attack = 100 + (Constants.medium * (level - 1));
		speed = 100 + (Constants.low * (level - 1));
		currHealth = healthPoints;
	}
}
