package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;

import utilities.ChatMessage;
//import utilities.CPRequest;
import utilities.DataPacket;
import utilities.DeadSwitch;
import utilities.FinalBattleState;
import utilities.GameScore;
import utilities.PlayerAction;
import utilities.User;

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
  private Boolean win = false;
  
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
  
  public void sendData(DataPacket<?> data){
    try {
      oos.writeObject(data);
      oos.flush();
    } catch (IOException e) {
      utilities.Util.printExceptionToCommand(e);
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
    sendData(new DataPacket<String>(utilities.Commands.LOGOUT_USER, userName));
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
        
        if(streamContent.equals(utilities.Commands.TIMER_OUT)){
          mGameGUI.timerOut();
        }
        
        if (streamContent.equals(utilities.Commands.FINAL_BATTLE)){
          if(input.getData() instanceof FinalBattleState){
            if(fbs==null){
              mGameGUI.StartMultiPlayerFinalBattle(me, (FinalBattleState)input.getData());
            }else{
//              FinalBattleState newFBS = (FinalBattleState)input.getData();
              fbs.recieveMessage((FinalBattleState)input.getData());
            }
          }else if(input.getData() instanceof Integer){
            if(fbs==null){
              System.out.print("We screwed up");
            }else{
              Integer temp = (Integer)input.getData();
              if(temp==me)
                fbs.replaceDead();
            }
          }
        }
        if(streamContent.equals(utilities.Commands.SINGLE_FINAL_BATTLE)) {
          mGameGUI.StartSinglePlayerFinalBattle();
        }
        if (streamContent.equals(utilities.Commands.END_GAME)){
          win = (Boolean)input.getData();
          sendData(new DataPacket<Integer>(utilities.Commands.SEND_SCORE, mGameGUI.getPlayerScore()));
        } else if (streamContent.equals(utilities.Commands.TOP_SCORES)){
          @SuppressWarnings("unchecked")
          ArrayList<GameScore> gs = (ArrayList<GameScore>)input.getData();
          EndGameGUI endGUI = new EndGameGUI(2, mGameGUI.getBestCP(), gs, win, this);
          endGUI.setVisible(true);
        }
        else if(streamContent.equals(utilities.Commands.LOGOUT_USER)){
          System.exit(0);
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
        else if(streamContent.equals(utilities.Commands.TIME_UPDATE)) {
          if (input.getData() != null) {
            mGameGUI.updateTimer((Integer)input.getData());
          }
        } else if(streamContent.equals(utilities.Commands.CHAT_MESSAGE)) {
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
