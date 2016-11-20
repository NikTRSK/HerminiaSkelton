package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import utilities.DataPacket;
import utilities.GameInstance;
import utilities.PlayerInstance;
import utilities.User;
import utilities.Util;

public class ServerListener {
	private ServerSocket serverSocket;
//	private Vector<ServerClientCommunicator> playerThreads;
	private HashMap<Integer, ServerClientCommunicator> playerThreads;
	private GameServerGUI gameServerGUI;
	
	private DatabaseLogic db;
	private Queue<String> playerQueue;
	private Vector<GameInstance> gameInstances;
	
	private boolean listenForConnections;
	private int instanceID;
	
	public ServerListener(ServerSocket serverSocket, GameServerGUI gsg) {
		this.serverSocket = serverSocket;
		this.gameServerGUI = gsg;
		this.instanceID = 1;
//		this.playerThreads = new Vector<ServerClientCommunicator>();
		this.playerThreads = new HashMap<Integer, ServerClientCommunicator>();
		this.db = new DatabaseLogic();
		this.playerQueue = new LinkedList<String>();
		this.gameInstances = new Vector<GameInstance>();
		new Thread().start();
	}
	
//	protected void setServerGUI(GameServerGUI gsg) {
//		this.gameServerGUI = gsg;
//	}
	
	protected void sendToAllClients(DataPacket<?> dp) {
		for (ServerClientCommunicator playerThread : playerThreads.values())
			playerThread.sendData(dp);
	}
	
	// FIGURE OUT HOW TO ADD THE USERNAME
	protected boolean loginUser(User userInfo) {
		try {
			// check if user is valid
			if (db.loginUser(userInfo.getUsername(), userInfo.getPassword())) {
				// add player to the queue
				playerQueue.add(userInfo.getUsername());
				gameServerGUI.addUserToUsersTable(userInfo.getUsername());
				return true;
			}
			else
				return false;
		} catch (SQLException e) { e.printStackTrace(); return false; }
	}
	
	protected boolean createUser(User userInfo) {
			// try to create a user
			try {
				return db.createUser(userInfo.getUsername(), userInfo.getPassword());
			} catch (SQLException sqle) { Util.printExceptionToCommand(sqle); return false; }
	}
	
	public void checkQueue() {
		// if queue has 2 players start game instance
		if (playerQueue.size() == utilities.Constants.GAME_SIZE) {
			// get usernames
			String p1 = playerQueue.peek(); playerQueue.remove();
			String p2 = playerQueue.peek(); playerQueue.remove();
			// create palyer instances
			PlayerInstance P1 = new PlayerInstance(p1);
			PlayerInstance P2 = new PlayerInstance(p2);
			GameInstance gi = new GameInstance(P1, P2, instanceID++);
			gameInstances.add(gi);
			// signal players to start the game
			startGame(gi);
			// update server table
			gameServerGUI.addGameInstance(gi);
		}
	}
	
	protected synchronized void logOutUser(String userName) {
		// Find instance of the player
		ArrayList<String> instanceUsernames = null;
		
		for (GameInstance gi : gameInstances) {
			ArrayList<String> players = gi.getPlayerUsernames();
			if (players.contains(userName)) {
				instanceUsernames = players;
				break;
			}
		}
		
		// Logout all users from instance
		if (instanceUsernames != null) {
			for (int i = playerThreads.size(); i >= 0; i--) {
				if (playerThreads.get(i).getUserName().equalsIgnoreCase(instanceUsernames.get(0))) {
					playerThreads.get(i).sendData(new DataPacket<String>(utilities.Commands.LOGOUT_USER, playerThreads.get(i).getUserName()));
					playerThreads.remove(i);
				}
				if (instanceUsernames.size() > 1 && playerThreads.get(i).getUserName().equalsIgnoreCase(instanceUsernames.get(1))) {
					playerThreads.get(i).sendData(new DataPacket<String>(utilities.Commands.LOGOUT_USER, playerThreads.get(i).getUserName()));
					playerThreads.remove(i);
				}
			}
		}
	}
	
	public void startGame(GameInstance gameInstance) {
//  for (ServerClientCommunicator pt : playerThreads) {
		for (ServerClientCommunicator player : playerThreads.values()) {
			player.startGame(gameInstance);
		}
	}
	
	public void sendGameInstance() {
		//
	}
	
	private void checkAllInstances() {
		for (GameInstance gi : gameInstances) {
			if (gi.allPlayersMadeAMove()) {
				updateGameInstance(gi);
			}
		}
	}
	
	private void updateGameInstance (GameInstance gi) {
		// get both players
		ArrayList<String> playersInInstance = gi.getPlayerUsernames();
		for (ServerClientCommunicator player : playerThreads.values()) {
//			if (player.getUserName().equalsIgnoreCase(anotherString)
			if (playersInInstance.contains(player.getUserName())) {
				Integer move = gi.getOtherPlayersMove(player.getUserName());
				player.sendMove(move);
			}
		}
	}
	
	protected ArrayList<Integer> updateScores(String username, Integer playerScore) {
		// calculate score
		
		// pass into the database and return the top five scores
		
		return null;
	}
	
	public void start() {
		listenForConnections = true;
		System.out.println("Server listening on Port: " + serverSocket.getLocalSocketAddress());
		
		while (listenForConnections) {
			try {
				// Accept incomming connections
				Socket socket = serverSocket.accept();
				System.out.println("Client connected");
				// Stop server
				if (!listenForConnections) break;
				
				// Add the player to the list
				System.out.println("Client port local: " + socket.getLocalPort() + " remote: " + socket.getPort());
				int clientID = socket.getPort();
				ServerClientCommunicator player = new ServerClientCommunicator(socket, this);
				player.setID(clientID); // Set the client ID. Used to identify clients.
				playerThreads.put(clientID, player);
				player.start();
				
				checkQueue();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
    // If stop requested, close all the client connections
    try {
      serverSocket.close();
//      for (ServerClientCommunicator pt : playerThreads) {
      for (ServerClientCommunicator pt : playerThreads.values()) {
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
