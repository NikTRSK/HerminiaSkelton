package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import client.Player;
import utilities.ChatMessage;
import utilities.DataPacket;
import utilities.DeadSwitch;
import utilities.GameScore;
import utilities.PlayerAction;
import utilities.User;

public class ServerClientCommunicator extends Thread implements Serializable {
  private static final long serialVersionUID = -2902489247828430845L;
  protected Socket socket;
  protected ObjectOutputStream oos;
  protected ObjectInputStream ois;
  private ServerListener serverListener;
  
  private String userName;
  private Integer connectionID;
  private Integer gameType;
  
  public ServerClientCommunicator(Socket socket, ServerListener serverListener) throws IOException {
    this.socket = socket;
    this.serverListener = serverListener;
    this.gameType = -1;
    this.oos = new ObjectOutputStream(socket.getOutputStream());
    this.ois = new ObjectInputStream(socket.getInputStream());
  }
  
  public void sendData(DataPacket<?> dp) {
    try {
      oos.writeObject(dp);
      oos.flush();
    } catch (IOException ioe) { utilities.Util.printExceptionToCommand(ioe); }
  }
  
//  protected void startGame(GameInstance gameInstance) {
  protected void startGame(Integer turn) {
    sendData(new DataPacket<Integer>(utilities.Commands.START_GAME, turn));
  }
  
  protected void setUserName(String userName) {
    this.userName = userName;
  }
  
  protected String getUserName() {
    return this.userName;
  }
  
  public void sendScores(Integer [] scores) {
    try {
      oos.writeObject(scores);
      oos.flush();
    } catch (IOException ioe) {
      utilities.Util.printExceptionToCommand(ioe);
    }
  }
  
  protected void sendMove(Integer move) {
    sendData(new DataPacket<Integer>(utilities.Commands.OTHER_MOVE, move)); 
  }
  
  protected void setID(int threadID) {
    this.connectionID = threadID;
  }
  
  // takes in the current score from the player and returns the top 5 scores
  public ArrayList<GameScore> getTopScores(Integer playerScore) {
    ArrayList<GameScore> scores = serverListener.updateScores(userName, playerScore);
    
    return scores;
  }
  
  public Integer getGameType() {
    return gameType;
  }
  
  public void closeConnection() {
    try {
      if (oos != null)
        oos.close();
      if (ois != null)
        ois.close();
      if (socket != null)
        socket.close();
    } catch (IOException e) {
    	utilities.Util.printExceptionToCommand(e);
    }
  }
  
  public void run() {
    boolean listenForConnections = true;
    while (listenForConnections) {
      try {
        DataPacket<?> input = (DataPacket<?>)ois.readObject();
        switch (input.getCommand()) {
          case utilities.Commands.LOGIN_USER :
            User userInfo = (User)input.getData();
            if (serverListener.loginUser(userInfo, connectionID)) {
              // Send a response to user
              userName = userInfo.getUsername();
              sendData(new DataPacket<Boolean>(utilities.Commands.AUTH_RESPONSE, true));
            } else sendData(new DataPacket<Boolean>(utilities.Commands.AUTH_RESPONSE, false));
            break;
          
          case utilities.Commands.LOGOUT_USER :
            serverListener.logOutUser(this.userName);
            break;
            
          case utilities.Commands.CREATE_USER :
            User createUserInfo = (User)input.getData();
            Boolean createUserResponse = (serverListener.createUser(createUserInfo));
            sendData(new DataPacket<Boolean>(utilities.Commands.CREATE_RESPONSE, createUserResponse));
            break;
            
          case utilities.Commands.SEND_SCORE :
            Integer playerScore = (Integer)input.getData();
            ArrayList<GameScore> topScores = getTopScores(playerScore);
            sendData(new DataPacket<ArrayList<GameScore>>(utilities.Commands.TOP_SCORES, topScores));
            break;
            
          case utilities.Commands.CHAT_MESSAGE :
            ChatMessage cm = (ChatMessage)input.getData();
            cm.setUsername(userName);
            serverListener.sendToAllClients(input);
            break;
            
          case utilities.Commands.GAME_MODE :
            Integer gameMode = (Integer)input.getData();
            gameType = gameMode;
            serverListener.addPlayerToQueue(connectionID);
            break;
            
          case utilities.Commands.PLAYER_ACTION :
            if (input.getData() instanceof PlayerAction){
              serverListener.receiveActionToFinalBattleManager((PlayerAction)input.getData(), userName);
            }
            else if (input.getData() instanceof DeadSwitch)
              serverListener.receiveDeadSwitchToFinalBattleManager((DeadSwitch)input.getData(), userName);
            break;
            
          case utilities.Commands.PLAYER_BEFORE_FB :
            
            Player player = (Player)input.getData();
            serverListener.addPlayerToGameInstance(player, userName);
            
          case utilities.Commands.END_GAME :
            break;
        }
      } catch (IOException ioe) {
        System.out.println("ioe in run(): " + ioe.getMessage());
        break;
      } catch (ClassNotFoundException cnfe) {
        System.out.println("cnfe in run(): " + cnfe.getMessage()); break;
      }
    }
  }
}
