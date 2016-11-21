package utilities;

import java.io.Serializable;

public class GameTimer extends Thread implements Serializable {
	private static final long serialVersionUID = 6661280195671974593L;
	private Integer timer;
//	private Boolean timerExpired;
	GameInstance gameInstance;
	
	public GameTimer(GameInstance gi) {
		timer = utilities.Constants.GAME_TIME*60; // 300 sec
//		timer = 10; // 300 sec
		this.gameInstance = gi;
	}
	
	@Override
	public void run() {
		while (timer >= 0) {
			try {
				timer--;
				gameInstance.updatePlayerTimers(timer);
				Thread.yield();
				Thread.sleep(1000);
			} catch (InterruptedException ie) { utilities.Util.printExceptionToCommand(ie);	}
		}
		gameInstance.timerOut();
	}
}
