package client;

import java.net.Socket;

public class GameClient {
	
	public GameClient() {
//		HostAndPortGUI hostAndPortGUI = new HostAndPortGUI();
////		hostAndPortGUI.setVisible(true);
//		Socket socket = hostAndPortGUI.getSocket();
		new loginGUI().setVisible(true);;
	}
	
	public static void main (String [] args) {
		new GameClient();
	}
}
