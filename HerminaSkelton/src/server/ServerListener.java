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

import client.Player;
import utilities.DataPacket;
import utilities.DeadSwitch;
import utilities.GameInstance;
import utilities.GameScore;
import utilities.GameTimer;
import utilities.PlayerAction;
import utilities.PlayerInstance;
import utilities.User;
import utilities.Util;

public class ServerListener {
  private ServerSocket serverSocket;
  private HashMap<Integer, ServerClientCommunicator> playerThreads;
  private GameServerGUI gameServerGUI;
  
  private DatabaseLogic db;
  private Queue<Integer> playerQueue;
  private Vector<GameInstance> gameInstances;
  
  private boolean listenForConnections;
  private int instanceID;
  
  public ServerListener(ServerSocket serverSocket, GameServerGUI gsg) {
    this.serverSocket = serverSocket;
    this.gameServerGUI = gsg;
    this.instanceID = 1;
    this.playerThreads = new HashMap<Integer, ServerClientCommunicator>();
    this.db = new DatabaseLogic();
    this.playerQueue = new LinkedList<Integer>();
    this.gameInstances = new Vector<GameInstance>();
    new Thread().start();
  }
  
  protected void sendToAllClients(DataPacket<?> dp) {
    for (ServerClientCommunicator playerThread : playerThreads.values())
      playerThread.sendData(dp);
  }
  
  protected boolean loginUser(User userInfo, Integer ID) {
    try {
      // check if user is valid
      if (db.loginUser(userInfo.getUsername(), userInfo.getPassword())) {
        for(ServerClientCommunicator player : playerThreads.values()) {
          if (player.getUserName()!=null && player.getUserName().equalsIgnoreCase(userInfo.getUsername()))
            return false;
        }
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
      if (players.contains(playerUsername)) {
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
    // if queue has 2 players start game instance
    if (playerQueue.size() == utilities.Constants.GAME_SIZE) {
      Integer p1 = playerQueue.peek(); playerQueue.remove();
      Integer p2 = playerQueue.peek(); playerQueue.remove();
      // create palyer instances
      PlayerInstance P1 = new PlayerInstance(playerThreads.get(p1).getUserName(), playerThreads.get(p1));
      PlayerInstance P2 = new PlayerInstance(playerThreads.get(p2).getUserName(), playerThreads.get(p2));
      GameInstance gi = new GameInstance(P1, P2, instanceID++);
      gameInstances.add(gi);
      startGame(gi);
      gameServerGUI.addGameInstance(gi);
    }
  }
  
  protected synchronized void logOutUser(String userName) {
    // Find instance of the player
    ArrayList<String> instanceUsernames = null;
    
    int instanceID = -1;
    for (int k = 0; k < gameInstances.size(); ++k) {
      ArrayList<String> players = gameInstances.get(k).getPlayerUsernames();
      if (players.contains(userName)) {
        instanceUsernames = players;
        instanceID = k;
        break;
      }
    }
    
    // Logout all users from instance
    if (instanceUsernames != null) {
      ArrayList<Integer> playerKeys = new ArrayList<Integer>();
      for (Integer key : playerThreads.keySet()) {
        if (playerThreads.get(key).getUserName().equalsIgnoreCase(instanceUsernames.get(0))) {
          playerKeys.add(key);
        }
        else if (instanceUsernames.size() > 1 && playerThreads.get(key).getUserName().equalsIgnoreCase(instanceUsernames.get(1))) {
          playerKeys.add(key);
        }
      }
      // remove the game instance
      if (instanceID >= 0) {
        gameInstances.get(instanceID).stopTimer();
        gameInstances.remove(instanceID);
      }
      // remove the users
      for (int key : playerKeys) {
        playerThreads.get(key).sendData(new DataPacket<String>(utilities.Commands.LOGOUT_USER, playerThreads.get(key).getUserName()));
        playerThreads.get(key).closeConnection();
        playerThreads.remove(key);
      }
    } else { // if the user is not part of an instance yet
      for (Integer key : playerThreads.keySet()) {
        if (playerThreads.get(key).getUserName().equals(userName)) {
          playerThreads.get(key).sendData(new DataPacket<String>(utilities.Commands.LOGOUT_USER, playerThreads.get(key).getUserName()));
          playerThreads.remove(key);
        } 
      }
    }
  }
  
  public void startGame(GameInstance gameInstance) {
    ArrayList<String> players = gameInstance.getPlayerUsernames();
    int i = 0;
    GameTimer timer = gameInstance.getTimer();
    for (ServerClientCommunicator player : playerThreads.values()) {
      if (players.contains(player.getUserName()))
        player.startGame(i++);
    }
    timer.start();
  }
  
  public void receiveActionToFinalBattleManager(PlayerAction pa, String username) {
    for (GameInstance gameInstance : gameInstances) {
      ArrayList<String> playersInInstance = gameInstance.getPlayerUsernames();
      for (ServerClientCommunicator player : playerThreads.values()) {
        if (playersInInstance.contains(player.getUserName())) {
          gameInstance.FBMReceiveAction(pa);
          return;
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
  
  protected ArrayList<GameScore> updateScores(String username, Integer playerScore) {
    // pass into the database and return the top five scores
    return db.getScores(username, playerScore);
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
      } catch (IOException e) {
        utilities.Util.printExceptionToCommand(e);
      }
    }
    
    // If stop requested, close all the client connections
    try {
      serverSocket.close();
      for (ServerClientCommunicator pt : playerThreads.values()) {
        try {
          pt.ois.close();
          pt.oos.close();
          pt.socket.close();
        } catch(IOException ioe) {
        	utilities.Util.printExceptionToCommand(ioe);
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
