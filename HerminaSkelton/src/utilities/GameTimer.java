package utilities;

public class GameTimer extends Thread {
	
	private Integer timer;
//	private Boolean timerExpired;
	GameInstance gameInstance;
	
	public GameTimer(GameInstance gi) {
		timer = 5*60; // 300 sec
		this.gameInstance = gi;
	}
	
	@Override
	public void run() {
		while (timer >= 0) {
			try {
				Thread.yield();
				Thread.sleep(1000);
				timer--;
			} catch (InterruptedException ie) { utilities.Util.printExceptionToCommand(ie);	}
		}
		gameInstance.startFinalBattle();
	}
}
