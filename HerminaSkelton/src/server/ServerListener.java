package server;

import java.net.ServerSocket;
import java.util.Vector;

public class ServerListener {
	private ServerSocket serverSocket;
	private Vector<ServerClientCommunicator> playerThreads;
//	private Game game;
	
	public ServerListener(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		playerThreads = new Vector<ServerClientCommunicator>();
		new Thread().start();
	}
}
