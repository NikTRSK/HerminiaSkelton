package client;

import java.io.ObjectInputStream;
import java.net.Socket;

public class GameClientListener extends Thread {
	private Socket mSocket;
	private ObjectInputStream ois;
//	private PrintWriter pw;
//	private FactoryManager mFManager;
//	private FactoryClientGUI mFClientGUI;
	//Lab 8 Expannsion code member vars
//	private Factory mFactory;  //get a pointer to the factory
//	private Vector<Resource> tempResources; //pointer to the resources
//	private String previous = "";
	GameGUI mGameGUI;
	
	public GameClientListener(GameGUI gameGUI, Socket inSocket) {
		mSocket = inSocket;
		mGameGUI = gameGUI;
		boolean socketReady = initializeVariables();
		if (socketReady) {
			start();
		}
	}
}
