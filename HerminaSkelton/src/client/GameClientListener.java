package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.text.Utilities;

import server.ServerListener;
import utilities.DataPacket;
import utilities.User;
import utilities.Util;

public class GameClientListener extends Thread{
	private String userName;
	private Socket mSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	//private PrintWriter pw;
	private GameGUI mGameGUI;
	private loginGUI loginGUI;
	private Boolean ans = null;
	// added this
	private Lock userLock;
	private Condition userCondition;
	///////////
	
	public GameClientListener(Socket socket){
		mSocket = socket;
//		userLock = new ReentrantLock();
//		userCondition = userLock.newCondition();
		
		boolean socketReady = initializeVariables();
		if (socketReady){
			System.out.println("Starting client");
			start();
		}
	}
	
	/*
	 * send data to gameClientListener, receives response from server 
	 * and subsequently allows gui to behave accordingly
	 */
	public void sendData(DataPacket<?> data){
		System.out.println("Sending data");
		try {
			oos.writeObject(data);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	protected void setLoginGUI(loginGUI lgui) {
		this.loginGUI = lgui;
	}
	
	protected void setGameGUI(GameGUI gameGUI) {
		mGameGUI = gameGUI;
	}
	
	public void sendAction(){
		
	}
	
	//send msg to clientListener, doesn't send data that requires/expects a response from server
	public void sendMessage(DataPacket<?> data){
		try{
			oos.writeObject(data);
			oos.flush();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public boolean getAnswer(){
		return ans;
	}

	public void login(User user) {
		ans = null;
		userName = user.getUsername();
		sendData(new DataPacket<User>(utilities.Commands.LOGIN_USER, user));
	}
	
	public boolean create(User user) {
		ans = null;
		sendData(new DataPacket<User>(utilities.Commands.LOGIN_USER, user));
		while (ans == null) {}
		return ans;
	}
	
	public void logout() {
		// stub
	}
	
	public void sendGameMode(Integer type) {
		sendData(new DataPacket<Integer>(utilities.Commands.GAME_MODE, type));
	}
	
	public void run(){		
		try{
			while(true){
				// get the data packet
				DataPacket<?> input = (DataPacket<?>)ois.readObject();			
				String streamContent = input.getCommand();
				System.out.println("COMMAND::: " + streamContent);
				
				if (streamContent.equals(utilities.Commands.END_GAME)){
					
				}
				else if(streamContent.equals(utilities.Commands.LOGOUT_USER)){
					User user = (User)input.getData();
					// call the logout on the gui
				}
				else if(streamContent.equals(utilities.Commands.AUTH_RESPONSE)){
					boolean authenticate = (boolean)input.getData();
					loginGUI.processLogin(authenticate);
				}
				else if(streamContent.equals(utilities.Commands.CREATE_RESPONSE)){
					boolean create = (boolean)input.getData();
					loginGUI.processCreateAccount(create);
				}
				else if(streamContent.equals(utilities.Commands.START_GAME)){
					// Dispose of login
					loginGUI.dispose();
					// Start Main GUI
					mGameGUI = new GameGUI(this);
				}
			}
		}catch(IOException ioe){
			System.out.println("Server communication failed");
		}catch(ClassNotFoundException cnfe){
			System.out.println("Class not found");
		}
	}
	
	private boolean initializeVariables(){
		try{
			ois = new ObjectInputStream(mSocket.getInputStream());
			//get socket's output stream and open PrintWriter
			//pw = new PrintWriter(mSocket.getOutputStream());
			oos = new ObjectOutputStream(mSocket.getOutputStream());
		}catch (IOException ioe){
			return false;
		}
		return true;
	}
	
}