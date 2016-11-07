package AllCPs;

import javax.swing.ImageIcon;

import Client.Constants;

public class EdgarLugo extends CP {
	// Water type
	public EdgarLugo(int level) {
		super(level);
		name = "Edgar Lugo";
		type = 1;
		updateStats();
		moves = new int[2];
		moves[0] = 0;
		moves[1] = Constants.rand.nextInt(Constants.attackMoves.length+1)-1;
		sprite = new ImageIcon(getClass().getResource("/images/edgar_lugo.jpg"));
	}

	// very high health, medium attack, low speed
	@Override
	protected void updateStats() {
		healthPoints = 100 + (Constants.max * (level - 1));
		attack = 100 + (Constants.medium * (level - 1));
		speed = 100 + (Constants.low * (level - 1));
	}
}
