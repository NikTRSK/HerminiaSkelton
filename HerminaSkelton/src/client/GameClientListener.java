package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

import AllCPs.CP;
import utilities.ChatMessage;
//import utilities.CPRequest;
import utilities.DataPacket;
import utilities.DeadSwitch;
import utilities.FinalBattleState;
import utilities.PlayerAction;
import utilities.User;
import client.Player;

public class GameClientListener extends Thread implements Serializable {
	private static final long serialVersionUID = 6902042676702904127L;
	private String userName;
	private Socket mSocket;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	//private PrintWriter pw;
	private GameGUI mGameGUI;
	private waitGUI waitgui;
	private loginGUI loginGUI;
	private Boolean ans = null;
	
	private Integer me;
	private FinalBattleScreen fbs;
	
	public GameClientListener(Socket socket){
		mSocket = socket;
		fbs=null;
		
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
		this.mGameGUI = gameGUI;
		this.waitgui = null;
	}
	
	protected void setWaitGUI(waitGUI waitgui) {
		this.waitgui = waitgui;
		this.loginGUI = null;
	}
	
	public void sendAction(PlayerAction pa){
		sendData(new DataPacket<PlayerAction>(utilities.Commands.PLAYER_ACTION, pa));
	}

	public void sendDeadSwitch(DeadSwitch ds){
		sendData(new DataPacket<DeadSwitch>(utilities.Commands.PLAYER_ACTION, ds));
	}
	
	public String getUser(){
		return userName;
	}
	
	//send msg to clientListener, doesn't send data that requires/expects a response from server
	public void sendMessage(DataPacket<?> data) {
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
		userName = user.getUsername();
		sendData(new DataPacket<User>(utilities.Commands.LOGIN_USER, user));
	}
	
	public void create(User user) {
		sendData(new DataPacket<User>(utilities.Commands.CREATE_USER, user));
	}
	
	public void prepareForFinalBattle(Player p){
		sendData(new DataPacket<Player>(utilities.Commands.PLAYER_BEFORE_FB, p));
	}
	
	public void logout() {
		try {
			ois.close();
			oos.close();
			mSocket.close();
		} catch (IOException ioe) { utilities.Util.printExceptionToCommand(ioe); }
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
				
				if(streamContent.equals(utilities.Commands.TIMER_OUT)){
					mGameGUI.timerOut();
				}
				
				if (streamContent.equals(utilities.Commands.FINAL_BATTLE)){
					if(input.getData() instanceof FinalBattleState){
						if(fbs==null){
							mGameGUI.StartMultiPlayerFinalBattle(me, (FinalBattleState)input.getData());
						}else{
							fbs.recieveMessage((FinalBattleState)input.getData());
						}
					}else if(true){//input.getData() instanceof CPRequest){
						if(fbs==null){
							System.out.print("We screwed up");
						}else{
							fbs.replaceDead();
						}
					}
					
				}
				if (streamContent.equals(utilities.Commands.END_GAME)){
					//mainGUI.endOfGame();
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
					me = (Integer)input.getData();
					waitgui.startGame();
				}
				else if(streamContent.equals(utilities.Commands.GAME_SCORES)){
					@SuppressWarnings("unchecked")
					ArrayList<Integer> test = (ArrayList<Integer>)input.getData();
					for (Integer t : test)
						System.out.println("Score: " + t);
				}
				else if(streamContent.equals(utilities.Commands.TIME_UPDATE)) {
					if (input.getData() != null) {
						mGameGUI.updateTimer((Integer)input.getData());
					}
				} else if(streamContent.equals(utilities.Commands.CHAT_MESSAGE)) {
					System.out.println("CHAAAAT RECEIVEDDDDD");
					ChatMessage message = (ChatMessage)input.getData();
					mGameGUI.appendToChat(message.getUsername(), message.getMessage());
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
	
	public void setFBS(FinalBattleScreen fbs){
		this.fbs = fbs;
	}
	
	public void sendChat(ChatMessage msg){
		sendData(new DataPacket<ChatMessage>(utilities.Commands.CHAT_MESSAGE, msg));	
	}
}