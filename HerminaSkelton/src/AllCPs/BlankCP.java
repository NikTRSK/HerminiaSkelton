package AllCPs;

import javax.swing.ImageIcon;

import client.Constants;

public class BlankCP extends CP{
	private static final long serialVersionUID = -3388022880368146263L;

		public BlankCP(int level) {
			super(level);
			name = "";
			type = 1;
			sprite = new ImageIcon("");
			this.real = false;
			this.currHealth = 0;
			this.healthPoints = 0;
		}

		@Override
		protected void updateStats() {
			// Do nothing
		}
}
