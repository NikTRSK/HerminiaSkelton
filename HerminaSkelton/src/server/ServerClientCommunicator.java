package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerClientCommunicator {
	private Socket socket;
	private ObjectOutputStream oos;
	private BufferedReader br;
	private ServerListener serverListener;
	
	
	public void sendScores(Integer [] scores) {
		try {
			oos.writeObject(scores);
			oos.flush();
		} catch (IOException ioe) {
			utilities.Util.printExceptionToCommand(ioe);
		}
	}
}
