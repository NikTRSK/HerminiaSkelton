package server;

import java.net.ServerSocket;

public class GameServer {
	private ServerSocket serverSocket;
	private static ServerListener serverListener;
	
	public GameServer() {
		PortGUI portGUI = new PortGUI();
		serverSocket = portGUI.getServerSocket();
		listenForConnections();
	}
	
	public void listenForConnections() {
		// Start the server
		serverListener = new ServerListener(serverSocket, new GameServerGUI());
		serverListener.start();
	}
	
	public static void main(String [] args) {
		// start the server
		new GameServer();
	}
}
