package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Constants {
	public static final DateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

	
	/*
	 * SETTINGS 
	 * 					*/
	public static final int DEFAULT_PORT = 6880;
	public static final int MIN_PORT_VAL = 0;
	public static final int MAX_PORT_VAL = 65535;
	public static final int GAME_SIZE = 2;
	
	
	// STRINGS
	public static final String PORT_DESCRIPTION_STRING = "<html>Enter the port number on which<br />you would like the server to listen.</html>";
	public static final String PORT_LABEL_STRING = "Port";
	public static final String PORT_ERROR_MESSAGE = "<html><font color=\"red\">Please enter a valid port<br />between " + MIN_PORT_VAL + " and " + MAX_PORT_VAL + "</font></html>";
	public static final String PORT_IN_USE_MESSAGE = "<html><font color=\"red\">Port already in use.  Select another port<br />between " + MIN_PORT_VAL + " and " + MAX_PORT_VAL + "</font></html>";
	
	
	// DATA RESOURCES
  public static final String resourceFolderbgm = "resources/bgm/";
  public static final String resourceFolderbg = "resources/img/";
  public static final String battlemusic = "battlestart.mp3";
  public static final String casualmusic = "casualstart.mp3";
  public static final String startmusic = "gamestart.mp3";
  public static final String finalmusic = "finalstart.mp3";
  
  //
  public static final String win = " did a great job and found true friendships with your CPs. Good luck on the final exam";
  public static final String lose = " did a terrible job, make sure to try it again";
}
