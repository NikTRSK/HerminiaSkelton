package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
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
	
	boolean listenForConnections;
	
	public ServerListener(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.playerThreads = new Vector<ServerClientCommunicator>();
		this.db = new DatabaseLogic();
		this.playerQueue = new LinkedList<String>();
		this.gameInstances = new Vector<GameInstance>();
		new Thread().start();
	}
	
	// FIGURE OUT HOW TO ADD THE USERNAME
	public boolean loginUser(User userInfo) {
		try {
			// check if user is valid
			if (db.loginUser(userInfo.getUsername(), userInfo.getPassword())) {
				// add player to the queue
				playerQueue.add(userInfo.getUsername());
				return true;
			}
			else
				return false;
		} catch (SQLException e) { e.printStackTrace(); return false; }
	}
	
	public void checkQueue() {
		// if queue has 2 players start game instance
		if (playerQueue.size() == Constants.GAME_SIZE) {
			String p1 = playerQueue.peek(); playerQueue.remove();
			String p2 = playerQueue.peek(); playerQueue.remove();
			GameInstance gi = new GameInstance(p1, p2);
			gameInstances.add(gi);
			startGame(gi);
		}
	}
	
	public void logOutUser(String userName) {
		
	}
	
	public void startGame(GameInstance gameInstance) {
		for (ServerClientCommunicator player : playerThreads) {
			player.startGame(gameInstance);
		}
	}
	
	public void sendGameInstance() {
		//
	}
	
	public void start() {
		listenForConnections = true;
		System.out.println("Server listening on Port: " + serverSocket.getLocalSocketAddress() + ":" + serverSocket.getLocalPort());
		
		while (listenForConnections) {
			try {
				// Accept incomming connections
				Socket socket = serverSocket.accept();
				
				// Stop server
				if (!listenForConnections) break;
				
				// Add the player to the list
				ServerClientCommunicator player = new ServerClientCommunicator(socket, this);
				playerThreads.add(player);
				player.start();
				
				checkQueue();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
    // If stop requested, close all the client connections
    try {
      serverSocket.close();
      for (ServerClientCommunicator pt : playerThreads) {
        try {
          pt.ois.close();
          pt.oos.close();
          pt.socket.close();
        } catch(IOException ioe) {
          ioe.printStackTrace();
        }
      }
    }
    catch (IOException e) {
    	System.out.println("Exception in server: " + e.getMessage());
    }
    catch (Exception e) {
      System.out.println("Exception closing the server and clients: " + e.getMessage());
    }
  }
}
