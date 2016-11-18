package client;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.text.Utilities;

import server.ServerListener;
import utilities.DataPacket;
import utilities.User;

public class GameClientListener extends Thread{
	private Socket mSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	//private PrintWriter pw;
	private GameGUI mGameGUI;
	private boolean ans = false;
	
	public GameClientListener(GameGUI gameGUI, Socket socket){
		mSocket = socket;
		mGameGUI = gameGUI;
		boolean socketReady = initializeVariables();
		if (socketReady){
			start();
		}
	}
	
	/*
	 * send data to gameClientListener, receives response from server 
	 * and subsequently allows gui to behave accordingly
	 */
	public boolean sendData(DataPacket<?> data){
		try {
			oos.writeObject(data);
			oos.flush();
			//should there be some way to wait until response is received from server?
			//does this always return false?
			return getAnswer();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}	
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

	
	public void run(){		
		try{
			DataPacket<?> input = (DataPacket<?>)ois.readObject();			
			String streamContent = input.getCommand();
			//ServerListener listener = new ServerListener(mSocket);
			while(true){
				if (streamContent == utilities.Commands.END_GAME){
					//String endMsg = (String)input.getData();
					mGameGUI.updateGameGUI(streamContent);
				}
				else if(streamContent == utilities.Commands.LOGIN_USER){
					User user = (User)input.getData();
					oos.writeObject(new DataPacket<User>(utilities.Commands.LOGIN_USER, user));
				}
				else if(streamContent == utilities.Commands.LOGOUT_USER){
					//send user to server to be logged out
					User user  = (User)input.getData();
					oos.writeObject(new DataPacket<User>(utilities.Commands.LOGOUT_USER, user));
					//or take command and update gameGUI accordingly
					//String logoutMsg = (String)input.getData();
					//mGameGUI.updateGameGUI();
				}
				else if (streamContent == utilities.Commands.CREATE_USER){
					User user = (User)input.getData();
					oos.writeObject(new DataPacket<User>(utilities.Commands.CREATE_USER, user));
				}
				else if(streamContent == utilities.Commands.AUTH_RESPONSE){
					boolean authenticate = (boolean)input.getData();
					ans = authenticate;
				}
				else if(streamContent == utilities.Commands.CREATE_RESPONSE){
					boolean create = (boolean)input.getData();
					ans = create;
				}
				else if(streamContent == utilities.Commands.START_GAME){
					
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
