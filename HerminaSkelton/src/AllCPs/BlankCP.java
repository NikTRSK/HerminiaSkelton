package AllCPs;

public class BlankCP extends CP{
	private static final long serialVersionUID = -3388022880368146263L;

		public BlankCP(int level) {
			super(level);
			name = "";
			type = 1;
			sprite = "";
			this.real = false;
			this.currHealth = 0;
			this.healthPoints = 0;
			updateStats();
		}

		@Override
		protected void updateStats() {
			this.attack = 0;
			this.healthPoints = 0;
			this.speed = 0;
			this.level = 1;
			// Do nothing
		}
}
