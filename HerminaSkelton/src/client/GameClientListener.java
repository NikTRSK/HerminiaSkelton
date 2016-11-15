package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClientListener extends Thread{
	private Socket mSocket;
	private ObjectInputStream ois;
	private PrintWriter pw;
	private GameGUI mGameGUI;
	
	public GameClientListener(GameGUI gameGUI, Socket socket){
		mSocket = socket;
		mGameGUI = gameGUI;
		boolean socketReady = initializeVariables();
		if (socketReady){
			start();
		}
	}
	
	public void sendMessage(String msg){
		pw.print(msg);
		pw.flush();
	}
	
	public void run(){
		//check for another game instance
		GameGUI game;
		try{
			while(true){
				game = (GameGUI)ois.readObject();
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
			pw = new PrintWriter(mSocket.getOutputStream());
		}catch (IOException ioe){
			return false;
		}
		return true;
	}
	
}
