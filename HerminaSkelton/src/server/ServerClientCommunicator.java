package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Vector;

import utilities.Commands;
import utilities.DataPacket;
import utilities.GameInstance;
import utilities.User;

public class ServerClientCommunicator extends Thread {
	protected Socket socket;
	protected ObjectOutputStream oos;
	protected ObjectInputStream ois;
	private BufferedReader br;
	private ServerListener serverListener;
	
	String userName;
	Integer connectionID;
	
	public ServerClientCommunicator(Socket socket, ServerListener serverListener) throws IOException {
		this.socket = socket;
		this.serverListener = serverListener;
		this.oos = new ObjectOutputStream(socket.getOutputStream());
		this.ois = new ObjectInputStream(socket.getInputStream());
		this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
		
	protected void startGame(GameInstance gameInstance) {
		try {
			oos.writeObject(new DataPacket<GameInstance>(utilities.Commands.START_GAME, gameInstance));
			oos.flush();
		} catch (IOException ioe) {
			utilities.Util.printExceptionToCommand(ioe);
		}
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
	
	public void loginUser(User userInfo) {
		boolean validUser = serverListener.loginUser(userInfo);
		if (validUser)
			userName = userInfo.getUsername();
		else {
			DataPacket<String> error = new DataPacket<String>(Commands.ERROR_MESSAGE, "Invalid Login!!!");
			try {
				oos.writeObject(error);
				oos.flush();
			} catch (IOException ioe) {
				utilities.Util.printExceptionToCommand(ioe);
			}
		}
	}
	
	protected void setID(int threadID) {
		this.connectionID = threadID;
	}
	
	public void run() {
    boolean listenForConnections = true;
    while (listenForConnections) {
      try {
        DataPacket<?> input = (DataPacket<?>)ois.readObject();
        switch (input.getCommand()) {
        	case utilities.Commands.LOGIN_USER :
        		System.out.println("Logging in user");
//        		User userInfo = (User)input.getData();
//        		serverListener.loginUser(userInfo);
        		loginUser((User)input.getData());
        		break;
        	case utilities.Commands.LOGOUT_USER :
        		serverListener.logOutUser((String)userName);
        		break;
//        	case utilities.Commands.START_GAME :
//        		break;
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
