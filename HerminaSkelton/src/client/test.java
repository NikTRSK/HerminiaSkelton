package client;

import java.net.Socket;

import javax.swing.JFrame;

public class test extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public test(){
		new GameGUI(new GameClientListener(new Socket()));
	}
	
	public static void main(String [] args){
		new test();
	}
}
