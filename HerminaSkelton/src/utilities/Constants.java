package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {
    public static final DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
    
    
    /*
     * SETTINGS
     * 					*/
    public static final int DEFAULT_PORT = 6880;
    public static final String DEFUALT_HOST = "localhost";
    public static final int MIN_PORT_VAL = 0;
    public static final int MAX_PORT_VAL = 65535;
    public static final int GAME_SIZE = 2;
    public static final int GAME_TIME = 3; // in minutes
    
    
    // STRINGS
    public static final String PORT_DESCRIPTION_STRING = "<html>Enter the port number on which<br />you would like the server to listen.</html>";
    public static final String PORT_LABEL_STRING = "Port";
    public static final String PORT_ERROR_MESSAGE = "<html><font color=\"red\">Please enter a valid port<br />between " + MIN_PORT_VAL + " and " + MAX_PORT_VAL + "</font></html>";
    public static final String PORT_IN_USE_MESSAGE = "<html><font color=\"red\">Port already in use.  Select another port<br />between " + MIN_PORT_VAL + " and " + MAX_PORT_VAL + "</font></html>";
    
    
    
    // DATA RESOURCES
    public static final String resourceFolderbgm = "resources/bgm/";
    public static final String resourceFolderbg = "resources/img/";
    public static final String connectedimag = "connected.png";
    public static final String disconnectedimag = "disconnected.png";
    public static final String battlemusic = "battlestart.mp3";
    public static final String casualmusic = "casualstart.mp3";
    public static final String startmusic = "gamestart.mp3";
    public static final String finalmusic = "finalstart.mp3";
    public static final String waitmusic = "wait.mp3";
    public static final String endmusic = "end.mp3";
    public static final String healmusic = "heal.mp3";
    public static final String connected = "connected.png";
    public static final String disconnected  = "disconnected.png";
    public static final String serverbkgimg = "serverbkgimg.png";
    
    //
    public static final String win = "did a great job and founded true friendships with your CPs. Be sure to play again!";
    public static final String lose = " have lost the battle, make sure to try it again";
    
    //
    public static final String generateWord(boolean win, int gamemode){
    	String temp;
    	if(win){
    		if(gamemode == 2){
				temp = "Congratulations! You ";
			}
			else{
				temp = "Congratulate you ";
			}
			temp+=Constants.win;
			if(gamemode == 2){
				//temp += " you guys ";
			}
			else{
				temp += " you ";
			}
			return temp;
    	}
    	else{
			if(gamemode == 2){
				temp = "WAHAHAHAHA you guys";
			}
			else{
				temp = "WAHAHAHAHA you";
			}
			temp+=Constants.lose;
			return temp;
		}
    }
}
