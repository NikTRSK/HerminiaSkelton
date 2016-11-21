package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import client.Player;
import utilities.DataPacket;
import utilities.DeadSwitch;
import utilities.GameInstance;
import utilities.GameTimer;
import utilities.PlayerAction;
import utilities.PlayerInstance;
import utilities.User;
import utilities.Util;

public class ServerListener {
	private ServerSocket serverSocket;
//	private Vector<ServerClientCommunicator> playerThreads;
	private HashMap<Integer, ServerClientCommunicator> playerThreads;
	private GameServerGUI gameServerGUI;
	
	private DatabaseLogic db;
//	private Queue<String> playerQueue;
	private Queue<Integer> playerQueue;
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
//		this.playerQueue = new LinkedList<String>();
		this.playerQueue = new LinkedList<Integer>();
		this.gameInstances = new Vector<GameInstance>();
		new Thread().start();
	}
	
	protected void sendToAllClients(DataPacket<?> dp) {
		for (ServerClientCommunicator playerThread : playerThreads.values())
			playerThread.sendData(dp);
	}
	
	// FIGURE OUT HOW TO ADD THE USERNAME
	protected boolean loginUser(User userInfo, Integer ID) {
		try {
			// check if user is valid
			if (db.loginUser(userInfo.getUsername(), userInfo.getPassword())) {
				gameServerGUI.addUserToUsersTable(userInfo.getUsername());
				return true;
			}
			else
				return false;
		} catch (SQLException e) { e.printStackTrace(); return false; }
	}
	
	protected void addPlayerToGameInstance(Player player, String playerUsername) {
		for (GameInstance gameInstance : gameInstances) {
			ArrayList<String> players = gameInstance.getPlayerUsernames();
			System.out.println("Adding players to instance" + players.size());
			if (players.contains(playerUsername)) {
				System.out.println("Players in instance");
				gameInstance.addPlayerToFinalBattle(player);
			}
		}
	}
	
	protected void addPlayerToQueue(Integer ID) {
		// add player to the queue
		if (playerThreads.get(ID).getGameType() == 0) {
			PlayerInstance P1 = new PlayerInstance(playerThreads.get(ID).getUserName(), playerThreads.get(ID));
			GameInstance gi = new GameInstance(P1, null, instanceID++);
			gameInstances.add(gi);
			gameServerGUI.addGameInstance(gi);
			startGame(gi);
		} else {
			playerQueue.add(ID);
			checkQueue();
		}
	}
	
	protected boolean createUser(User userInfo) {
			// try to create a user
			try {
				return db.createUser(userInfo.getUsername(), userInfo.getPassword());
			} catch (SQLException sqle) { Util.printExceptionToCommand(sqle); return false; }
	}
	
	public void checkQueue() {
		System.out.println("Checking Queue");
		// if queue has 2 players start game instance
		if (playerQueue.size() == utilities.Constants.GAME_SIZE) {
			Integer p1 = playerQueue.peek(); playerQueue.remove();
			Integer p2 = playerQueue.peek(); playerQueue.remove();
			
			System.out.println("GettingPlayers: " + playerThreads.get(p1).getUserName() + "| " + playerThreads.get(p2).getUserName());
			// create palyer instances
			PlayerInstance P1 = new PlayerInstance(playerThreads.get(p1).getUserName(), playerThreads.get(p1));
			PlayerInstance P2 = new PlayerInstance(playerThreads.get(p2).getUserName(), playerThreads.get(p2));
			GameInstance gi = new GameInstance(P1, P2, instanceID++);
			System.out.println("Starting multiplayer ");
			gameInstances.add(gi);
			startGame(gi);
			gameServerGUI.addGameInstance(gi);
//			gi.startTimer();
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
		ArrayList<String> players = gameInstance.getPlayerUsernames();
//  for (ServerClientCommunicator pt : playerThreads) {
//		for (Integer i = 0; i < playerThreads.size(); i++) {
		int i = 0;
		GameTimer timer = gameInstance.getTimer();
		for (ServerClientCommunicator player : playerThreads.values()) {
			if (players.contains(player.getUserName()))
				player.startGame(i++);
//				player.sendData(new DataPacket<GameTimer>(utilities.Commands.TIME_UPDATE, timer));
		}
		timer.start();
		// new stuff

//		timer.start();
//		//
//		for (ServerClientCommunicator player : playerThreads.values()) {
//			if (players.contains(player.getUserName()))
//				player.startGame(i++);
//		}
	}
	
	public void receiveActionToFinalBattleManager(PlayerAction pa, String username) {
		for (GameInstance gameInstance : gameInstances) {
			ArrayList<String> playersInInstance = gameInstance.getPlayerUsernames();
			for (ServerClientCommunicator player : playerThreads.values()) {
				if (playersInInstance.contains(player.getUserName())) {
					gameInstance.FBMReceiveAction(pa);
				}
			}
		}
	}
	
	public void receiveDeadSwitchToFinalBattleManager(DeadSwitch ds, String username) {
		for (GameInstance gameInstance : gameInstances) {
			ArrayList<String> playersInInstance = gameInstance.getPlayerUsernames();
			for (ServerClientCommunicator player : playerThreads.values()) {
				if (playersInInstance.contains(player.getUserName())) {
					gameInstance.FBMReceiveDeadSwitch(ds);
				}
			}
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
		// pass into the database and return the top five scores
		return db.getScores(username, playerScore);
	}
	
	public void endGame(GameInstance gi) {
		
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
				
//				checkQueue();
				
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
