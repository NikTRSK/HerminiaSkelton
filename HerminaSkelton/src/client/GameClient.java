package client;

public class GameClient {
	
	public GameClient() {
		new loginGUI().setVisible(true);
	}
	
	public static void main (String [] args) {
		new GameClient();
	}
}
