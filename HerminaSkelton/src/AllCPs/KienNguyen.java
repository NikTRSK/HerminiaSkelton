package AllCPs;

import javax.swing.ImageIcon;

import client.Constants;

public class KienNguyen extends CP{
	private static final long serialVersionUID = -3617809947222869824L;

		// Water type
		public KienNguyen(int level) {
			super(level);
			name = "Kien Nguyen";
			type = 1;
			updateStats();
			sprite = new ImageIcon(Constants.CPImage[5]);
		}

		// medium health, medium attack, high speed
		@Override
		protected void updateStats() {
			healthPoints = 100 + (Constants.medium * (level - 1));
			attack = 100 + (Constants.medium * (level - 1));
			speed = 100 + (Constants.high * (level - 1));
			currHealth = healthPoints;
		}
}
