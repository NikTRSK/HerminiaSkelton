package server;

import java.awt.Color;

public class Constants {
	public static final Color SERVER_BACKGROUND_COLOR = new Color(232, 81, 41);
	public static final Color SERVER_FONT_COLOR = Color.WHITE;
	public static final Color SERVER_ERROR_MESSAGE_COLOR = Color.RED;

	// size of the Port GUI window
	public static final int PORT_GUI_WIDTH = 300;
	public static final int PORT_GUI_HEIGHT = 170;
	
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
			

}
