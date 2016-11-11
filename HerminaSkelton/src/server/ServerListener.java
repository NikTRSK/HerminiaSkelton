package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import utilities.DataPacket;
import utilities.GameInstance;
import utilities.User;

public class ServerListener {
	private ServerSocket serverSocket;
	private Vector<ServerClientCommunicator> playerThreads;
//	private Game game;
	
	private DatabaseLogic db;
	private Queue<String> playerQueue;
	private Vector<GameInstance> gameInstances;
	
	public ServerListener(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.playerThreads = new Vector<ServerClientCommunicator>();
		this.db = new DatabaseLogic();
		this.playerQueue = new LinkedList<String>();
		this.gameInstances = new Vector<GameInstance>();
		new Thread().start();
	}
	
	public void loginUser(User userInfo) {
		try {
			// check if user is valid
			if (db.loginUser(userInfo.getUsername(), userInfo.getPassword())) {
				// add player to the queue
				playerQueue.add(userInfo.getUsername());
				// if queue has 2 players start game instance
				if (playerQueue.size() == Constants.GAME_SIZE) {
					String p1 = playerQueue.peek(); playerQueue.remove();
					String p2 = playerQueue.peek(); playerQueue.remove();
					GameInstance gi = new GameInstance(p1, p2);
					gameInstances.add(gi);
//					startGame(gi);
				}
			}
			else {
			// if invalid return a message
			}
		} catch (SQLException e) { e.printStackTrace(); }

	}
	
	public void sendGameInstance() {
		//
	}
	
	public
}
