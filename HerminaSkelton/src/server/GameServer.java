package server;

import java.net.ServerSocket;

public class GameServer {
	private ServerSocket serverSocket;
	private static ServerListener serverListener;
	
//	private GameServerGUI gameServerGUI;
	
	public GameServer() {
		PortGUI portGUI = new PortGUI();
		serverSocket = portGUI.getServerSocket();
//		gameServerGUI = new GameServerGUI();
		listenForConnections();
//		serverListener.setServerGUI(gameServerGUI);
		
//		new GameServerGUI();
	}
	
	public void listenForConnections() {
		// Start the server
		serverListener = new ServerListener(serverSocket, new GameServerGUI());
		serverListener.start();
	}
	
//	public static void startGame(Game game) {
//		if (serverListener != null)
//			serverListener.startGame(game);
//	}
	
	public static void main(String [] args) {
		// start the server
		new GameServer();
	}
}
