package AllCPs;

import javax.swing.ImageIcon;

import Client.Constants;

public class EmmaLautz extends CP{

	//Grass type
	public EmmaLautz(int level) {
		super(level);
		name = "Emma Lautz";
		type = 3;
		updateStats();
		moves = new int[2];
		moves[0] = 0;
		moves[1] = Constants.rand.nextInt(Constants.attackMoves.length+1)-1;
		sprite = new ImageIcon(getClass().getResource("/images/emma_lautz.jpg"));
	}

	//Medium health, medium attack, high speed
	@Override
	protected void updateStats() {
		healthPoints = 100+(Constants.medium*(level-1));
		attack = 100+(Constants.medium*(level-1));
		speed = 100+(Constants.high*(level-1));
	}

}
