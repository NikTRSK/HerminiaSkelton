package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import client.Player;
import utilities.ChatMessage;
import utilities.DataPacket;
import utilities.DeadSwitch;
import utilities.GameInstance;
import utilities.PlayerAction;
import utilities.User;

public class ServerClientCommunicator extends Thread {
	protected Socket socket;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
//	private BufferedReader br;
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
//		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void sendData(DataPacket<?> dp) {
		try {
			oos.writeObject(dp);
			oos.flush();
		} catch (IOException ioe) { utilities.Util.printExceptionToCommand(ioe); }
	}
	
//	protected void startGame(GameInstance gameInstance) {
	protected void startGame(Integer turn) {
		sendData(new DataPacket<Integer>(utilities.Commands.START_GAME, turn));
	}
	
	protected void endGame() {
		
	}
	
	protected void setUserName(String userName) {
		this.userName = userName;
	}
	
	protected String getUserName() {
		return userName;
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
	public ArrayList<Integer> getTopScores(Integer playerScore) {
		ArrayList<Integer> scores = serverListener.updateScores(userName, playerScore);
		
		return scores;
	}
	
	public Integer getGameType() {
		return gameType;
	}
	
	public void run() {
    boolean listenForConnections = true;
    while (listenForConnections) {
      try {
        DataPacket<?> input = (DataPacket<?>)ois.readObject();
        switch (input.getCommand()) {
        	case utilities.Commands.LOGIN_USER :
        		System.out.println("Logging in user id" + socket.getPort());
        		User userInfo = (User)input.getData();
        		if (serverListener.loginUser(userInfo, connectionID)) {
        			// Send a response to user
        			userName = userInfo.getUsername();
        			sendData(new DataPacket<Boolean>(utilities.Commands.AUTH_RESPONSE, true));
        		} else sendData(new DataPacket<Boolean>(utilities.Commands.AUTH_RESPONSE, false));
        		break;
        	
        	case utilities.Commands.LOGOUT_USER :
        		serverListener.logOutUser((String)userName);
        		break;
        		
        	case utilities.Commands.CREATE_USER :
        		User createUserInfo = (User)input.getData();
        		Boolean createUserResponse = (serverListener.createUser(createUserInfo));
        		sendData(new DataPacket<Boolean>(utilities.Commands.CREATE_RESPONSE, createUserResponse));
        		break;
        		
        	case utilities.Commands.GAME_SCORES :
        		Integer playerScore = (Integer)input.getData();
        		ArrayList<Integer> topScores = getTopScores(playerScore);
        		sendData(new DataPacket<ArrayList<Integer>>(utilities.Commands.GAME_SCORES, topScores));
        		break;
        		
        	case utilities.Commands.CHAT_MESSAGE :
        		ChatMessage cm = (ChatMessage)input.getData();
        		cm.setUsername(userName);
//        		serverListener.sendToAllClients(new DataPacket<ChatMessage>(utilities.Commands.CHAT_MESSAGE, cm));
        		serverListener.sendToAllClients(input);
        		break;
        		
        	case utilities.Commands.GAME_MODE :
        		Integer gameMode = (Integer)input.getData();
        		System.out.println("Setting Game Mode");
        		gameType = gameMode;
        		serverListener.addPlayerToQueue(connectionID);
        		break;
        		
        	case utilities.Commands.PLAYER_ACTION :
        		if (input.getData() instanceof PlayerAction)
        			serverListener.receiveActionToFinalBattleManager((PlayerAction)input.getData(), userName);
        		else if (input.getData() instanceof DeadSwitch)
        			serverListener.receiveDeadSwitchToFinalBattleManager((DeadSwitch)input.getData(), userName);
        		break;
        	
        	case utilities.Commands.FINAL_BATTLE :
        		// Do stuff
        		break;
        		
        	case utilities.Commands.PLAYER_BEFORE_FB :
        		Player player = (Player)input.getData();
        		serverListener.addPlayerToGameInstance(player, userName);
        		
        	case utilities.Commands.END_GAME :
        		break;
        }
      } catch (IOException ioe) {
        System.out.println("ioe in run(): " + ioe.getMessage()); break;
      } catch (ClassNotFoundException cnfe) {
        System.out.println("cnfe in run(): " + cnfe.getMessage()); break;
      }
    }
	}
}
