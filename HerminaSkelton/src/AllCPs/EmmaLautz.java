package AllCPs;

import javax.swing.ImageIcon;

import client.Constants;

public class EmmaLautz extends CP{
	private static final long serialVersionUID = 9003732126461557874L;

	//Grass type
	public EmmaLautz(int level) {
		super(level);
		name = "Emma Lautz";
		type = 3;
		updateStats();
		sprite = new ImageIcon(Constants.CPImage[1]);
	}

	//Medium health, medium attack, high speed
	@Override
	protected void updateStats() {
		healthPoints = 100+(Constants.medium*(level-1));
		attack = 100+(Constants.medium*(level-1));
		speed = 100+(Constants.high*(level-1));
		currHealth = healthPoints;
	}

}
